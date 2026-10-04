# Potter

🇫🇷 [Version française](README.md) · Kata: [codingdojo.org/kata/Potter](https://codingdojo.org/kata/Potter/)

## The kata

A Harry Potter book costs €8. **Different** titles bought together form a discounted set: 5% for 2, 10% for 3, 20% for 4, 25% for 5. Price any basket with the best possible discount.

The trap: with `2, 2, 2, 1, 1` copies, two sets of 4 (€51.20) are cheaper than a set of 5 and a set of 3 (€51.60).

## TDD walkthrough

🔴 red · 🟢 green · 🔵 refactor · 📌 test green on first run

| # | Step | What happened |
|---|---|---|
| 1 | 🔴 `price an empty basket at zero` | Does not compile. |
| 2 | 🟢 `price an empty basket` | `ZERO`. |
| 3 | 🔴 `price identical books at 8 euros each` | Expected 8, got 0. |
| 4 | 🟢 `charge 8 euros per book` | `8 × number of books`. |
| 5 | 🔴 `discount sets of different books` | 2 different titles: 16 instead of 15.20. |
| 6 | 🟢 `discount a set of different books` | A discount table, applied when every book differs. |
| 7 | 🔴 `split a basket into several sets` | `0, 0, 1`: 24 instead of 23.20. |
| 8 | 🟢 `split the basket greedily into the largest sets` | Greedy algorithm: always form the largest possible set. |
| 9 | 🔴 `prefer two sets of four over five and three` | The kata's example: greedy gives 51.60 instead of 51.20. **This is the heart of the kata.** |
| 10 | 🟢 `search the cheapest split, memoized on the copy counts` | Rather than an ad hoc fix ("replace 5 + 3 with 4 + 4"), a search: for each possible set size, take one copy of the most numerous titles, and keep the cheapest split. Only the sorted copy counts matter, which lets sub-baskets be memoized. |
| 11 | 📌 `price big baskets quickly and correctly` | 40 books (10, 10, 10, 5, 5): €256, computed instantly. |
| 12 | 🔵 `state the expected split in the big basket test` | I had not checked that 256 before writing it, and the test comment was sloppy. An independent Python oracle, trying **every** combination of titles, confirms €256 (10 sets of 4) and €51.20. |

## Solution

```kotlin
private fun cheapest(copies: List<Int>, known: MutableMap<List<Int>, BigDecimal>): BigDecimal {
    if (copies.isEmpty()) return BigDecimal.ZERO
    known[copies]?.let { return it }
    val best = (1..copies.size).minOf { size ->
        val rest = copies.mapIndexed { index, count -> if (index < size) count - 1 else count }
            .filter { it > 0 }.sortedDescending()
        setPrice(size) + cheapest(rest, known)
    }
    known[copies] = best
    return best
}
```

Building a set from the **most numerous** titles is enough: it leaves the most options for the rest, and the exhaustive oracle confirms it on the examples.

## Takeaways

- The greedy version was written and kept while it was enough; the kata's example is what justified the optimisation.
- A 📌 test whose expected value you did not compute yourself proves nothing: it freezes whatever the code produces. The independent oracle of step 12 restored the proof.

## Running the tests

```bash
./gradlew :katas:potter:test
git log --reverse --format='%s%n%b' strict-tdd-start.. -- katas/potter   # the TDD history
```
