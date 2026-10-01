# Roman Calculator

🇫🇷 [Version française](README.md) · Kata: [codingdojo.org/kata/RomanCalculator](https://codingdojo.org/kata/RomanCalculator/)

## The kata

Add two Roman numbers **without converting them to integers**: "we are in Rome, there is no such thing as decimals or `int`". Example: `XIV + LX = LXXIV`.

The kata deliberately gives no test cases: the whole point is finding the next one.

## TDD walkthrough

🔴 red · 🟢 green · 🔵 refactor · 📌 test green on first run

| # | Step | What happened |
|---|---|---|
| 1 | 🔴 `add I and I` | Does not compile: `RomanCalculator` does not exist. |
| 2 | 🟢 `concatenate both numerals` | `left + right`. |
| 3 | 🔴 `sort letters from the biggest` | `I + X`: expected `"XI"`, got `"IX"`. |
| 4 | 🟢 `sort letters from the biggest` | Sort following the `MDCLXVI` order. |
| 5 | 🔴 `group five I into V` | Expected `"V"`, got `"IIIII"`. |
| 6 | 🟢 `group five I into V` | A `replace("IIIII", "V")`. |
| 7 | 🔴 `group letters at every level` | `V + V`, `XXX + XX`, `D + D`... and `VIII + VII` (`VVV` instead of `XV`). |
| 8 | 🟢 `group letters at every level` | A grouping table walked **from smallest to largest** so carries propagate (`IIIII → V` creates a `VV`, which becomes `X`). |
| 9 | 🔴 `write four I as IV` | Expected `"IV"`, got `"IIII"`. |
| 10 | 🟢 `write four I as IV` | A final `replace("IIII", "IV")`. |
| 11 | 🔴 `write VIIII as IX` | `VII + II`: expected `"IX"`, got `"VIV"`. The classic trap. |
| 12 | 🟢 `write VIIII as IX before IIII as IV` | At each level, the long form must be rewritten before the short one. |
| 13 | 🔴 `use subtraction at every level` | `XL`, `XC`, `CD`, `CM` are missing. |
| 14 | 🟢 `use subtraction at every level` | An ordered `subtractives` table (`CM` before `CD`, `XC` before `XL`, `IX` before `IV`). |
| 15 | 🔴 `expand subtractive inputs before adding` | `IV + I`: expected `"V"`, got `"VII"`. Subtractive inputs must be expanded (`IV → IIII`) before sorting. |
| ⛔ | rejected green attempt | My first version expanded **after** concatenating. `VIII + VII` gives `VIIIVII`, which holds a fake `IV` at the junction. Three tests went red again and `scripts/tdd.sh` refused the commit. |
| 16 | 🟢 `expand each subtractive operand before adding` | Each operand is expanded on its own. |
| 17 | 🔵 `express addition as an expand, sort, group, compress pipeline` | The code finally reads like the algorithm: `compress(group(sort(expand(a) + expand(b))))`. |
| 18 | 📌 `carry through several levels` | `CMXCIX + I = M`, `MMCDXLIV + MCDXLIV = MMMDCCCLXXXVIII` pass. |

## Solution

```kotlin
fun add(left: String, right: String): String =
    compress(group(sort(expand(left) + expand(right))))
```

| Stage | Role | Example |
|---|---|---|
| `expand` | expand subtractive forms | `XIV → XIIII` |
| `sort` | sort letters from largest to smallest | `XIIII` + `LX` → `LXXIIII` |
| `group` | group with carry | `IIIII → V`, `VV → X` |
| `compress` | rewrite in subtractive form | `IIII → IV` |

The Roman rules are data (`groupings`, `subtractives`) that can be reviewed without reading the algorithm.

## Takeaways

- This is a **pick-the-next-test** kata: each test revealed one pipeline stage.
- The guard's refusal shows why all previous tests matter: the fix for test 15 broke three older cases, which an "eyeball" check would probably have missed.

## Running the tests

```bash
./gradlew :katas:roman-calculator:test
git log --reverse --format='%s%n%b' strict-tdd-start.. -- katas/roman-calculator   # the TDD history
```
