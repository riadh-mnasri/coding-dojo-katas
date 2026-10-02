# Minesweeper (démineur)

🇬🇧 [English version](README.en.md) · Énoncé : [codingdojo.org/kata/Minesweeper](https://codingdojo.org/kata/Minesweeper/)

## Le kata

Pour chaque champ de mines (`*` = mine, `.` = case sûre), remplacer chaque case sûre par le nombre de mines voisines (jusqu'à 8). L'entrée enchaîne plusieurs champs précédés de leur taille et se termine par `0 0` ; la sortie numérote les champs (`Field #1:`) séparés par une ligne vide.

## Démarche TDD

🔴 rouge · 🟢 vert · 🔵 refactor · 📌 test vert du premier coup

D'abord le calcul des indices sur un champ (`Field`), ensuite seulement le format d'entrée/sortie (`Minesweeper`).

| # | Étape | Ce qui s'est passé |
|---|---|---|
| 1 | 🔴 `hint 0 for a lonely safe square` | Compilation impossible. |
| 2 | 🟢 `replace every square with 0` | Chaque case devient `0`. |
| 3 | 🔴 `keep mines as they are` | `*` devient `0`. |
| 4 | 🟢 `keep mines` | Les mines restent des mines. |
| 5 | 🔴 `count a mine next to a square` | `.*.` donne `0*0` au lieu de `1*1`. |
| 6 | 🟢 `count the mines among the eight neighbours` | Implémentation évidente, la même qu'au jeu de la vie : 8 décalages, une case hors champ n'est pas une mine (`getOrNull`). |
| 7 | 📌 `solve the kata field with vertical and diagonal mines` | Le champ 4 × 4 de l'énoncé et une case entourée de 8 mines passent. |
| 8 | 🔴 `solve the acceptance input` | `Minesweeper` n'existe pas. |
| 9 | 🟢 `read fields until 0 0 and print their hints` | Un curseur parcourt les lignes : en-tête, `n` lignes de champ, et ainsi de suite jusqu'à `0 0`. |
| 10 | 🔴 `reject a field larger than its header` | Une ligne de 3 caractères pour 2 colonnes déclarées passe. |
| 11 | 🟢 `check each row against the declared width` | Un `require` par champ. |

## Solution

- `Field` : calcule les indices d'un champ, sans rien savoir du format de fichier.
- `Minesweeper.solve` : découpe l'entrée, délègue à `Field`, met en forme la sortie.

## Ce que j'en retiens

Le test d'acceptation de l'énoncé a été écrit **après** que le cœur était prêt : il n'a demandé que la lecture du format. Séparer le calcul de l'entrée/sortie évite d'écrire tous les tests du calcul avec des chaînes multi-lignes.

## Lancer les tests

```bash
./gradlew :katas:minesweeper:test
git log --reverse --format='%s%n%b' strict-tdd-start.. -- katas/minesweeper   # l'historique TDD
```
