# Lags

🇫🇷 [Version française](README.md) · Kata: [codingdojo.org/kata/Lags](https://codingdojo.org/kata/Lags/)

## The kata

ABEAS Corp has a single plane. Customers send rental requests: a start time, a duration, a price. Find the combination of compatible requests (the plane flies one trip at a time) that maximises the gain.

```
AF514 0 5 10
CO5 3 7 14
AF515 5 9 7
BA01 6 9 8
```

Best combination: AF514 + BA01 = 18.

## TDD walkthrough

🔴 red · 🟢 green · 🔵 refactor · 📌 test green on first run

"This kata seems simple at first glance": the simple version was written and kept while it was enough, and a realistic-size test is what forced the right one.

| # | Step | What happened |
|---|---|---|
| 1 | 🔴 `earn nothing without requests` | Does not compile. |
| 2 | 🟢 `earn nothing` | `return 0`. |
| 3 | 🔴 `earn the price of a single request` | Expected 10. |
| 4 | 🟢 `add up the prices` | The sum of every price. |
| 5 | 🔴 `keep only one of two overlapping requests` | Two overlapping flights: 24 instead of 14. |
| 6 | 🟢 `try with and without each request, recursively` | For the first request: either refuse it, or accept it and keep only the requests leaving after it lands. Correct, but exponential. |
| 7 | 📌 `solve the kata sample and chain back-to-back flights` | The sample (18), and a flight leaving the moment the previous one lands. |
| 8 | 🔴 `handle ten thousand requests in two seconds` | 20,000 requests whose optimum is known **by construction** (10,000 chained one-hour flights, plus overlapping decoys): `StackOverflowError`, before even hitting the time limit. |
| 9 | 🟢 `compute the best gain bottom-up with a binary search` | Dynamic programming: `best[i] = max(best[i + 1], price[i] + best[next(i)])`, filled from the end, the next compatible request being found by binary search. O(n log n), no recursion. |
| 10 | 🔴 `read the request file format` | `parse` does not exist. |
| 11 | 🟢 `read one request per line` | Four fields per line. |

## Solution

```kotlin
for (i in sorted.indices.reversed()) {
    val next = firstStartingFrom(starts, sorted[i].end)
    best[i] = maxOf(best[i + 1], sorted[i].price + best[next])
}
```

## Takeaways

- The recursion of step 6 is exactly the dynamic programming relation of step 9: the first draft gave the formula, the performance test forced how to compute it.
- For a volume test, building the input so the optimum is **known** avoids freezing whatever the code outputs.

## Running the tests

```bash
./gradlew :katas:lags:test
git log --reverse --format='%s%n%b' strict-tdd-start.. -- katas/lags   # the TDD history
```
