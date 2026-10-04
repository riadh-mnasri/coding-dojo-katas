# Pagination Seven

🇫🇷 [Version française](README.md) · Kata: [codingdojo.org/kata/PaginationSeven](https://codingdojo.org/kata/PaginationSeven/)

## The kata

Display a pagination using **at most 7 slots**, with the current page in parentheses:

| Case | Example |
|---|---|
| I. 7 pages or fewer | `1 (2) 3 4 5` |
| II. in the middle | `1 … 41 (42) 43 … 100` |
| III. near the start | `1 2 3 (4) 5 … 9` |
| IV. near the end | `1 … 5 (6) 7 8 9` |

## TDD walkthrough

🔴 red · 🟢 green · 🔵 refactor · 📌 test green on first run

The tests follow the kata's four parts, in order.

| # | Step | What happened |
|---|---|---|
| 1 | 🔴 `show a single page` | Does not compile. |
| 2 | 🟢 `show a hard-coded single page` | `return "(1)"`. |
| 3 | 🔴 `show every page up to seven` | Part I. |
| 4 | 🟢 `list every page and mark the current one` | Every page, the current one in parentheses. |
| 5 | 🔴 `fold far pages into ellipses around the middle` | Part II: all 100 pages are printed. |
| 6 | 🟢 `use seven slots with ellipses around the current page` | A list of 7 "slots" where `null` stands for the ellipsis, then a single rendering. |
| 7 | 🔴 `drop the first ellipsis near the start` | Part III: page 2 of 9 gives `1 … 1 (2) 3 … 9`. |
| 8 | 🟢 `show the first five pages near the start` | Pages 1 to 5, `…`, last page. |
| 9 | 🔴 `drop the last ellipsis near the end` | Part IV: page 100 of 100 shows a page 101. |
| 10 | 🟢 `show the last five pages near the end` | The symmetric case. |
| 11 | 🔵 `name the slot count and the edge size` | The magic numbers 7, 4, 5 and 3 become `SLOTS` and `EDGE = SLOTS - 2`, with the reasoning spelled out. |
| 12 | 🔴 `reject a page outside the pagination` | Page 0 or 10 of 9 accepted. |
| 13 | 🟢 `require an existing page` | A `require`. |

## Solution

```kotlin
val slots = when {
    total <= SLOTS -> (1..total).toList()
    page < EDGE -> (1..EDGE).toList() + listOf(ELLIPSIS, total)
    page > total - EDGE + 1 -> listOf(1, ELLIPSIS) + (total - EDGE + 1..total).toList()
    else -> listOf(1, ELLIPSIS, page - 1, page, page + 1, ELLIPSIS, total)
}
```

## Takeaways

Separating the **choice of slots** (a list where `null` is an ellipsis) from their **rendering** avoided string juggling in each case. The boundaries (4 and 6 out of 9 pages) are the tests that matter: they decide between `<` and `<=`.

## Running the tests

```bash
./gradlew :katas:pagination-seven:test
git log --reverse --format='%s%n%b' strict-tdd-start.. -- katas/pagination-seven   # the TDD history
```
