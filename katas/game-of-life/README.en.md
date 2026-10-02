# Game of Life

🇫🇷 [Version française](README.md) · Kata: [codingdojo.org/kata/GameOfLife](https://codingdojo.org/kata/GameOfLife/)

## The kata

Compute the next generation of Conway's Game of Life on a **finite** grid (no life beyond the edges):

1. a live cell with fewer than 2 neighbours dies;
2. a live cell with more than 3 neighbours dies;
3. a live cell with 2 or 3 neighbours lives on;
4. a dead cell with exactly 3 neighbours is born.

Input and output use the `Generation N:` / `rows columns` / grid of `.` and `*` format.

## TDD walkthrough

🔴 red · 🟢 green · 🔵 refactor · 📌 test green on first run

I worked **inside out**: a cell's rules, then the grid, then the file format.

| # | Step | What happened |
|---|---|---|
| 1 | 🔴 `kill a lonely live cell` | Does not compile: `Rules` does not exist. |
| 2 | 🟢 `every cell dies for now` | `return false`. |
| 3 | 🔴 `keep a live cell with two or three neighbours` | 2 and 3 neighbours: expected alive. |
| 4 | 🟢 `keep cells with two or three neighbours` | `liveNeighbours in 2..3`. |
| 5 | 📌 `kill an overcrowded live cell` | 4, 5, 8 neighbours: already dead. |
| 6 | 🔴 `keep a dead cell with two neighbours dead` | A dead cell with 2 neighbours "survives": the rule ignores the state. |
| 7 | 🟢 `only live cells can survive` | `alive && ...`. |
| 8 | 🔴 `bring a dead cell with three neighbours to life` | No birth. |
| 9 | 🟢 `give birth with exactly three neighbours` | A `when` on the state: the four rules in two lines. |
| 10 | 🔴 `let a lonely cell die on a grid` | Does not compile: `Grid` does not exist. |
| 11 | 🟢 `parse a grid and kill everything` | A `data class` over a list of lists, `next()` kills everything. |
| 12 | 🔴 `make a blinker oscillate` | The blinker vanishes instead of rotating. |
| 13 | 🟢 `apply the rules with each cell's live neighbours` | Count the 8 neighbours through offsets; a cell outside the grid is dead (`getOrNull`). |
| 14 | 📌 `handle births and deaths on the edges` | A birth in a corner and a stable block: green, `getOrNull` already handles the edges. |
| 15 | 🔴 `read and write the generation file format` | `GenerationFile` does not exist. |
| 16 | 🟢 `read the header, compute and print the next generation` | Read the header, `Grid.render()`, increment the generation number. |
| 17 | 🔴 `reject a grid that does not match its declared size` | A wrong declared size goes through silently. |
| 18 | 🟢 `check the grid against its declared size` | Rows × columns check. |

## Solution

- `Rules`: the decision for one cell, with no notion of grid.
- `Grid`: immutable; `next()` builds a new grid by applying `Rules` with each cell's live neighbour count.
- `GenerationFile`: the only place that knows the text format.

## Takeaways

Starting with the rules, isolated from the grid, gives very simple tests (a boolean and an integer). The grid then only counts neighbours, and the edges come down to one decision: "outside the grid means dead".

## Running the tests

```bash
./gradlew :katas:game-of-life:test
git log --reverse --format='%s%n%b' strict-tdd-start.. -- katas/game-of-life   # the TDD history
```
