# Roman Numerals

🇫🇷 [Version française](README.md) · Kata: [codingdojo.org/kata/RomanNumerals](https://codingdojo.org/kata/RomanNumerals/)

## The kata

- **Part I**: convert an integer (1 to ~3000) to Roman numerals (`7 → VII`).
- **Part II**: convert the other way round.

## TDD walkthrough

🔴 red · 🟢 green · 🔵 refactor · 📌 test green on first run

| # | Step | What happened |
|---|---|---|
| 1 | 🔴 `convert 1 to I` | Does not compile: `RomanNumerals` does not exist. |
| 2 | 🟢 `fake I for 1` | `return "I"`. |
| 3 | 🔴 `repeat I for 2 and 3` | Expected `"II"`, got `"I"`. |
| 4 | 🟢 `repeat I` | `"I".repeat(number)`. |
| 5 | 🔴 `convert 5 and 6` | Expected `"V"`, got `"IIIII"`. |
| 6 | 🟢 `use V before the I` | An `if (remaining >= 5)` then the remaining `I`s. |
| 7 | 🔴 `convert 10 and 20` | Expected `"X"`, got `"VIIIII"`. |
| 8 | 🟢 `use X before V and I` | Three identical `while` loops, one per letter. Blatant duplication. |
| 9 | 🔵 `replace the three loops with a symbol table` | A `value → symbol` table walked from largest to smallest: the greedy algorithm emerges. |
| 10 | 🔴 `convert 4 and 9 with subtraction` | Expected `"IV"`, got `"IIII"`. |
| 11 | 🟢 `add IV and IX as symbols` | Instead of coding the subtractive rule, `IV` and `IX` are added **to the table**. The algorithm does not change. |
| 12 | 🔴 `convert L, C, D, M and their subtractions` | 8 failing cases (`50 → XXXXX`...). |
| 13 | 🟢 `complete the symbol table` | Six more data rows, no logic. |
| 14 | 📌 `convert full numbers` | `1990`, `2008`, `1666`, `3999` pass. |
| 15 | 🔴 `reject zero` | No exception thrown for `0`. |
| 16 | 🟢 `accept only 1 to 3999` | A `require`. |
| 17 | 🔴 `convert I back to 1` | Part II: `toArabic` does not exist. |
| 18 | 🟢 `fake 1 for I` | `return 1`. |
| 19 | 🔴 `add letter values back` | `III`: expected 3, got 1. |
| 20 | 🟢 `sum letter values` | Sum of the letter values. |
| 21 | 🔴 `handle subtraction when converting back` | `IV`: expected 4, got 6. |
| 22 | 🟢 `subtract a letter smaller than its neighbour` | Each letter is compared with its right neighbour. |
| 23 | 📌 `round-trip every supported number` | `toArabic(toRoman(n)) == n` for 1..3999: a safety net. |
| 24 | 🔴 `reject malformed numerals` | `IIII`, `VV`, `IC` are wrongly accepted, `ABC` throws the wrong exception. |
| 25 | 🟢 `reject numerals that are not in canonical form` | Rather than piling up validation rules, the canonical rewrite must give back the input string. |
| 26 | 🔵 `group the lookup tables` | Both tables side by side, a comment on the validation choice. |

## Solution

- Greedy algorithm over a 13-symbol table (7 letters + 6 subtractive pairs).
- The reverse direction compares each letter with its right neighbour.
- Validation reuses `toRoman`: a single source of truth for the correct form.

## Takeaways

Test order shaped the design: when `4` arrived, the table already existed (step 9), so subtractive pairs became plain data rows instead of special logic.

## Running the tests

```bash
./gradlew :katas:roman-numerals:test
git log --reverse --format='%s%n%b' strict-tdd-start.. -- katas/roman-numerals   # the TDD history
```
