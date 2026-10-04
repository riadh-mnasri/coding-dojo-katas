# Numbers in Words

🇫🇷 [Version française](README.md) · Kata: [codingdojo.org/kata/NumbersInWords](https://codingdojo.org/kata/NumbersInWords/)

## The kata

Write a number in words, as on a cheque: `745` → "seven hundred and forty five dollars". **Step 2**: convert it back. **Step 3**: do all of it test-driven.

The kata spells "fourty"; I used the correct "forty", and "fourty" is precisely rejected when reading back.

## TDD walkthrough

🔴 red · 🟢 green · 🔵 refactor · 📌 test green on first run

| # | Step | What happened |
|---|---|---|
| 1 | 🔴 `say zero` | Does not compile. |
| 2 | 🟢 `say zero` | `return "zero"`. |
| 3 | 🔴 `say units and teens` | `one`, `seven`, `ten`, `thirteen`... |
| 4 | 🟢 `name numbers below twenty` | A list of 20 words: in English 11 to 19 are irregular, so just list them. |
| 5 | 🔴 `say tens with their units` | Index 20 outside the list. |
| 6 | 🟢 `say tens and their unit` | A list of tens, plus the unit if any. |
| 7 | 🔴 `say hundreds with and` | `745`: index outside the list. |
| 8 | 🟢 `say hundreds and link the rest with and` | `seven hundred and ...` by recursion on the rest. |
| 9 | 🔴 `say thousands and millions` | `1000` gives "ten hundred". |
| 10 | 🟢 `say numbers by groups of three digits` | The number is split into three-digit groups (millions, thousands, units), each said by the hundreds function. British rule: the last group takes "and" when below 100 (`one thousand and one`). |
| 11 | 🔴 `read words back into a number` | Step 2: `toNumber` does not exist. |
| 12 | 🟢 `read words back by accumulating groups` | Units and tens add up in a group, "hundred" multiplies it by 100, a scale ("thousand", "million") moves it into the total. **Slip**: I also added the rejection of an unknown word, which no test asked for. |
| 13 | 📌 `round-trip numbers through words` | `toNumber(toWords(n)) == n` for 0 to 20,000 and a few large numbers. |
| 14 | 📌 `cover unknown words, rejected without a red test` | Making up for the step 12 slip ("fourty" is rejected). This test could only pass. |
| 15 | 🔴 `accept only 0 to 999,999,999` | `-1` throws an `ArrayIndexOutOfBoundsException`. |
| 16 | 🟢 `reject numbers out of range` | A `require`. |
| 17 | 🔴 `write a cheque amount in dollars` | `dollars` does not exist. |
| 18 | 🟢 `write cheque amounts in dollars` | With the singular for 1. |

## Solution

- `toWords`: three-digit groups + `belowThousand`, with the "and" rule.
- `toNumber`: a group accumulator and a total.
- `dollars`: the cheque wording.

## Takeaways

- The round-trip test (step 13) checks both directions against each other over 20,000 values.
- Two 📌 validation tests (as in NimGame) reveal code written ahead of the tests. I left them visible in the history rather than dressing them up.

## Running the tests

```bash
./gradlew :katas:numbers-in-words:test
git log --reverse --format='%s%n%b' strict-tdd-start.. -- katas/numbers-in-words   # the TDD history
```
