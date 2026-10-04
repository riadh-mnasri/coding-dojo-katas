# Eight Queens

🇫🇷 [Version française](README.md) · Kata: [codingdojo.org/kata/eight-queens](https://codingdojo.org/kata/eight-queens/)

## The kata

Place eight queens on a chessboard so that none can capture another. The kata asks for **all** solutions, then for several approaches to compare: depth-first, breadth-first, a heuristic (min-conflicts, genetic, simulated annealing...) and brute force with bit masks.

A solution is the column of the queen on each row.

## TDD walkthrough

🔴 red · 🟢 green · 🔵 refactor · 📌 test green on first run

The first implementation (depth-first) is the reference; the others are tested **against it**, and an independent validator checks boards.

| # | Step | What happened |
|---|---|---|
| 1 | 🔴 `solve the one-square board` | Does not compile. |
| 2 | 🟢 `solve the one-square board` | A hard-coded answer. |
| 3 | 🔴 `find the two solutions of the four-queens board` | `[1,3,0,2]` and `[2,0,3,1]` expected. |
| 4 | 🟢 `place queens row by row with depth-first backtracking` | One queen per row, only on a safe square, backtracking when a row has none. Recursion carries the backtracking. |
| ⛔ | rejected pin | I meant to commit the 92-solutions test as 📌, but it used a `Board` validator not written yet: refused by the guard (it did not compile), then committed as red. |
| 5 | 🔴 `check the 92 solutions with an independent validator` | 92 is the known number of solutions; each one is checked by a validator testing **every pair** of queens, without reusing the search code. |
| 6 | 🟢 `validate a whole board pair by pair` | The validator. The 92 solutions pass. |
| 7 | 🔴 `find the same solutions breadth-first` | `BreadthFirst` does not exist. |
| 8 | 🟢 `extend every partial board one row at a time` | A row-by-row `fold` over every partial position. |
| 9 | 📌 `make sure the validator rejects attacking queens` | The validator had only been tested on valid boards. |
| 10 | 🔴 `find the same solutions by brute force with bit masks` | `BruteForce` does not exist. |
| 11 | 🟢 `check every permutation with shifted bit masks` | Column permutations (8! = 40,320) settle rows and columns; diagonals are checked by shifting each row's bit by its row number, as the kata describes. |
| 12 | 🔴 `find one solution with the min-conflicts heuristic` | One solution for 8 **and for 100** queens. |
| 13 | 🟢 `repair a random board by minimising conflicts` | A random board, then an attacked queen moves to the least attacked column. Fixed seed for reproducible tests. |

## Comparison

Durations from one test run (warm JVM or not, so indicative only):

| Approach | Result | Duration | Readability |
|---|---|---|---|
| Depth-first | all 92 solutions | ~15 ms | the most natural: recursion *is* the backtracking |
| Breadth-first | all 92 solutions | ~15 ms | a short `fold`, but keeps every partial position of a row in memory |
| Brute force + masks | all 92 solutions | ~100 ms | clever but less obvious; walks all 40,320 permutations without pruning |
| Min-conflicts | **one** solution | ~30 ms for 8 **and** 100 queens | the only one that scales; guarantees neither every solution nor a bounded time |

Not done: the genetic algorithm and simulated annealing.

## Takeaways

A reference implementation and an independent validator made every new approach testable in one line ("same solutions as the reference"). The only test that did not pass on first run was the validator's own, first checked in one direction only.

## Running the tests

```bash
./gradlew :katas:eight-queens:test
git log --reverse --format='%s%n%b' strict-tdd-start.. -- katas/eight-queens   # the TDD history
```
