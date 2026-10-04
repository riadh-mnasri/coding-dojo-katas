# Bank OCR

🇫🇷 [Version française](README.md) · Kata: [codingdojo.org/kata/BankOCR](https://codingdojo.org/kata/BankOCR/)

## The kata

A machine scans documents and outputs account numbers drawn with `|` and `_`, 9 digits on 3 lines (plus a blank line):

```
    _  _     _  _  _  _  _
  | _| _||_||_ |_   ||_||_|
  ||_  _|  | _||_|  ||_| _|
```

1. read these drawings;
2. validate the checksum: `(d1 + 2×d2 + ... + 9×d9) mod 11 = 0`, where d1 is the **rightmost** digit;
3. produce a report: `ILL` when a digit is illegible (replaced by `?`), `ERR` when the checksum is wrong;
4. for `ILL` or `ERR`, try to fix it by adding or removing **a single stroke**: one valid fix → use it; several → `AMB` with the list; none → `ILL`.

## Test data

The kata's grids contain meaningful spaces (and fully blank lines). Rather than copying them by hand, I extracted them from the kata's source into three resource files (`use-case-1.txt`, `-3`, `-4`), read as is by parameterised tests.

## TDD walkthrough

🔴 red · 🟢 green · 🔵 refactor · 📌 test green on first run

| # | Step | What happened |
|---|---|---|
| 1 | 🔴 `read an entry of zeros, from the kata fixtures` | Does not compile. |
| 2 | 🟢 `cut an entry into 3x3 cells and recognise zero` | Split into 3 × 3 cells, a recognition table limited to 0. |
| 3 | 🔴 `read every entry of use case 1` | The ten other entries: unknown cells. |
| 4 | 🟢 `recognise the ten digits` | The full table, written over 3 lines per digit as the kata advises, so the digits can be **seen** in the code. |
| 5 | 🔴 `validate account numbers with the checksum` | Story 2: `isValid` does not exist. |
| 6 | 🟢 `compute the checksum with reversed positions` | The trap the kata warns about: positions count from the right, hence `reversed()`. |
| 7 | 🔴 `report illegible and erroneous numbers` | Story 3: `report` does not exist. |
| 8 | 🟢 `mark illegible digits and report ILL or ERR` | An unknown cell becomes `?`. |
| 9 | 🔴 `guess numbers from one missing or extra stroke` | Story 4: the kata's 12 cases, `AMB`s included. |
| 10 | 🟢 `try every one-stroke change and keep the valid numbers` | For each cell, the digits whose drawing differs by a single character (necessarily a stroke added or removed, since `_` and `|` have fixed places), then keep valid numbers. The story 3 report is kept separately, since story 4 changes the answer for the same entries. |
| 11 | 🔴 `report a whole file, one account per line` | `reportFile` does not exist. |
| 12 | 🟢 `report every entry of a file` | The file is split into 4-line blocks. |

## Solution

```kotlin
val guesses = cells.indices
    .flatMap { position -> oneStrokeAway(cells[position]).map { account.replaceRange(position, position + 1, "$it") } }
    .filter { '?' !in it && isValid(it) }
    .distinct().sorted()
```

## Takeaways

- The kata's "gotchas" (reversed checksum positions, fixable digits not all listed, `?` to fix too) are all covered by the original data, hence the value of extracting it as is instead of rewriting it.
- With a drawing table, "one stroke apart" becomes "one character apart": story 4, reputed hard, fits in about ten lines.

## Running the tests

```bash
./gradlew :katas:bank-ocr:test
git log --reverse --format='%s%n%b' strict-tdd-start.. -- katas/bank-ocr   # the TDD history
```
