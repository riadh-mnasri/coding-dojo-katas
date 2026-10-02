# Minesweeper

🇫🇷 [Version française](README.md) · Kata: [codingdojo.org/kata/Minesweeper](https://codingdojo.org/kata/Minesweeper/)

## The kata

For each minefield (`*` = mine, `.` = safe square), replace every safe square with the number of adjacent mines (up to 8). The input chains several fields preceded by their size and ends with `0 0`; the output numbers the fields (`Field #1:`) separated by an empty line.

## TDD walkthrough

🔴 red · 🟢 green · 🔵 refactor · 📌 test green on first run

First the hints of one field (`Field`), only then the input/output format (`Minesweeper`).

| # | Step | What happened |
|---|---|---|
| 1 | 🔴 `hint 0 for a lonely safe square` | Does not compile. |
| 2 | 🟢 `replace every square with 0` | Every square becomes `0`. |
| 3 | 🔴 `keep mines as they are` | `*` becomes `0`. |
| 4 | 🟢 `keep mines` | Mines stay mines. |
| 5 | 🔴 `count a mine next to a square` | `.*.` gives `0*0` instead of `1*1`. |
| 6 | 🟢 `count the mines among the eight neighbours` | Obvious implementation, the same as in Game of Life: 8 offsets, a square outside the field is no mine (`getOrNull`). |
| 7 | 📌 `solve the kata field with vertical and diagonal mines` | The kata's 4 × 4 field and a square surrounded by 8 mines pass. |
| 8 | 🔴 `solve the acceptance input` | `Minesweeper` does not exist. |
| 9 | 🟢 `read fields until 0 0 and print their hints` | A cursor walks the lines: header, `n` field rows, and so on until `0 0`. |
| 10 | 🔴 `reject a field larger than its header` | A 3-character row for 2 declared columns goes through. |
| 11 | 🟢 `check each row against the declared width` | One `require` per field. |

## Solution

- `Field`: computes a field's hints, knowing nothing about the file format.
- `Minesweeper.solve`: splits the input, delegates to `Field`, formats the output.

## Takeaways

The kata's acceptance test was written **after** the core was ready: it only required reading the format. Splitting computation from input/output avoids writing every computation test with multi-line strings.

## Running the tests

```bash
./gradlew :katas:minesweeper:test
git log --reverse --format='%s%n%b' strict-tdd-start.. -- katas/minesweeper   # the TDD history
```
