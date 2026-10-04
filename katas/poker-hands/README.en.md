# Poker Hands

🇫🇷 [Version française](README.md) · Kata: [codingdojo.org/kata/PokerHands](https://codingdojo.org/kata/PokerHands/)

## The kata

Compare two 5-card poker hands and announce the winner, with the reason:

```
Black: 2H 3D 5S 9C KD  White: 2C 3H 4S 8C AH    →  White wins. - with high card: Ace
Black: 2H 4S 4C 2D 4H  White: 2S 8S AS QS 3S    →  Black wins. - with full house: 4 over 2
Black: 2H 3D 5S 9C KD  White: 2C 3H 4S 8C KH    →  Black wins. - with high card: 9
Black: 2H 3D 5S 9C KD  White: 2D 3H 5C 9S KH    →  Tie.
```

Categories, weakest to strongest: high card, pair, two pairs, three of a kind, straight, flush, full house, four of a kind, straight flush.

## TDD walkthrough

🔴 red · 🟢 green · 🔵 refactor · 📌 test green on first run

First comparing hands (`Hand`, `Comparable`), then the announcement (`Game`).

| # | Step | What happened |
|---|---|---|
| 1 | 🔴 `rank high cards by their highest card` | Does not compile. |
| 2 | 🟢 `compare hands by their highest card` | Compare the maximums. |
| 3 | 🔴 `break high card ties with the next cards` | Same king: the hand with a 9 must beat the one with an 8. |
| 4 | 🟢 `compare values from the highest down` | Compare sorted values one by one. |
| 5 | 🔴 `rank a pair above high cards` | A pair of 2s loses to a lone ace. |
| 6 | 🟢 `compare the shape of value groups before the values` | **The central idea**: group values, sort groups by size then value. The "shape" (`[2,1,1,1]` for a pair) is compared before the values, and values ordered that way (pair first, then single cards) directly give each category's tie-break. |
| 7 | 📌 `order two pairs, three of a kind, full house and four of a kind` | The shape already ranks two pairs `[2,2,1]` < three of a kind `[3,1,1]` < full house `[3,2]` < four of a kind `[4,1]`. |
| 8 | 🔵 `remove a confusing expression from the groups test` | I had left a `.let` discarding its result in the test: cleaned up. |
| 9 | 🔴 `rank straights and flushes` | Straights and flushes have no special shape (`[1,1,1,1,1]`): they rank as high cards. |
| 10 | 🟢 `introduce categories with straights and flushes` | An explicit `Category` enum, computed from the shape, the suits and the spread between extreme values. The category replaces the shape in the comparison. |
| 11 | 🔴 `rank a straight flush above four of a kind` | The straight flush is seen as a mere flush. |
| 12 | 🟢 `recognise straight flushes` | A new top category. |
| 13 | 🔵 `name the flush and straight conditions` | `isFlush`, `isStraight`. |
| 14 | 🔴 `announce a tie` | `Game` does not exist. |
| 15 | 🟢 `announce a tie` | `return "Tie."`. |
| 16 | 🔴 `announce the winner as in the kata sample` | The kata's other three lines. |
| 17 | 🟢 `name the winner and the deciding card` | The reason: the winner's category, then the first differing value ("high card: 9") or, for a full house, "4 over 2". |
| 18 | 🔴 `reject malformed lines and hands` | A malformed line throws a `NullPointerException` (the `!!` from step 17), bad cards go through. |
| 19 | 🟢 `validate lines and hands` | Five cards, known values and suits, no duplicate. |

## Solution

```kotlin
private val groups = values.groupingBy { it }.eachCount().entries
    .sortedWith(compareByDescending<Map.Entry<Int, Int>> { it.value }.thenByDescending { it.key })
val ordered = groups.map { it.key }   // the tie-break order
override fun compareTo(other: Hand) =
    category.compareTo(other.category).takeIf { it != 0 } ?: compareLists(ordered, other.ordered)
```

Deliberate choice: the ace only counts high, as in the kata (no A-2-3-4-5 straight).

## Takeaways

Ordering values "by group size, then value" gives, in a single list, the tie-break of every category: pair, two pairs, full house, kickers. That is what keeps the code short as categories pile up.

## Running the tests

```bash
./gradlew :katas:poker-hands:test
git log --reverse --format='%s%n%b' strict-tdd-start.. -- katas/poker-hands   # the TDD history
```
