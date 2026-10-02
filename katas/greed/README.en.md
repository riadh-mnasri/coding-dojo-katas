# Greed

🇫🇷 [Version française](README.md) · Kata: [codingdojo.org/kata/Greed](https://codingdojo.org/kata/Greed/)

## The kata

Score a roll of the Greed dice game (up to 6 dice):

| Combination | Score |
|---|---|
| a single 1 / a single 5 | 100 / 50 |
| triple ones | 1000 |
| triple 2 to 6 | face × 100 |
| four, five and six of a kind | triple score × 2, × 4, × 8 |
| three pairs | 800 |
| straight 1 to 6 | 1200 |

## TDD walkthrough

🔴 red · 🟢 green · 🔵 refactor · 📌 test green on first run

| # | Step | What happened |
|---|---|---|
| 1 | 🔴 `score nothing for no dice` | Does not compile. |
| 2 | 🟢 `score zero` | `return 0`. |
| 3 | 🔴 `score single ones and fives` | Expected 100, got 0. |
| ⛔ | rejected green attempt | `sumOf` with a `when` returning literals: `Int`/`Long` overload ambiguity, the code does not compile. |
| 4 | 🟢 `score single ones and fives` | `map { ... }.sum()`. |
| 5 | 🔴 `score triples` | `1 1 1` gives 300 instead of 1000. |
| 6 | 🟢 `score triples by face, then the remaining singles` | Dice are grouped by face (`groupingBy`): a triple scores by face, the rest as singles. |
| 7 | 🔴 `double the triple score for each extra die` | Four 2s give 200 instead of 400. |
| 8 | 🟢 `multiply the triple score for four, five and six of a kind` | × 2, × 4, × 8 is a bit shift: `tripleScore shl (count - 3)`. |
| 9 | 🔴 `score three pairs and a straight` | 0 and 150 instead of 800 and 1200. |
| 10 | 🟢 `score three pairs and straights as whole rolls` | Both combinations concern the whole roll: they are checked before the face-by-face scoring. Constants and comments along the way. |
| 11 | 🔴 `accept up to six real dice only` | Seven dice or a face 7 are accepted. |
| 12 | 🟢 `validate the number of dice and their faces` | Two `require`s. |
| 13 | 📌 `mix triples and singles` | `1 1 1 5 1 = 2050`, `3 4 5 3 3 = 350`... |

## Solution

```kotlin
fun score(dice: List<Int>): Int {
    val counts = dice.groupingBy { it }.eachCount()
    return when {
        counts.size == 6 -> STRAIGHT
        counts.size == 3 && counts.values.all { it == 2 } -> THREE_PAIRS
        else -> counts.map { (face, count) -> scoreOf(face, count) }.sum()
    }
}

private fun scoreOf(face: Int, count: Int) =
    if (count >= 3) tripleScore(face) shl (count - 3) else count * singleScore(face)
```

## Takeaways

Grouping dice by face as soon as triples appear makes every later rule local to one face, except two (three pairs and straight) that concern the whole roll. Telling them apart explicitly keeps both levels from getting mixed.

## Running the tests

```bash
./gradlew :katas:greed:test
git log --reverse --format='%s%n%b' strict-tdd-start.. -- katas/greed   # the TDD history
```
