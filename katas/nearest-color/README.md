# Nearest Color

🇬🇧 [English version](README.en.md) · Énoncé : [codingdojo.org/kata/NearestColor](https://codingdojo.org/kata/NearestColor/)

## Le kata

Une couleur s'écrit en hexadécimal, ici sur 3 chiffres (`F42` vaut `FF4422`).

- **Partie 1** : dans un ensemble de couleurs (`F00`, `0F0`, `00F`), trouver la plus proche d'une couleur donnée (`F42` → `F00`).
- **Partie 2** : en cas d'égalité, les donner toutes (`FF0` est aussi proche de `F00` que de `0F0`).
- **Bonus** : couleurs à 6 chiffres, couleur la plus éloignée, comparaison avec les noms de couleurs CSS (ce dernier bonus n'est pas traité).

## Démarche TDD

🔴 rouge · 🟢 vert · 🔵 refactor · 📌 test vert du premier coup

| # | Étape | Ce qui s'est passé |
|---|---|---|
| 1 | 🔴 `find a color that is in the palette` | Compilation impossible. |
| 2 | 🟢 `answer the color itself` | `return color`. |
| 3 | 🔴 `find the nearest primary of F42` | Attendu `F00`, obtenu `F42`. |
| 4 | 🟢 `pick the palette color at the smallest RGB distance` | Chaque chiffre est doublé (`F` → `FF` = 255), puis distance euclidienne au carré (la racine est inutile pour comparer). |
| 5 | 🔴 `list every nearest color in case of a tie` | `nearestColors` n'existe pas. |
| 6 | 🟢 `keep every color at the minimal distance` | La distance minimale, puis toutes les couleurs qui l'atteignent. |
| 7 | 🔴 `accept six-digit colors` | `FF0000` est lu chiffre par chiffre : mauvais résultat. |
| 8 | 🟢 `read three or six hexadecimal digits` | Six chiffres se lisent par paires. |
| 9 | 🔵 `rank palette colors by distance once` | `nearest` devient le premier des `nearestColors`, et le calcul des distances est factorisé dans `closest`. |
| 10 | 🔴 `find the farthest color` | `farthestColors` n'existe pas. |
| 11 | 🟢 `find the farthest colors with the same ranking` | `closest` avec `max` au lieu de `min` : une ligne grâce au refactoring précédent. |
| 12 | 🔴 `reject colors that are not hexadecimal` | `F4` et une palette vide sont acceptés. |
| 13 | 🟢 `validate colors and palettes` | 3 ou 6 chiffres hexadécimaux, palette non vide (vérifiée à la construction). |

## Solution

```kotlin
fun nearestColors(color: String) = closest(color) { distances -> distances.min() }
fun farthestColors(color: String) = closest(color) { distances -> distances.max() }
```

## Ce que j'en retiens

La partie 2 (égalités) a changé le type de retour, de « une couleur » à « des couleurs ». Garder `nearest` comme cas particulier de `nearestColors` a évité deux algorithmes, et le refactoring a rendu le bonus « la plus éloignée » quasi gratuit.

## Lancer les tests

```bash
./gradlew :katas:nearest-color:test
git log --reverse --format='%s%n%b' strict-tdd-start.. -- katas/nearest-color   # l'historique TDD
```
