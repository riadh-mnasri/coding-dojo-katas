# RPN Calculator (notation polonaise inverse)

🇬🇧 [English version](README.en.md) · Énoncé : [codingdojo.org/kata/RPN](https://codingdojo.org/kata/RPN/)

## Le kata

Évaluer une expression en notation polonaise inverse : `3 5 8 * 7 + *` vaut `((5 × 8) + 7) × 3 = 141`. Puis ajouter `SQRT` (`9 SQRT = 3`) et `MAX` (`5 3 4 2 9 1 MAX = 9`, `4 5 MAX 1 2 MAX * = 10`).

## Démarche TDD

🔴 rouge · 🟢 vert · 🔵 refactor · 📌 test vert du premier coup

| # | Étape | Ce qui s'est passé |
|---|---|---|
| 1 | 🔴 `evaluate a lone number` | Compilation impossible : `RpnCalculator` n'existe pas. |
| 2 | 🟢 `parse a lone number` | `expression.toDouble()`. |
| 3 | 🔴 `add the two previous values` | `NumberFormatException` sur `"1 2 +"`. |
| 4 | 🟢 `push numbers on a stack and add them` | La **pile** apparaît : les nombres sont empilés, `+` dépile deux valeurs. |
| 5 | 🔴 `subtract, multiply and divide in operand order` | `-`, `*`, `/` inconnus. |
| 6 | 🟢 `support the four arithmetic operators` | Un `when` à quatre branches ; attention à l'ordre des opérandes (`5 3 -` vaut 2). |
| 7 | 🔵 `map each symbol to a binary operation` | Les quatre branches identiques deviennent une table `symbole → (Double, Double) -> Double`. |
| 8 | 📌 `chain expressions` | `4 2 + 3 -` et `3 5 8 * 7 + *` : la pile fait le travail. |
| 9 | 🔴 `compute a square root` | `SQRT` inconnu. |
| 10 | 🟢 `compute square roots` | Un cas particulier dans la boucle, assumé pour passer au vert vite. |
| 11 | 🔵 `let every operation work on the whole stack` | `SQRT` ne rentre pas dans le modèle « deux opérandes ». Une interface `Operation` reçoit la pile, avec des fabriques `binary` et `unary`. |
| 12 | 🔴 `take the max of the whole stack` | `MAX` inconnu. |
| ⛔ | tentative de vert refusée | Ma première version prenait le max de **toute** la pile. `4 5 MAX 1 2 MAX *` échoue : le second `MAX` absorbe aussi le `5` calculé avant, et `*` n'a plus qu'une valeur. J'avais mal lu l'énoncé, et `scripts/tdd.sh` a refusé le commit. |
| 13 | 🟢 `take the max of the operands pushed since the last operation` | Une `OperandStack` retient où s'arrête le résultat de la dernière opération ; `MAX` ne prend que les opérandes empilés depuis. |
| 14 | 🔴 `reject invalid expressions` | Opérandes manquants (`NoSuchElementException`), division par zéro acceptée, `MAX` sans opérande. |
| 15 | 🟢 `report missing operands and division by zero` | Des `require` avec un message clair à chaque point de contrôle. |
| 16 | 🔴 `plug in a new operation` | Le constructeur ne prend pas de table d'opérations. |
| 17 | 🟢 `inject the operation table` | `RpnCalculator(operations = defaultOperations)` : on ajoute `%` sans modifier la classe. |

## Solution

- `OperandStack` : la pile, plus la position du dernier résultat.
- `Operation` : une interface fonctionnelle qui agit sur la pile. Les fabriques `binary` et `unary` couvrent les cas courants, `MAX` est écrite à part.
- `RpnCalculator` : parcourt les jetons, applique l'opération connue ou empile le nombre.

## Ce que j'en retiens

- Le passage de « un opérateur prend deux nombres » à « une opération reçoit la pile » a été déclenché par `SQRT`, et c'est lui qui a rendu `MAX` possible sans casser le reste.
- Le second exemple de `MAX` précise une règle que le premier laissait ambiguë. Sans lui, j'aurais livré la mauvaise sémantique.

## Lancer les tests

```bash
./gradlew :katas:rpn-calculator:test
git log --reverse --format='%s%n%b' strict-tdd-start.. -- katas/rpn-calculator   # l'historique TDD
```
