# Markov Chain

🇫🇷 [Version française](README.md) · Kata: [codingdojo.org/kata/MarkovChain](https://codingdojo.org/kata/MarkovChain/)

## The kata

A two-part text generator:

1. **analyse** a text: for each word, the words following it with their percentage ("libres" is followed by "peuvent" 50% of the time and "ou" 50%);
2. **generate** a text of a given number of words from those statistics, optionally from an imposed first word.

## TDD walkthrough

🔴 red · 🟢 green · 🔵 refactor · 📌 test green on first run

Randomness is injected (`kotlin.random.Random`): in tests, a `ScriptedRandom` returns draws planned in advance.

| # | Step | What happened |
|---|---|---|
| 1 | 🔴 `learn nothing from a single word` | Does not compile. |
| 2 | 🟢 `know no follower` | `emptyMap()`. |
| 3 | 🔴 `give each follower with its percentage` | The kata's text: `les → hommes 100%`, `libres → peuvent 50%, ou 50%`. |
| 4 | 🟢 `count word pairs into follower frequencies` | `zipWithNext` yields pairs of consecutive words; they are grouped by first word, then counted. |
| 5 | 🔴 `generate the only possible text of a linear chain` | `generate` does not exist. |
| 6 | 🟢 `walk the chain from the first word` | Always take the first follower: enough for a chain without choices. |
| 7 | 🔴 `draw the next word according to the frequencies` | The `random` parameter does not exist. |
| 8 | 🟢 `draw followers by cumulative frequency` | Each follower takes a share of [0, 1) equal to its frequency; a draw picks the share. |
| 9 | 🔴 `restart from a random word at a dead end` | "dort" has no follower: exception. (My test planned only 3 draws for a scenario consuming 4: fixed by amending the red commit, before pushing.) |
| 10 | 🟢 `restart from a random known word at a dead end` | Design choice: at a dead end, start again from a randomly drawn known word. |
| 11 | 🔴 `refuse to generate from a text without any pair` | A one-word text causes an `IndexOutOfBoundsException`. |
| 12 | 🟢 `report a chain that learned nothing` | A `check` with a clear message. |

## Solution

```kotlin
val transitions = words.zipWithNext()
    .groupBy({ it.first }, { it.second })
    .mapValues { (_, followers) -> followers.groupingBy { it }.eachCount().mapValues { (_, n) -> n.toDouble() / followers.size } }
```

## Takeaways

Injecting randomness makes generation testable deterministically, and even readable: the test says "with a 0.75 draw, you get *ou*". Without it, all one could check would be vague properties of unpredictable texts.

## Running the tests

```bash
./gradlew :katas:markov-chain:test
git log --reverse --format='%s%n%b' strict-tdd-start.. -- katas/markov-chain   # the TDD history
```
