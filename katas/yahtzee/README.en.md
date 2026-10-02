# Yahtzee

🇫🇷 [Version française](README.md) · Kata: [codingdojo.org/kata/Yahtzee](https://codingdojo.org/kata/Yahtzee/)

## The kata

Given a 5-dice roll and a category, compute the score: chance, yahtzee (50), ones to sixes, pair, two pairs, three of a kind, four of a kind, small straight (15), large straight (20), full house. A roll that does not fit the category scores 0.

## TDD walkthrough

🔴 red · 🟢 green · 🔵 refactor · 📌 test green on first run

| # | Step | What happened |
|---|---|---|
| 1 | 🔴 `score chance as the sum of the dice` | Does not compile. |
| 2 | 🟢 `sum the dice for chance` | `dice.sum()`, ignoring the category. |
| 3 | 🔴 `score 50 for a yahtzee` | `YAHTZEE` does not exist. |
| 4 | 🟢 `score a yahtzee` | A `when` over the category. |
| 5 | 🔴 `score the upper section by face` | `ONES` to `SIXES` do not exist. |
| 6 | 🟢 `let each category carry its scoring rule, upper section included` | Rather than a `when` heading for fifteen branches, each `enum` value carries its scoring function. The six faces share a `sumOf(face)` factory. |
| 7 | 🔴 `score the highest pair` | `PAIR` does not exist. |
| 8 | 🟢 `score the highest pair` | An `ofAKind(count)` factory: the highest face present at least `count` times. |
| 9 | 🔴 `score three and four of a kind` | Missing categories. |
| 10 | 🟢 `reuse the of-a-kind rule for three and four` | `ofAKind(3)` and `ofAKind(4)`: two lines. |
| 11 | 🔴 `score two different pairs` | Missing category. |
| 12 | 🟢 `score two different pairs` | Two different faces present at least twice. Choice: four of a kind is not "two pairs". |
| 13 | 🔴 `score small and large straights` | Missing categories. |
| 14 | 🟢 `score both straights` | A `straight(1..5)` / `straight(2..6)` factory, regardless of dice order. |
| 15 | 🔴 `score a full house` | Missing category. |
| 16 | 🟢 `score a full house` | Face counts must be exactly `[2, 3]`, which rules out the `4,4,4,4,4` yahtzee. |
| 17 | 🔵 `share face counting between categories` | A shared `faceCounts` instead of four `groupingBy`s. |
| 18 | 🔴 `require five dice from 1 to 6` | 4 dice or a face 7 are accepted. |
| 19 | 🟢 `validate the roll before scoring` | A `require` in `Yahtzee.score`. |

## Solution

```kotlin
enum class Category(val score: (List<Int>) -> Int) {
    CHANCE({ dice -> dice.sum() }),
    PAIR(ofAKind(2)),
    SMALL_STRAIGHT(straight(1..5)),
    FULL_HOUSE({ dice -> if (faceCounts(dice).values.sorted() == listOf(2, 3)) dice.sum() else 0 }),
    // ...
}
```

Adding a category means adding one line to the `enum`, often from an existing factory.

## Takeaways

Moving from a `when` to an `enum` carrying its behaviour (step 6) was triggered by six categories arriving at once. From then on, each cycle added one category without touching the others.

## Running the tests

```bash
./gradlew :katas:yahtzee:test
git log --reverse --format='%s%n%b' strict-tdd-start.. -- katas/yahtzee   # the TDD history
```
