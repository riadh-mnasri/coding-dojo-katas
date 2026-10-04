#!/usr/bin/env bash
# Copyright (c) 2026 Riadh MNASRI. Licensed under the MIT License.
#
# Garde-fou TDD : lance les tests d'un kata et ne commite que si la phase annoncée est respectée.
#
#   scripts/tdd.sh <kata> red      "test(<kata>): ..."      -> exige au moins un test en échec
#   scripts/tdd.sh <kata> green    "feat(<kata>): ..."      -> exige une suite entièrement verte
#   scripts/tdd.sh <kata> refactor "refactor(<kata>): ..."  -> exige une suite entièrement verte
#   scripts/tdd.sh <kata> docs     "docs(<kata>): ..."      -> suite verte et seuls des README modifiés
#   scripts/tdd.sh <kata> pin      "test(<kata>): ..."      -> test ajouté qui passe du premier coup
#                                                            (il ne force aucun code, on le garde comme
#                                                            documentation ou filet de sécurité)
#
# En phase rouge, la raison de l'échec (compilation ou assertion) est recopiée dans le corps du commit.
set -uo pipefail

kata=${1:?kata manquant}
phase=${2:?phase manquante (red|green|refactor)}
message=${3:?message de commit manquant}
custom_body=${4:-}   # corps de commit optionnel, pour expliquer une étape inhabituelle

cd "$(dirname "$0")/.."

case $phase in
  red) expected_type="test" ;;
  green) expected_type="feat|fix" ;;
  refactor) expected_type="refactor" ;;
  pin) expected_type="test" ;;
  docs) expected_type="docs" ;;
  *) echo "Phase inconnue : $phase" >&2; exit 2 ;;
esac
if ! [[ $message =~ ^($expected_type)\($kata\):\ .+ ]]; then
  echo "Refusé : en phase $phase, le message doit commencer par ($expected_type)($kata): ..." >&2
  exit 2
fi

if [ "$phase" = docs ]; then
  others=$(git status --porcelain | awk '{print $2}' | grep -vE '(^|/)README(\.en)?\.md$' || true)
  if [ -n "$others" ]; then
    echo "Refusé : un commit de documentation ne doit toucher que des README, or ceci a changé :" >&2
    echo "$others" >&2
    exit 1
  fi
fi

rm -rf "katas/$kata/build/test-results"
output=$(./gradlew ":katas:$kata:test" --offline --console=plain 2>&1)
status=$?

failures() {
  python3 - "katas/$kata/build/test-results/test" <<'PY'
import glob, sys, xml.etree.ElementTree as ET
for path in sorted(glob.glob(sys.argv[1] + "/*.xml")):
    for case in ET.parse(path).getroot().iter("testcase"):
        failure = case.find("failure")
        if failure is not None:
            lines = [l.strip() for l in (failure.get("message") or "").splitlines() if l.strip()]
            print(f"- {case.get('name')}: {' / '.join(lines)[:300]}")
PY
}

body=""
if [ "$phase" = red ]; then
  if [ $status -eq 0 ]; then
    echo "Refusé : la phase rouge exige un test qui échoue, or toute la suite est verte." >&2
    exit 1
  fi
  compile_errors=$(echo "$output" | grep -E '^e: ' | sed -E 's#^e: file://[^ ]*/([^/]+\.kt):[0-9]+:[0-9]+ #\1: #' | head -5)
  if [ -n "$compile_errors" ]; then
    body=$(printf 'Red (compilation):\n%s' "$compile_errors")
  else
    failing=$(failures)
    if [ -z "$failing" ]; then
      echo "$output" | grep -vE '^\s*$|^> Task' | head -15 >&2
      echo "Refusé : la suite échoue sans erreur de compilation ni test en échec (problème de build ou de dépendances ?)." >&2
      exit 1
    fi
    body=$(printf 'Red:\n%s' "$failing")
  fi
else
  if [ $status -ne 0 ]; then
    echo "$output" | grep -E '^e: |FAILED|expected|but was' | head -30 >&2
    failures >&2
    echo "Refusé : la phase $phase exige une suite entièrement verte." >&2
    exit 1
  fi
fi

total=$(cat katas/$kata/build/test-results/test/*.xml 2>/dev/null | grep -o '<testsuite [^>]*' | grep -o ' tests="[0-9]*"' | grep -o '[0-9]*' | paste -sd+ - | bc)
git add "katas/$kata"
[ "$phase" = docs ] && git add README.md README.en.md
if [ -n "$body" ]; then
  git commit -q -m "$message" -m "$body"
else
  [ "$phase" = pin ] && body="Passed on first run: no production code was needed."
  [ -n "$custom_body" ] && body="$custom_body"
  if [ -n "$body" ]; then git commit -q -m "$message" -m "$body"; else git commit -q -m "$message"; fi
fi
echo "[$phase] ${total:-0} test(s) exécuté(s) : $(git log -1 --format='%h %s')"
[ -n "$body" ] && echo "$body"
exit 0
