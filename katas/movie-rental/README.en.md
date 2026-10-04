# Movie Rental

🇫🇷 [Version française](README.md) · Kata: [codingdojo.org/kata/movie-rental](https://codingdojo.org/kata/movie-rental/)

## The kata

The example opening Martin Fowler's *Refactoring*: a video store's `statement()` method prints a text statement. We want an **HTML** version too. The instruction: "first refactor the program to make it easy to add the feature, then add the feature".

## Approach

🔴 red · 🟢 green · 🔵 refactor · 📌 test green on first run

| # | Step | What happened |
|---|---|---|
| 0 | `chore: import the legacy code to refactor` | The book's code, translated into Kotlin. Committed outside the guard. |
| 1 | 📌 `characterise the text statement with hand-computed amounts` | Four statements whose amounts were computed **by hand** from the rules (not copied from a run): the kata's example, a new release, children's movies, a customer without rentals. |
| 2 | 🔵 `move the charge computation to the rental` | The charge only depends on the rental: it moves to `Rental.charge()`. |
| 3 | 🔵 `move the frequent renter points to the rental` | Same for the loyalty points. |
| 4 | 🔵 `replace the running totals with queries` | Running variables become `totalCharge()` and `totalFrequentRenterPoints()`. `statement()` now only **formats**. |
| 5 | 🔵 `replace the price code switch with price objects` | The `when` on the price code becomes a `Price` interface (regular, new release, children's), each with its own computation. The original integer codes are still accepted. **Slip**: an unknown code now throws, whereas the original code silently charged 0. A behaviour change slipped into a refactoring. |
| 6 | 📌 `pin the behaviour change on unknown price codes` | Makes the step 5 slip visible. |
| 7 | 🔴 `print the statement in HTML` | The requested feature, in the kata's format. |
| 8 | 🟢 `print the statement in HTML` | One more formatting method, reusing `charge()`, `totalCharge()` and `totalFrequentRenterPoints()`. Before the refactoring, the whole computation logic would have had to be duplicated. |

Format detail: amounts print as in the original text statement (`2.0`), where the kata's HTML example writes `2`.

## Solution

```kotlin
fun htmlStatement(): String {
    val header = "<h1>Rental Record for <em>$name</em></h1>\n<table>\n"
    val lines = rentals.joinToString("") { "  <tr><td>${it.movie.title}</td><td>${it.charge()}</td></tr>\n" }
    val footer = "</table>\n<p>Amount owed is <em>${totalCharge()}</em></p>\n" +
        "<p>You earned <em>${totalFrequentRenterPoints()}</em> frequent renter points</p>"
    return header + lines + footer
}
```

## Takeaways

- "Make the change easy, then make the easy change": four refactorings turned adding HTML into a plain formatting method.
- Replacing a `switch` with polymorphism is when one feels like "fixing" things along the way. If you do, say so; a 📌 test makes it visible.

## Running the tests

```bash
./gradlew :katas:movie-rental:test
git log --reverse --format='%s%n%b' strict-tdd-start.. -- katas/movie-rental   # the history
```
