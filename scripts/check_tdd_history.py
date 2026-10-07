# Copyright (c) 2026 Riadh MNASRI. Licensed under the MIT License.
"""Vérifie que l'historique git depuis le tag strict-tdd-start respecte le cycle TDD, kata par kata.

Règles, pour chaque commit qui touche katas/<slug> :
- un commit `test` dont le corps commence par « Red » ne modifie que des tests ; il ouvre un cycle ;
- un commit `feat` ne vient qu'après un rouge, et le referme : deux verts de suite sans rouge sont refusés ;
- un commit `refactor` ou `docs` ne se fait pas pendant qu'un rouge attend son vert ;
- un commit `test` sans « Red » (test épinglé, vert du premier coup) ne modifie que des tests ;
- un commit `docs` ne modifie que des README ;
- l'approbation d'un golden master (un commit `test` qui ne touche qu'un fichier .approved.txt) referme un rouge.

Les modules peuvent ajouter leurs dépendances (build.gradle.kts) dès le premier rouge.
Les écarts connus, racontés dans la doc du kata, sont listés dans KNOWN_SLIPS : ils restent visibles ici.

Usage : python3 scripts/check_tdd_history.py [révision de départ, strict-tdd-start par défaut]
"""
import re
import subprocess
import sys
from collections import defaultdict

START = sys.argv[1] if len(sys.argv) > 1 else "strict-tdd-start"
# Écarts du passé, déjà documentés dans le README du kata concerné. Toute nouvelle entrée doit l'être aussi.
KNOWN_SLIPS = {
    # Tennis : commandes non enchaînées, du code de production en échec est parti dans le commit docs ;
    # corrigé par le commit suivant, fix(tennis), et à l'origine de la phase docs du garde-fou.
    "ce6b18dd1cc69fe82ff65d46931c2f1eb400831f",
}

SUBJECT = re.compile(r"^(\w+)(?:\(([\w-]+)\))?: ")


def commits():
    log = subprocess.run(
        ["git", "log", "--reverse", "--format=%x1e%H%x1f%s%x1f%b%x1f", "--name-only", f"{START}..HEAD"],
        capture_output=True, text=True, check=True,
    ).stdout
    for record in log.split("\x1e")[1:]:
        sha, subject, body, files = record.split("\x1f")
        yield sha, subject, body.strip(), [f for f in files.split("\n") if f]


def is_test(path):
    return "/src/test/" in path or path.endswith("build.gradle.kts")


def is_approval(files):
    return bool(files) and all(f.endswith(".approved.txt") for f in files)


def main():
    errors = []
    red_pending = {}
    seen = defaultdict(int)

    for sha, subject, body, files in commits():
        match = SUBJECT.match(subject)
        if not match:
            errors.append(f"{sha[:7]} sujet hors format Angular : {subject}")
            continue
        kind, scope = match.groups()
        if scope is None:
            continue  # pas de scope : un changement transverse, hors des katas
        kata_files = [f for f in files if f.startswith(f"katas/{scope}/")]
        if not kata_files:
            continue  # scripts, README racine, etc.
        seen[scope] += 1
        if sha in KNOWN_SLIPS:
            red_pending[scope] = None
            print(f"Écart connu et documenté : {sha[:7]} {subject}")
            continue
        code = [f for f in kata_files if f.endswith(".kt") or f.endswith(".kts")]
        where = f"{sha[:7]} {subject}"

        if kind == "chore":
            continue  # import d'un code legacy, hors cycle
        if kind == "test":
            if any(not is_test(f) for f in code):
                errors.append(f"{where} : un commit de test modifie du code de production")
            if is_approval(kata_files) and red_pending.get(scope):
                red_pending[scope] = None
            elif body.startswith("Red"):
                if red_pending.get(scope):
                    errors.append(f"{where} : deux rouges de suite")
                red_pending[scope] = sha
        elif kind == "feat":
            if not red_pending.get(scope):
                errors.append(f"{where} : vert sans rouge avant lui")
            red_pending[scope] = None
        elif kind == "fix":
            if not red_pending.get(scope) and any(not is_test(f) for f in code):
                errors.append(f"{where} : correctif de production sans rouge avant lui")
            red_pending[scope] = None
        elif kind in ("refactor", "docs"):
            if red_pending.get(scope):
                errors.append(f"{where} : {kind} alors qu'un rouge attend son vert ({red_pending[scope][:7]})")
            if kind == "docs" and any(not f.split("/")[-1].startswith("README") for f in kata_files):
                errors.append(f"{where} : un commit docs modifie autre chose que des README")
        else:
            errors.append(f"{where} : type de commit inattendu sur un kata")

    for scope, sha in red_pending.items():
        if sha:
            errors.append(f"{scope} : le rouge {sha[:7]} n'a jamais eu son vert")

    if errors:
        print("\n".join(errors))
        sys.exit(f"{len(errors)} écart(s) au cycle TDD.")
    print(f"Historique TDD conforme : {sum(seen.values())} commits sur {len(seen)} katas.")


if __name__ == "__main__":
    main()
