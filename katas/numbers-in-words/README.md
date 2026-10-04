# Numbers in Words

🇬🇧 [English version](README.en.md) · Énoncé : [codingdojo.org/kata/NumbersInWords](https://codingdojo.org/kata/NumbersInWords/)

## Le kata

Écrire un nombre en toutes lettres, comme sur un chèque : `745` → « seven hundred and forty five dollars ». **Étape 2** : faire la conversion inverse. **Étape 3** : tout faire en TDD.

L'énoncé écrit « fourty » ; j'ai retenu l'orthographe correcte « forty », et « fourty » est justement refusé à la relecture.

## Démarche TDD

🔴 rouge · 🟢 vert · 🔵 refactor · 📌 test vert du premier coup

| # | Étape | Ce qui s'est passé |
|---|---|---|
| 1 | 🔴 `say zero` | Compilation impossible. |
| 2 | 🟢 `say zero` | `return "zero"`. |
| 3 | 🔴 `say units and teens` | `one`, `seven`, `ten`, `thirteen`... |
| 4 | 🟢 `name numbers below twenty` | Une liste de 20 mots : en anglais, 11 à 19 sont irréguliers, autant les lister. |
| 5 | 🔴 `say tens with their units` | Indice 20 hors de la liste. |
| 6 | 🟢 `say tens and their unit` | Une liste des dizaines, plus l'unité éventuelle. |
| 7 | 🔴 `say hundreds with and` | `745` : indice hors liste. |
| 8 | 🟢 `say hundreds and link the rest with and` | `seven hundred and ...` par récursion sur le reste. |
| 9 | 🔴 `say thousands and millions` | `1000` donne « ten hundred ». |
| 10 | 🟢 `say numbers by groups of three digits` | Le nombre est découpé en groupes de trois chiffres (millions, milliers, unités), chacun dit par la fonction des centaines. Règle britannique : le dernier groupe prend « and » s'il est inférieur à 100 (`one thousand and one`). |
| 11 | 🔴 `read words back into a number` | Étape 2 : `toNumber` n'existe pas. |
| 12 | 🟢 `read words back by accumulating groups` | On additionne unités et dizaines dans un groupe, « hundred » le multiplie par 100, une échelle (« thousand », « million ») le verse dans le total. **Écart** : j'y ai aussi ajouté le rejet d'un mot inconnu, qu'aucun test ne demandait. |
| 13 | 📌 `round-trip numbers through words` | `toNumber(toWords(n)) == n` pour 0 à 20 000 et quelques grands nombres. |
| 14 | 📌 `cover unknown words, rejected without a red test` | Rattrapage de l'écart de l'étape 12 (« fourty » est refusé). Ce test ne pouvait que passer. |
| 15 | 🔴 `accept only 0 to 999,999,999` | `-1` lève une `ArrayIndexOutOfBoundsException`. |
| 16 | 🟢 `reject numbers out of range` | Un `require`. |
| 17 | 🔴 `write a cheque amount in dollars` | `dollars` n'existe pas. |
| 18 | 🟢 `write cheque amounts in dollars` | Avec le singulier pour 1. |

## Solution

- `toWords` : groupes de trois chiffres + `belowThousand`, avec la règle du « and ».
- `toNumber` : un accumulateur de groupe et un total.
- `dollars` : la formule du chèque.

## Ce que j'en retiens

- Le test aller-retour (étape 13) vérifie les deux sens l'un par l'autre sur 20 000 valeurs.
- Deux tests 📌 de validation (comme pour NimGame) trahissent du code écrit en avance. Je les ai laissés visibles dans l'historique plutôt que de les maquiller.

## Lancer les tests

```bash
./gradlew :katas:numbers-in-words:test
git log --reverse --format='%s%n%b' strict-tdd-start.. -- katas/numbers-in-words   # l'historique TDD
```
