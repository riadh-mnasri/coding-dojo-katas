# Roman Numerals (chiffres romains)

🇬🇧 [English version](README.en.md) · Énoncé : [codingdojo.org/kata/RomanNumerals](https://codingdojo.org/kata/RomanNumerals/)

## Le kata

- **Partie I** : convertir un entier (1 à ~3000) en chiffres romains (`7 → VII`).
- **Partie II** : convertir dans l'autre sens.

## Démarche TDD

🔴 rouge · 🟢 vert · 🔵 refactor · 📌 test vert du premier coup

| # | Étape | Ce qui s'est passé |
|---|---|---|
| 1 | 🔴 `convert 1 to I` | Compilation impossible : `RomanNumerals` n'existe pas. |
| 2 | 🟢 `fake I for 1` | `return "I"`. |
| 3 | 🔴 `repeat I for 2 and 3` | Attendu `"II"`, obtenu `"I"`. |
| 4 | 🟢 `repeat I` | `"I".repeat(number)`. |
| 5 | 🔴 `convert 5 and 6` | Attendu `"V"`, obtenu `"IIIII"`. |
| 6 | 🟢 `use V before the I` | Un `if (remaining >= 5)` puis les `I` restants. |
| 7 | 🔴 `convert 10 and 20` | Attendu `"X"`, obtenu `"VIIIII"`. |
| 8 | 🟢 `use X before V and I` | Trois boucles `while` identiques, une par lettre. La duplication est flagrante. |
| 9 | 🔵 `replace the three loops with a symbol table` | Une table `valeur → symbole` parcourue du plus grand au plus petit : l'algorithme glouton apparaît. |
| 10 | 🔴 `convert 4 and 9 with subtraction` | Attendu `"IV"`, obtenu `"IIII"`. |
| 11 | 🟢 `add IV and IX as symbols` | Au lieu de coder la règle soustractive, on ajoute `IV` et `IX` **dans la table**. L'algorithme ne change pas. |
| 12 | 🔴 `convert L, C, D, M and their subtractions` | 8 cas en échec (`50 → XXXXX`...). |
| 13 | 🟢 `complete the symbol table` | Six lignes de données de plus, zéro logique. |
| 14 | 📌 `convert full numbers` | `1990`, `2008`, `1666`, `3999` passent. |
| 15 | 🔴 `reject zero` | Aucune exception levée pour `0`. |
| 16 | 🟢 `accept only 1 to 3999` | Un `require`. |
| 17 | 🔴 `convert I back to 1` | Partie II : `toArabic` n'existe pas. |
| 18 | 🟢 `fake 1 for I` | `return 1`. |
| 19 | 🔴 `add letter values back` | `III` : attendu 3, obtenu 1. |
| 20 | 🟢 `sum letter values` | Somme des valeurs des lettres. |
| 21 | 🔴 `handle subtraction when converting back` | `IV` : attendu 4, obtenu 6. |
| 22 | 🟢 `subtract a letter smaller than its neighbour` | Chaque lettre est comparée à sa voisine de droite. |
| 23 | 📌 `round-trip every supported number` | `toArabic(toRoman(n)) == n` pour 1..3999 : filet de sécurité. |
| 24 | 🔴 `reject malformed numerals` | `IIII`, `VV`, `IC` sont acceptés à tort, `ABC` lève la mauvaise exception. |
| 25 | 🟢 `reject numerals that are not in canonical form` | Plutôt que d'empiler des règles de validation, on vérifie que la réécriture canonique redonne la chaîne d'entrée. |
| 26 | 🔵 `group the lookup tables` | Rangement des deux tables ensemble, commentaire sur le choix de validation. |

## Solution

- Algorithme glouton sur une table de 13 symboles (les 7 lettres + 6 soustractions).
- Le sens inverse compare chaque lettre à sa voisine de droite.
- La validation réutilise `toRoman` : une seule source de vérité pour la forme correcte.

## Ce que j'en retiens

L'ordre des tests a façonné le design : quand `4` est arrivé, la table existait déjà (étape 9), et les soustractions sont devenues de simples lignes de données plutôt qu'une logique spéciale.

## Lancer les tests

```bash
./gradlew :katas:roman-numerals:test
git log --reverse --format='%s%n%b' strict-tdd-start.. -- katas/roman-numerals   # l'historique TDD
```
