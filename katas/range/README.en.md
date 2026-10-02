# Range

🇫🇷 [Version française](README.md) · Kata: [codingdojo.org/kata/Range](https://codingdojo.org/kata/Range/)

## The kata

An integer range written with open `( )` or closed `[ ]` bounds, for example `[2,6)`. It must tell:

- whether it contains values (`[2,6)` contains `{2, 4}`, not `{-1, 1, 6, 10}`);
- its points (`{2, 3, 4, 5}`) and end points (`(2,6]` → `{3, 6}`);
- whether it contains another range, overlaps it, equals it.

## TDD walkthrough

🔴 red · 🟢 green · 🔵 refactor · 📌 test green on first run

| # | Step | What happened |
|---|---|---|
| 1 | 🔴 `contain the values inside a half-open range` | Does not compile. |
| 2 | 🟢 `contain everything for now` | `return true`. |
| 3 | 🔴 `exclude values outside and the open end` | Everything is "contained". |
| 4 | 🟢 `parse the bounds and check values against them` | A regular expression for the notation and two comparisons depending on whether each bound is included. |
| 5 | 🔴 `list all the points` | `allPoints` does not exist. |
| 6 | 🟢 `list the points between the first and the last` | **Key simplification**: the first and last integer points are computed once (`(2` gives 3, `6)` gives 5). `contains` then boils down to `value in first..last`. |
| 7 | 🔴 `give the end points of each kind of range` | `endPoints` does not exist. |
| 8 | 🟢 `expose the end points` | `first to last`, already computed. |
| 9 | 🔴 `tell whether it contains another range` | `containsRange` does not exist. |
| 10 | 🟢 `contain a range whose end points are inside` | `contains(other.first, other.last)`. |
| 11 | 🔴 `tell whether two ranges overlap` | `overlapsRange` does not exist. |
| 12 | 🟢 `overlap when each range starts before the other ends` | The classic overlap condition. |
| 13 | 🔴 `compare ranges by value` | Reference equality. |
| 14 | 🟢 `compare ranges by their points` | Equality on `first` and `last`. |
| 15 | 📌 `treat [3,5) and [3,4] as the same integer range` | A deliberate consequence: over integers, `[3,5)`, `[3,4]` and `(2,5)` are the same set. |
| 16 | 🔴 `reject malformed or empty ranges` | `[6,2]` and `(3,4)` (no integer) are accepted. |
| 17 | 🟢 `refuse ranges without any point` | A `require(first <= last)` at construction time. |

## Solution

The whole class rests on two values derived from the notation:

```kotlin
private val first = if (startIncluded) start else start + 1
private val last = if (endIncluded) end else end - 1
```

Every operation is then a one-liner: membership, points, end points, containment, overlap, equality.

## Takeaways

Open or closed bounds are a notation detail. Turning them into integer points straight away (step 6) removed the open/closed combinatorics from every later operation.

## Running the tests

```bash
./gradlew :katas:range:test
git log --reverse --format='%s%n%b' strict-tdd-start.. -- katas/range   # the TDD history
```
