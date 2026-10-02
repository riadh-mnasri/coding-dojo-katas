# String Calculator

🇫🇷 [Version française](README.md) · Kata: [codingdojo.org/kata/StringCalculator](https://codingdojo.org/kata/StringCalculator/)

## The kata

`add(String): String` sums decimal numbers separated by `,` or a newline, and returns the result **or a precise error message**:

| Input | Output |
|---|---|
| `""` | `0` |
| `"1.1,2.2"` | `3.3` |
| `"175.2,\n35"` | `Number expected but '\n' found at position 6.` |
| `"1,3,"` | `Number expected but EOF found.` |
| `"//sep\n2sep3"` | `5` |
| `"//\|\n1\|2,3"` | `'\|' expected but ',' found at position 3.` |
| `"2,-4,-5"` | `Negative not allowed : -4, -5` |
| `"-1,,2"` | `Negative not allowed : -1` + newline + `Number expected but ',' found at position 3.` |

Then: handle errors with something better than strings internally, and write `multiply` with the same rules.

## TDD walkthrough

🔴 red · 🟢 green · 🔵 refactor · 📌 test green on first run

| # | Step | What happened |
|---|---|---|
| 1 | 🔴 `sum an empty input to 0` | Does not compile. |
| 2 | 🟢 `return 0 for an empty input` | `return "0"`. |
| 3 | 🔴 `sum one or two decimal numbers` | `1`, `1.1,2.2`, `2,3` all give `0`. |
| 4 | 🟢 `sum comma separated decimals exactly` | `BigDecimal` rather than `Double`: `1.1 + 2.2` must print `3.3`, not `3.3000000000000003`. `stripTrailingZeros` prints `5`, not `5.0`. |
| 5 | 📌 `sum any amount of numbers` | `split` already handled any number of values. |
| 6 | 🔴 `accept newlines as separators` | `NumberFormatException`. |
| 7 | 🟢 `split on newlines too` | A second separator in `split`. |
| 8 | 🔴 `report a separator where a number is expected` | `split` loses positions: no way to say "position 6". |
| 9 | 🟢 `scan the input to locate unexpected separators` | **Model change**: `split` is dropped for a character-by-character scan (`Regex.matchAt` reads a number at a given position). |
| 10 | 🔴 `refuse a trailing separator` | `StringIndexOutOfBoundsException` at the end of the string. |
| 11 | 🟢 `report EOF when a number is missing at the end` | Check for the end of text before reading a number. |
| 12 | 🔴 `accept a custom separator` | `//` is read as a missing number. |
| 13 | 🟢 `read a custom separator from the first line` | The header sets the separators; positions are counted **after** the header, as in the kata's example. |
| 14 | 🔴 `report a wrong separator` | `NoSuchElementException` on an undeclared comma. |
| 15 | 🟢 `report the expected separator` | Message `'|' expected but ',' found at position 3.` |
| 16 | 🔴 `refuse negative numbers` | `-` is not recognised as the start of a number. |
| 17 | 🟢 `list the negative numbers refused` | The pattern accepts a sign, negatives are collected then refused together. |
| 18 | 🔴 `report every error at once` | Scanning stops at the first error, the negative is never reported. |
| 19 | 🟢 `keep scanning after an error to report them all` | Errors are accumulated and scanning resumes at the next character. |
| 20 | 🔵 `separate scanning, typed outcome and rendering` | Three responsibilities: `Scanner` (reading and positions), `compute` returning a typed `Outcome` (`Value` or `Failure`), and `render` producing the string. This is the kata's "Errors management" step, sum-type flavour. |
| 21 | 🔴 `multiply with the same rules` | `multiply` does not exist. |
| 22 | 🟢 `multiply through the same pipeline` | One line: `compute(input, BigDecimal.ONE, BigDecimal::multiply)`. Choice: an empty input is 1, the neutral element. |
| 23 | 📌 `expose errors as a typed outcome internally` | The test documents the internal contract: a `Failure` holding the ordered messages. |

## Solution

```kotlin
fun add(input: String): String = render(compute(input, BigDecimal.ZERO, BigDecimal::add))
fun multiply(input: String): String = render(compute(input, BigDecimal.ONE, BigDecimal::multiply))
```

- `Scanner` reads number, separator, number... and records each problem with its position, without stopping.
- `compute` adds the negative rule and returns `Outcome.Value` or `Outcome.Failure`.
- Only `render` knows the text format required by the kata.

## Takeaways

Step 8 is the pivot: a `split` cannot answer "where is the error?". Changing the model early made every later message simple. And moving errors out of `String` (step 20) made multiplication trivial.

## Running the tests

```bash
./gradlew :katas:string-calculator:test
git log --reverse --format='%s%n%b' strict-tdd-start.. -- katas/string-calculator   # the TDD history
```
