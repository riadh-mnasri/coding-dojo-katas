# Game of Life (jeu de la vie)

🇬🇧 [English version](README.en.md) · Énoncé : [codingdojo.org/kata/GameOfLife](https://codingdojo.org/kata/GameOfLife/)

## Le kata

Calculer la génération suivante du jeu de la vie de Conway sur une grille **finie** (pas de vie au-delà des bords) :

1. une cellule vivante avec moins de 2 voisins meurt ;
2. une cellule vivante avec plus de 3 voisins meurt ;
3. une cellule vivante avec 2 ou 3 voisins survit ;
4. une cellule morte avec exactement 3 voisins naît.

Entrée et sortie au format `Generation N:` / `lignes colonnes` / grille de `.` et `*`.

## Démarche TDD

🔴 rouge · 🟢 vert · 🔵 refactor · 📌 test vert du premier coup

J'ai travaillé **de l'intérieur vers l'extérieur** : les règles d'une cellule, puis la grille, puis le format de fichier.

| # | Étape | Ce qui s'est passé |
|---|---|---|
| 1 | 🔴 `kill a lonely live cell` | Compilation impossible : `Rules` n'existe pas. |
| 2 | 🟢 `every cell dies for now` | `return false`. |
| 3 | 🔴 `keep a live cell with two or three neighbours` | 2 et 3 voisins : attendu vivant. |
| 4 | 🟢 `keep cells with two or three neighbours` | `liveNeighbours in 2..3`. |
| 5 | 📌 `kill an overcrowded live cell` | 4, 5, 8 voisins : déjà mort. |
| 6 | 🔴 `keep a dead cell with two neighbours dead` | Une cellule morte avec 2 voisins « survit » : la règle ignore l'état. |
| 7 | 🟢 `only live cells can survive` | `alive && ...`. |
| 8 | 🔴 `bring a dead cell with three neighbours to life` | Pas de naissance. |
| 9 | 🟢 `give birth with exactly three neighbours` | Un `when` sur l'état : les quatre règles en deux lignes. |
| 10 | 🔴 `let a lonely cell die on a grid` | Compilation impossible : `Grid` n'existe pas. |
| 11 | 🟢 `parse a grid and kill everything` | Une `data class` sur une liste de listes, `next()` tue tout. |
| 12 | 🔴 `make a blinker oscillate` | Le clignotant disparaît au lieu de pivoter. |
| 13 | 🟢 `apply the rules with each cell's live neighbours` | Comptage des 8 voisins par décalages ; une case hors grille est morte (`getOrNull`). |
| 14 | 📌 `handle births and deaths on the edges` | Naissance dans un coin et bloc stable : verts, le `getOrNull` gère déjà les bords. |
| 15 | 🔴 `read and write the generation file format` | `GenerationFile` n'existe pas. |
| 16 | 🟢 `read the header, compute and print the next generation` | Lecture de l'en-tête, `Grid.render()`, numéro de génération incrémenté. |
| 17 | 🔴 `reject a grid that does not match its declared size` | Une taille déclarée fausse passe sans erreur. |
| 18 | 🟢 `check the grid against its declared size` | Vérification lignes × colonnes. |

## Solution

- `Rules` : la décision pour une cellule, sans aucune notion de grille.
- `Grid` : immuable ; `next()` construit une nouvelle grille en appliquant `Rules` avec le nombre de voisins vivants.
- `GenerationFile` : seul endroit qui connaît le format texte.

## Ce que j'en retiens

Commencer par les règles, isolées de la grille, donne des tests très simples (un booléen et un entier). La grille n'a plus qu'à compter les voisins, et les bords sont gérés par une seule décision : « hors grille, c'est mort ».

## Lancer les tests

```bash
./gradlew :katas:game-of-life:test
git log --reverse --format='%s%n%b' strict-tdd-start.. -- katas/game-of-life   # l'historique TDD
```
