# Diamond

🇫🇷 [Version française](README.md) · Kata: [codingdojo.org/kata/Diamond](https://codingdojo.org/kata/Diamond/)

## The kata

Given a letter, print a diamond starting with `A`, with the given letter at its widest point:

```
  A
 B B
C   C
 B B
  A
```

## TDD walkthrough

🔴 red · 🟢 green · 🔵 refactor · 📌 test green on first run

This kata is known for its trap: after `A` and `B`, the `C` test asks for the whole algorithm at once. I followed Seb Rose's **recycled tests** approach: properties true for every letter from `A` to `Z`, each forcing one small decision, with the examples kept for the end.

| # | Step | What happened |
|---|---|---|
| 1 | 🔴 `print A for A` | Does not compile: `Diamond` does not exist. |
| 2 | 🟢 `fake the A diamond` | `return "A"`. |
| 3 | 🔴 `have 2n+1 rows` | Property over every letter: for `B`, expected 3 rows, got 1. |
| 4 | 🟢 `one row per letter, there and back` | Letters from `A` to the given one, then the mirror without repeating the middle row (`dropLast(1).reversed()`). Each row still only holds its letter. |
| 5 | 📌 `go from A to the letter and back` | Green at once: step 4 already set the letter order. |
| 6 | 🔴 `be as wide as it is high` | For `B`, maximum width 1 instead of 3. |
| 7 | 🟢 `write each letter twice with inner spaces` | `letter + (2 × rank - 1) spaces + letter`, except for `A`. |
| 8 | 🔴 `be horizontally symmetric` | Once padded on the right, rows are not symmetric: `"A  "` instead of `"  A"`. The outer margin is missing. |
| 9 | 🟢 `pad each row with outer spaces` | `size - rank` spaces before each row. |
| 10 | 📌 `never end a row with a space` | Green: no space was ever added on the right. |
| 11 | 📌 `match the B and C examples` | The examples, written last, pass unchanged. They serve as readable documentation. |
| 12 | 🔴 `reject anything but a capital letter` | `'a'`, `'1'`, `'@'` throw nothing. |
| 13 | 🟢 `accept only capital letters` | A `require`. |

## Solution

```kotlin
fun of(widest: Char): String {
    val size = widest - 'A'
    val topHalf = ('A'..widest).map { row(it, size) }
    return (topHalf + topHalf.dropLast(1).reversed()).joinToString("\n")
}

private fun row(letter: Char, size: Int): String {
    val index = letter - 'A'
    val outer = " ".repeat(size - index)
    return if (index == 0) "$outer$letter" else "$outer$letter${" ".repeat(2 * index - 1)}$letter"
}
```

## Takeaways

Property tests turn the "leap" between the `A` and `C` examples into a series of small decisions: row count, then width, then symmetry. The examples become 📌 steps that confirm the design instead of dictating it.

## Running the tests

```bash
./gradlew :katas:diamond:test
git log --reverse --format='%s%n%b' strict-tdd-start.. -- katas/diamond   # the TDD history
```
