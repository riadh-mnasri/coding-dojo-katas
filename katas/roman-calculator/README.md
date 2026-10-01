# Roman Calculator (calculatrice romaine)

🇬🇧 [English version](README.en.md) · Énoncé : [codingdojo.org/kata/RomanCalculator](https://codingdojo.org/kata/RomanCalculator/)

## Le kata

Additionner deux nombres romains **sans les convertir en entiers** : « nous sommes à Rome, il n'y a ni décimaux ni `int` ». Exemple : `XIV + LX = LXXIV`.

L'énoncé ne donne volontairement pas de cas de test : tout l'intérêt est de trouver le suivant.

## Démarche TDD

🔴 rouge · 🟢 vert · 🔵 refactor · 📌 test vert du premier coup

| # | Étape | Ce qui s'est passé |
|---|---|---|
| 1 | 🔴 `add I and I` | Compilation impossible : `RomanCalculator` n'existe pas. |
| 2 | 🟢 `concatenate both numerals` | `left + right`. |
| 3 | 🔴 `sort letters from the biggest` | `I + X` : attendu `"XI"`, obtenu `"IX"`. |
| 4 | 🟢 `sort letters from the biggest` | Tri selon l'ordre `MDCLXVI`. |
| 5 | 🔴 `group five I into V` | Attendu `"V"`, obtenu `"IIIII"`. |
| 6 | 🟢 `group five I into V` | Un `replace("IIIII", "V")`. |
| 7 | 🔴 `group letters at every level` | `V + V`, `XXX + XX`, `D + D`... et `VIII + VII` (`VVV` au lieu de `XV`). |
| 8 | 🟢 `group letters at every level` | Une table de regroupements parcourue **du plus petit au plus grand**, pour que les retenues se propagent (`IIIII → V` crée un `VV`, qui devient `X`). |
| 9 | 🔴 `write four I as IV` | Attendu `"IV"`, obtenu `"IIII"`. |
| 10 | 🟢 `write four I as IV` | Un `replace("IIII", "IV")` final. |
| 11 | 🔴 `write VIIII as IX` | `VII + II` : attendu `"IX"`, obtenu `"VIV"`. Le piège classique. |
| 12 | 🟢 `write VIIII as IX before IIII as IV` | À chaque niveau, la forme longue doit être réécrite avant la courte. |
| 13 | 🔴 `use subtraction at every level` | `XL`, `XC`, `CD`, `CM` manquent. |
| 14 | 🟢 `use subtraction at every level` | Une table `subtractives` ordonnée (`CM` avant `CD`, `XC` avant `XL`, `IX` avant `IV`). |
| 15 | 🔴 `expand subtractive inputs before adding` | `IV + I` : attendu `"V"`, obtenu `"VII"`. Les entrées soustractives doivent être développées (`IV → IIII`) avant le tri. |
| ⛔ | tentative de vert refusée | Ma première version développait **après** concaténation. `VIII + VII` donne `VIIIVII`, qui contient un faux `IV` à la jonction. Trois tests repassent au rouge, et `scripts/tdd.sh` refuse le commit. |
| 16 | 🟢 `expand each subtractive operand before adding` | Chaque opérande est développé séparément. |
| 17 | 🔵 `express addition as an expand, sort, group, compress pipeline` | Le code se lit enfin comme l'algorithme : `compress(group(sort(expand(a) + expand(b))))`. |
| 18 | 📌 `carry through several levels` | `CMXCIX + I = M`, `MMCDXLIV + MCDXLIV = MMMDCCCLXXXVIII` passent. |

## Solution

```kotlin
fun add(left: String, right: String): String =
    compress(group(sort(expand(left) + expand(right))))
```

| Étape | Rôle | Exemple |
|---|---|---|
| `expand` | développer les formes soustractives | `XIV → XIIII` |
| `sort` | trier les lettres de la plus grande à la plus petite | `XIIII` + `LX` → `LXXIIII` |
| `group` | regrouper avec retenue | `IIIII → V`, `VV → X` |
| `compress` | réécrire en forme soustractive | `IIII → IV` |

Les règles romaines sont des données (`groupings`, `subtractives`) qu'on peut relire sans lire l'algorithme.

## Ce que j'en retiens

- C'est un kata de **choix du prochain test** : chaque test a fait apparaître une étape du pipeline.
- Le refus du garde-fou montre l'intérêt de garder tous les tests précédents : la correction du test 15 cassait trois anciens cas, ce qu'une vérification « à l'œil » n'aurait probablement pas vu.

## Lancer les tests

```bash
./gradlew :katas:roman-calculator:test
git log --reverse --format='%s%n%b' strict-tdd-start.. -- katas/roman-calculator   # l'historique TDD
```
