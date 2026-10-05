# Sudoku, the concurrent resolver

🇬🇧 [English version](README.en.md) · Énoncé : [codingdojo.org/kata/sudoku](https://codingdojo.org/kata/sudoku/)

## Le kata

Résoudre un sudoku avec des acteurs qui coopèrent, pour pratiquer le TDD sur du code concurrent :

- une **cellule** sait quels nombres restent possibles : valeur inconnue, connue (un seul possible) ou impossible (aucun) ;
- une **grille** est un carré de 3 × 3 cellules : une valeur connue y est exclue des autres cellules ;
- une **région** (A à I) contient une grille et a quatre entrées et quatre sorties (nord, est, sud, ouest), reliées en tore aux régions voisines ; quand sa grille découvre une valeur, elle l'envoie à l'affichage et à ses quatre sorties ; un message venu du nord ou du sud exclut la valeur de la colonne, de l'est ou de l'ouest de la ligne, puis continue tout droit.

Dans l'énoncé, chaque région est un **serveur REST**. Ici, chaque région est un **acteur** : une coroutine qui lit sa boîte aux lettres (`Channel`), sur le pool de threads par défaut. Le protocole est le même (un message porte la ligne, la colonne, la valeur et le chemin des régions traversées). Changer de transport n'aurait pas changé les règles, et neuf serveurs HTTP auraient rendu les tests lents et fragiles.

## Démarche TDD

🔴 rouge · 🟢 vert · 🔵 refactor · 📌 test vert du premier coup

De l'intérieur vers l'extérieur : cellule, grille, région isolée (sorties enregistrées par le test), puis le réseau concurrent.

| # | Étape | Ce qui s'est passé |
|---|---|---|
| 1 | 🔴 `start a cell with every number possible` | Compilation impossible. |
| 2 | 🟢 `start a cell with every number possible` | Un ensemble des nombres possibles. |
| 3 | 🔴 `know a cell's value once a single number is left` | `exclude` et `Known` n'existent pas. |
| 4 | 🟢 `know a cell once a single number is possible` | |
| 5 | 🔴 `report a contradiction when no number is left` | `Impossible` n'existe pas. |
| 6 | 🟢 `report a contradiction in a cell` | |
| 7 | 🔴 `exclude a known value from the rest of the grid` | `Grid` n'existe pas. |
| 8 | 🟢 `exclude a known value from the rest of the grid` | La valeur fixée est retirée des huit autres cellules, et la découverte est signalée. |
| 9 | 🔴 `discover a cell left with a single number` | Une cellule qui tombe à un seul possible n'est pas signalée. |
| 10 | 🟢 `propagate discoveries inside a grid` | Toute exclusion qui rend une cellule connue déclenche une découverte, signalée une seule fois, puis propagée. |
| 11 | 🔴 `exclude a value from a row or a column of the grid` | Les deux opérations dont les messages ont besoin. |
| 12 | 🟢 `exclude a value from a row or a column` | |
| 13 | 🔴 `broadcast a discovery to the display and every neighbour` | `Region` n'existe pas. |
| 14 | 🟢 `broadcast a region's discoveries` | La région envoie chaque découverte à l'affichage et à ses quatre sorties. |
| 15 | 🔴 `apply a message from the north to the column and pass it south` | `receive` n'existe pas. |
| ⛔ | vert refusé | Mon test vérifiait l'exclusion de façon détournée (en fixant une autre valeur), et la grille découvrait en route un 9 imprévu : c'était le test qui se trompait. Corrigé pour interroger directement la région (`isPossible`), en amendant le commit rouge avant de pousser. |
| 16 | 🟢 `apply vertical messages to the column and pass them on` | |
| 17 | 🔴 `apply a message from the east to the row and pass it west` | Le message part au sud au lieu de l'ouest. |
| 18 | 🟢 `apply horizontal messages to the row and pass them on` | Un message continue vers la direction opposée à celle d'où il vient. |
| 19 | 🔴 `stop a message that has gone all the way round` | Un message revenu à son point de départ repartirait indéfiniment. |
| 20 | 🟢 `stop messages that have gone all the way round` | Le chemin du message sert à l'arrêter. |
| 21 | 🔴 `solve a puzzle with nine concurrent regions` | La grille de Wikipédia, solution calculée à part par un solveur Python appliquant les mêmes règles ; test répété 20 fois, car l'ordre des messages varie d'une exécution à l'autre. |
| ⛔ | vert refusé | Une erreur de type (`Char` au lieu de `String`) : compilation impossible. |
| 22 | 🟢 `run the nine regions as concurrent actors` | Neuf acteurs en tore. La fin est détectée par un compteur de messages en transit : il est incrémenté à l'envoi et décrémenté **après** le traitement, donc il ne tombe à zéro que lorsque plus rien ne circule. |

## Ce que j'en retiens

- Toute la logique (cellule, grille, région) a été testée sans concurrence : la région ne connaît que la fonction `send`, que le test remplace par un enregistreur.
- La concurrence n'apparaît qu'au dernier cycle, dans l'assemblage. Comme chaque région traite ses messages une à une dans sa propre coroutine, son état n'est jamais partagé : pas de verrou.
- Ces règles simples ne résolvent pas tous les sudokus (il manque par exemple « le seul endroit possible pour un nombre ») : la grille de test a été choisie, et vérifiée, pour en relever.

## Lancer les tests

```bash
./gradlew :katas:sudoku:test
git log --reverse --format='%s%n%b' strict-tdd-start.. -- katas/sudoku   # l'historique TDD
```
