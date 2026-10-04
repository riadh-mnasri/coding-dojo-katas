# Nearest Color

🇫🇷 [Version française](README.md) · Kata: [codingdojo.org/kata/NearestColor](https://codingdojo.org/kata/NearestColor/)

## The kata

A color is written in hexadecimal, here with 3 digits (`F42` means `FF4422`).

- **Part 1**: in a set of colors (`F00`, `0F0`, `00F`), find the one nearest to a given color (`F42` → `F00`).
- **Part 2**: on a tie, give them all (`FF0` is as near to `F00` as to `0F0`).
- **Bonus**: 6-digit colors, farthest color, comparison with CSS color keywords (that last bonus is not done).

## TDD walkthrough

🔴 red · 🟢 green · 🔵 refactor · 📌 test green on first run

| # | Step | What happened |
|---|---|---|
| 1 | 🔴 `find a color that is in the palette` | Does not compile. |
| 2 | 🟢 `answer the color itself` | `return color`. |
| 3 | 🔴 `find the nearest primary of F42` | Expected `F00`, got `F42`. |
| 4 | 🟢 `pick the palette color at the smallest RGB distance` | Each digit is doubled (`F` → `FF` = 255), then the squared Euclidean distance (no square root needed to compare). |
| 5 | 🔴 `list every nearest color in case of a tie` | `nearestColors` does not exist. |
| 6 | 🟢 `keep every color at the minimal distance` | The minimal distance, then every color reaching it. |
| 7 | 🔴 `accept six-digit colors` | `FF0000` is read digit by digit: wrong result. |
| 8 | 🟢 `read three or six hexadecimal digits` | Six digits are read in pairs. |
| 9 | 🔵 `rank palette colors by distance once` | `nearest` becomes the first of `nearestColors`, and the distance computation moves into `closest`. |
| 10 | 🔴 `find the farthest color` | `farthestColors` does not exist. |
| 11 | 🟢 `find the farthest colors with the same ranking` | `closest` with `max` instead of `min`: one line thanks to the previous refactoring. |
| 12 | 🔴 `reject colors that are not hexadecimal` | `F4` and an empty palette are accepted. |
| 13 | 🟢 `validate colors and palettes` | 3 or 6 hexadecimal digits, non-empty palette (checked at construction). |

## Solution

```kotlin
fun nearestColors(color: String) = closest(color) { distances -> distances.min() }
fun farthestColors(color: String) = closest(color) { distances -> distances.max() }
```

## Takeaways

Part 2 (ties) changed the return type from "a color" to "colors". Keeping `nearest` as a special case of `nearestColors` avoided two algorithms, and the refactoring made the "farthest" bonus nearly free.

## Running the tests

```bash
./gradlew :katas:nearest-color:test
git log --reverse --format='%s%n%b' strict-tdd-start.. -- katas/nearest-color   # the TDD history
```
