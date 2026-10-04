# Quote of the Day

🇫🇷 [Version française](README.md) · Kata: [codingdojo.org/kata/QotdCgi](https://codingdojo.org/kata/QotdCgi/)

## The kata

A web service returning a **different quote on every visit**, and, with a `?q=foobar` parameter, a random quote containing "foobar".

Matteo Vaccari designed this kata to teach the web to students, without TDD (feedback comes from reloading the page). Here the same exercise is done test-first: the business core first, the HTTP adapter second.

## TDD walkthrough

🔴 red · 🟢 green · 🔵 refactor · 📌 test green on first run

Randomness is injected: in tests, a `Random` always returning the first candidate makes draws predictable.

| # | Step | What happened |
|---|---|---|
| 1 | 🔴 `give a quote from the collection` | Does not compile. |
| 2 | 🟢 `draw a quote at random` | `collection.random(random)`. |
| 3 | 🔴 `never give the same quote twice in a row` | With the "always the first" draw, the same quote comes back. |
| 4 | 🟢 `draw among the quotes other than the last one` | The last quote given is excluded. **Slip**: I also handled a single-quote collection without a red test. |
| 5 | 🔴 `pick a quote containing the searched word` | `next(containing)` does not exist; no match gives `null`. |
| 6 | 🟢 `search quotes containing a word` | Filter, then the same draw. **Slip**: the search ignores case without any test asking for it. |
| 7 | 📌 `cover the single-quote collection, handled without a red test` | Making up for the step 4 slip. |
| 8 | 📌 `cover the case-insensitive search written ahead of its test` | Making up for the step 6 slip. |
| 9 | 🔴 `serve quotes over HTTP` | An integration test: a real server on a free port (`port = 0`), a real HTTP client. |
| 10 | 🟢 `serve quotes with the JDK HTTP server` | `com.sun.net.httpserver`, no framework: read the `q` parameter, 200 with the quote or 404. `main` wires everything (untested, logic-free). |

## Running

```bash
./gradlew :katas:quote-of-the-day:test
# then run main() in QuoteServer.kt and open http://localhost:8080/?q=make
```

## Takeaways

- Splitting `Quotes` (business) from `QuoteServer` (web) lets every rule be tested without a network; only two tests start a real server.
- Two more catch-up 📌s: the "small obvious things" (case, single-element collection) are precisely the ones written without a test.

## History

```bash
git log --reverse --format='%s%n%b' strict-tdd-start.. -- katas/quote-of-the-day
```
