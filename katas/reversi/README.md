# Reversi

🇬🇧 [English version](README.en.md) · Énoncé : [codingdojo.org/kata/Reversi](https://codingdojo.org/kata/Reversi/)

## Le kata

À partir d'une position (un plateau 8 × 8 et le joueur dont c'est le tour), donner la liste des coups légaux. Un coup est légal s'il retourne au moins un pion adverse.

```
........
........
........
...BW...
...WB...
........
........
........
B
```

Réponse attendue pour les noirs : `[C5, D6, E3, F4]`, ou graphiquement avec des `0` sur le plateau.

## Démarche TDD

🔴 rouge · 🟢 vert · 🔵 refactor · 📌 test vert du premier coup

Un petit utilitaire de test fabrique un plateau à partir des seules lignes utiles, pour garder des exemples lisibles.

| # | Étape | Ce qui s'est passé |
|---|---|---|
| 1 | 🔴 `find no move without an opponent piece` | Compilation impossible. |
| 2 | 🟢 `find no move` | `emptyList()`. |
| 3 | 🔴 `capture a piece on the same row` | `...BW...` : attendu `F4`. |
| 4 | 🟢 `look along the row for opponents closed by an own piece` | Pour chaque case vide, on avance tant qu'on voit des pions adverses, puis on regarde si un pion à soi ferme la suite. Deux directions seulement : gauche et droite. |
| 5 | 🔴 `capture along columns and diagonals` | Les captures verticales et diagonales sont ignorées. |
| 6 | 🟢 `look in all eight directions` | La liste des directions devient les 8 voisins : une seule ligne change. |
| 7 | 📌 `find the four moves of the kata example, for both players` | L'exemple de l'énoncé pour les noirs **et** pour les blancs, plus une prise de plusieurs pions et une suite non fermée. |
| 8 | 🔴 `mark the legal moves on the board` | La sortie graphique de l'énoncé : `showMoves` n'existe pas. |
| 9 | 🟢 `mark the legal moves on the board` | La recherche renvoie désormais des cases ; les deux sorties (coordonnées et plateau annoté) s'en servent. |
| 10 | 🔴 `reject malformed positions` | Un plateau incomplet lève une `IndexOutOfBoundsException`. |
| 11 | 🟢 `validate the position before searching` | Deux `require`. |

## Solution

```kotlin
fun flips(column: Int, row: Int, dx: Int, dy: Int): Boolean {
    var x = column + dx
    var y = row + dy
    var seen = 0
    while (at(x, y) == opponent) { x += dx; y += dy; seen++ }
    return seen > 0 && at(x, y) == player
}
```

## Ce que j'en retiens

Avoir commencé par une seule direction a donné une fonction `flips(dx, dy)` déjà générale : passer à huit directions n'a coûté qu'une ligne de données.

## Lancer les tests

```bash
./gradlew :katas:reversi:test
git log --reverse --format='%s%n%b' strict-tdd-start.. -- katas/reversi   # l'historique TDD
```
