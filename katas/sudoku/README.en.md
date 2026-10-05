# Sudoku, the concurrent resolver

🇫🇷 [Version française](README.md) · Kata: [codingdojo.org/kata/sudoku](https://codingdojo.org/kata/sudoku/)

## The kata

Solve a sudoku with cooperating actors, to practise TDD on concurrent code:

- a **cell** knows which numbers are still possible: unknown value, known (a single one possible) or impossible (none);
- a **grid** is a 3 × 3 square of cells: a known value is excluded from the other cells;
- a **region** (A to I) holds a grid and has four inputs and four outputs (north, east, south, west), connected as a torus to the neighbouring regions; when its grid discovers a value, it sends it to the display and to its four outputs; a message coming from north or south excludes the value from the column, from east or west from the row, then keeps going straight.

In the kata, each region is a **REST server**. Here, each region is an **actor**: a coroutine reading its mailbox (`Channel`), on the default thread pool. The protocol is the same (a message carries the row, the column, the value and the path of regions it went through). Changing the transport would not have changed the rules, and nine HTTP servers would have made the tests slow and brittle.

## TDD walkthrough

🔴 red · 🟢 green · 🔵 refactor · 📌 test green on first run

Inside out: cell, grid, an isolated region (outputs recorded by the test), then the concurrent network.

| # | Step | What happened |
|---|---|---|
| 1 | 🔴 `start a cell with every number possible` | Does not compile. |
| 2 | 🟢 `start a cell with every number possible` | A set of possible numbers. |
| 3 | 🔴 `know a cell's value once a single number is left` | `exclude` and `Known` do not exist. |
| 4 | 🟢 `know a cell once a single number is possible` | |
| 5 | 🔴 `report a contradiction when no number is left` | `Impossible` does not exist. |
| 6 | 🟢 `report a contradiction in a cell` | |
| 7 | 🔴 `exclude a known value from the rest of the grid` | `Grid` does not exist. |
| 8 | 🟢 `exclude a known value from the rest of the grid` | The value set is removed from the eight other cells, and the discovery is reported. |
| 9 | 🔴 `discover a cell left with a single number` | A cell dropping to a single possibility is not reported. |
| 10 | 🟢 `propagate discoveries inside a grid` | Any exclusion making a cell known triggers a discovery, reported once, then propagated. |
| 11 | 🔴 `exclude a value from a row or a column of the grid` | The two operations messages need. |
| 12 | 🟢 `exclude a value from a row or a column` | |
| 13 | 🔴 `broadcast a discovery to the display and every neighbour` | `Region` does not exist. |
| 14 | 🟢 `broadcast a region's discoveries` | The region sends each discovery to the display and its four outputs. |
| 15 | 🔴 `apply a message from the north to the column and pass it south` | `receive` does not exist. |
| ⛔ | rejected green | My test checked the exclusion indirectly (by setting another value), and the grid discovered an unexpected 9 along the way: the test was wrong. Fixed to query the region directly (`isPossible`), amending the red commit before pushing. |
| 16 | 🟢 `apply vertical messages to the column and pass them on` | |
| 17 | 🔴 `apply a message from the east to the row and pass it west` | The message goes south instead of west. |
| 18 | 🟢 `apply horizontal messages to the row and pass them on` | A message continues in the direction opposite to where it came from. |
| 19 | 🔴 `stop a message that has gone all the way round` | A message back at its starting point would go round forever. |
| 20 | 🟢 `stop messages that have gone all the way round` | The message's path is used to stop it. |
| 21 | 🔴 `solve a puzzle with nine concurrent regions` | Wikipedia's puzzle, solution computed separately by a Python solver applying the same rules; the test is repeated 20 times, since message order varies between runs. |
| ⛔ | rejected green | A type error (`Char` instead of `String`): does not compile. |
| 22 | 🟢 `run the nine regions as concurrent actors` | Nine actors on a torus. Completion is detected by a count of messages in flight: incremented on sending and decremented **after** processing, so it only drops to zero once nothing is moving any more. |

## Takeaways

- All the logic (cell, grid, region) was tested without concurrency: the region only knows the `send` function, which the test replaces with a recorder.
- Concurrency only appears in the last cycle, in the wiring. Since each region processes its messages one at a time in its own coroutine, its state is never shared: no lock needed.
- These simple rules do not solve every sudoku (for instance, "the only place left for a number" is missing): the test puzzle was chosen, and checked, to fall within them.

## Running the tests

```bash
./gradlew :katas:sudoku:test
git log --reverse --format='%s%n%b' strict-tdd-start.. -- katas/sudoku   # the TDD history
```
