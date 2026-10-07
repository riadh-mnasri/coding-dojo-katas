# Anagram

🇫🇷 [Version française](README.md) · Kata: [codingdojo.org/kata/Anagram](https://codingdojo.org/kata/Anagram/)

## The kata

Find every **two-word** anagram of "documenting" from a word list, keeping an eye on the trade-off between performance and readability.

## A finding along the way

The word list suggested by the kata (1,633 words) holds **no** two-word anagram of "documenting". I checked it with a small independent Python script before writing the test, and it became one: the list is shipped as a test resource and the expected result is empty.

With the `web2` dictionary (Webster 1934, public domain, about 235,000 words, shipped with macOS), the same script finds **52 pairs**, for instance `document + gin`, `coming + tuned`, `medoc + tuning`. That test is skipped (`assumeTrue`) when the machine lacks this dictionary, as on the Linux CI: another word list (`/usr/share/dict/words`) would give other pairs.

## TDD walkthrough

🔴 red · 🟢 green · 🔵 refactor · 📌 test green on first run

| # | Step | What happened |
|---|---|---|
| 1 | 🔴 `find nothing in an empty dictionary` | Does not compile. |
| 2 | 🟢 `find nothing yet` | `emptySet()`. |
| 3 | 🔴 `find a pair of words using every letter` | Expected `document + gin`, found nothing. |
| 4 | 🟢 `compare sorted letters of every pair of words` | Naive and very readable: every pair `(a, b)` with `a <= b` whose sorted letters match the target. |
| 5 | 📌 `allow the same word twice` | `ab + ab` for `abab`: already covered by `a <= b`. (This commit first also claimed case handling, which was not tested; message fixed before pushing.) |
| 6 | 🔴 `ignore the case of dictionary words` | `Document`, `GIN`: nothing found. |
| 7 | 🟢 `lower-case the dictionary` | The dictionary is lower-cased and de-duplicated. |
| 8 | 📌 `find no two-word anagram in the kata word list` | Empty result on the kata's list, as the oracle said. |
| ⛔ | two false reds discarded | My first two versions of the next test did not compile (`assertTimeoutPreemptively` type inference): red for the wrong reason. I undid them before pushing and replayed the red step with an explicit `ThrowingSupplier`. |
| 9 | 🔴 `search the 235,000-word web2 dictionary in seconds` | **The right red**: the naive version exceeds the 10-second limit (about 5 × 10¹⁰ pairs). |
| 10 | 🟢 `index words by their sorted letters to look up the remainder` | Two ideas: keep only words written with the target's letters (663 out of 235,000), then index them by sorted letters. For each first word, compute the remaining letters and look the second one up. The test passes in 0.5 s. |
| 11 | 📌 `run the web2 test only on the web2 dictionary` | Added with the CI: the test fell back to `/usr/share/dict/words` when `web2` was missing, and would have compared the 52 expected pairs with another word list. It is now skipped without `web2`. |

## Solution

```kotlin
val candidates = dictionary.filter { it.isWrittenWithLettersOf(target) }
val bySignature = candidates.groupBy(::signature)
return candidates.flatMap { first ->
    val remainder = target.minusLettersOf(first)
    bySignature[signature(remainder)].orEmpty().map { second -> ordered(first, second) }
}.toSet()
```

## Takeaways

- The naive version came first and stayed as long as it was enough: a **performance test** justified the optimisation, and the existing functional tests guaranteed it did not change the result.
- Checking an expected value against an independent oracle avoids freezing into a test whatever the code happens to produce.

## Running the tests

```bash
./gradlew :katas:anagram:test
git log --reverse --format='%s%n%b' strict-tdd-start.. -- katas/anagram   # the TDD history
```
