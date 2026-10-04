# Reversi

🇫🇷 [Version française](README.md) · Kata: [codingdojo.org/kata/Reversi](https://codingdojo.org/kata/Reversi/)

## The kata

Given a position (an 8 × 8 board and the player to move), list the legal moves. A move is legal if it flips at least one opponent piece.

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

Expected answer for black: `[C5, D6, E3, F4]`, or graphically with `0`s on the board.

## TDD walkthrough

🔴 red · 🟢 green · 🔵 refactor · 📌 test green on first run

A small test helper builds a board from the relevant rows only, to keep examples readable.

| # | Step | What happened |
|---|---|---|
| 1 | 🔴 `find no move without an opponent piece` | Does not compile. |
| 2 | 🟢 `find no move` | `emptyList()`. |
| 3 | 🔴 `capture a piece on the same row` | `...BW...`: expected `F4`. |
| 4 | 🟢 `look along the row for opponents closed by an own piece` | For each empty square, walk while seeing opponent pieces, then check that an own piece closes the run. Two directions only: left and right. |
| 5 | 🔴 `capture along columns and diagonals` | Vertical and diagonal captures are ignored. |
| 6 | 🟢 `look in all eight directions` | The direction list becomes the 8 neighbours: a single line changes. |
| 7 | 📌 `find the four moves of the kata example, for both players` | The kata's example for black **and** white, plus a multi-piece capture and an unclosed run. |
| 8 | 🔴 `mark the legal moves on the board` | The kata's graphical output: `showMoves` does not exist. |
| 9 | 🟢 `mark the legal moves on the board` | The search now returns squares; both outputs (coordinates and annotated board) use them. |
| 10 | 🔴 `reject malformed positions` | An incomplete board throws an `IndexOutOfBoundsException`. |
| 11 | 🟢 `validate the position before searching` | Two `require`s. |

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

## Takeaways

Starting with a single direction already produced a general `flips(dx, dy)` function: moving to eight directions cost one line of data.

## Running the tests

```bash
./gradlew :katas:reversi:test
git log --reverse --format='%s%n%b' strict-tdd-start.. -- katas/reversi   # the TDD history
```
