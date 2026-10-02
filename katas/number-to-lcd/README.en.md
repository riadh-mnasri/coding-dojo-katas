# Number to LCD

🇫🇷 [Version française](README.md) · Kata: [codingdojo.org/kata/NumberToLCD](https://codingdojo.org/kata/NumberToLCD/)

## The kata

**Part 1**: print a number in LCD-style digits, on 3 lines:

```
    _  _     _  _  _  _  _  _
  | _| _||_||_ |_   ||_||_|| |
  ||_  _|  | _||_|  ||_| _||_|
```

**Part 2** (not to be read before part 1 is done): make the width and height of the digits variable. With width 3 and height 2, a 2 becomes:

```
 ___
    |
    |
 ___
|
|
 ___
```

## An inconsistency in the kata

At size 1 both formats differ: part 1 fits on **3 lines** (the middle and bottom bars share the line of the vertical segments), whereas part 2's example gives each bar **its own line** (2 × height + 3 lines, hence 5 lines at size 1). I kept both, following each example: `render(n)` for the compact form, `render(n, width, height)` for the stretched one.

## TDD walkthrough

🔴 red · 🟢 green · 🔵 refactor · 📌 test green on first run

| # | Step | What happened |
|---|---|---|
| 1 | 🔴 `draw the digit 1` | Does not compile. |
| 2 | 🟢 `draw a hard-coded 1` | The three lines, hard-coded. |
| 3 | 🔴 `draw the digit 2` | The hard-coded 1 is exposed. |
| 4 | 🟢 `look digits up in a glyph table` | A `digit → 3 lines` table. |
| 5 | 🔴 `draw a number with several digits` | `1234567890` missing from the table. |
| 6 | 🟢 `draw every digit and put them side by side` | The ten glyphs, and for each line the concatenation of glyphs. |
| 7 | 🔴 `stretch a 2 to width 3 and height 2` | The `render(n, width, height)` overload does not exist. |
| 8 | 🟢 `stretch digits from their segments` | A hard-coded drawing cannot stretch: each digit becomes a **set of lit segments**, and each line is computed from them. |
| ⛔ | rejected refactor | My `typealias` was declared inside the object, which Kotlin forbids: compilation failed, commit refused. |
| 9 | 🔵 `derive the compact form from the segments too` | Part 1's compact form also uses the segments: the glyph table goes away, leaving a single description of the digits. |
| 10 | 📌 `stretch several digits` | `10` at width 2. |
| 11 | 🔴 `reject negative numbers and empty sizes` | A negative number throws a `NoSuchElementException` on `-`. |
| 12 | 🟢 `validate the number and the sizes` | Two `require`s. |

## Solution

```
 _a_
b   c
 _d_
e   f
 _g_
```

Each digit is the set of its lit segments (`2` = `a c d e g`). A display form is a list of "rows", each a function of a digit's segments, repeated for every digit of the number.

## Takeaways

The kata asks not to read part 2 too early. Part 1's drawing table had to be replaced in part 2, and that is the point: adapting to an unforeseen requirement. The final refactoring removed the duplication between both forms.

## Running the tests

```bash
./gradlew :katas:number-to-lcd:test
git log --reverse --format='%s%n%b' strict-tdd-start.. -- katas/number-to-lcd   # the TDD history
```
