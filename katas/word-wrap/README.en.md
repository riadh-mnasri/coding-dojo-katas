# Word Wrap

🇫🇷 [Version française](README.md) · Kata: [codingdojo.org/kata/WordWrap](https://codingdojo.org/kata/WordWrap/)

## The kata

`Wrapper.wrap(text, column)` inserts line breaks so that no line is longer than `column` characters, breaking between words when possible (the last space of a line becomes a line break). A kata by Robert C. Martin.

## TDD walkthrough

🔴 red · 🟢 green · 🔵 refactor · 📌 test green on first run

| # | Step | What happened |
|---|---|---|
| 1 | 🔴 `wrap an empty text` | Does not compile. |
| 2 | 🟢 `return the text as is` | `return text`. |
| 3 | 🔴 `cut a word longer than the column` | `longword` at 4: expected `long\nword`. |
| 4 | 🟢 `cut recursively at the column` | When the text is too long: the first `column` characters, a break, then `wrap` of the rest. Recursion shows up with this very test. |
| 5 | 🔴 `break at a space instead of inside a word` | `word word` at 6 gives `word w\nord`. |
| 6 | 🟢 `break at the last space before the column` | `lastIndexOf(' ', column - 1)`; the space found is replaced by the break. |
| 7 | 🔴 `break right after the column when a space sits there` | `word word` at 4: the space sits just **after** the column, is not found, and the output holds a stray empty line. Uncle Bob's edge case. |
| 8 | 🟢 `look for a space up to the column itself` | Search up to index `column` included: a space there is a valid break. |
| 9 | 🔵 `name the two ways of breaking a line` | A `breakAt(text, index, skip)` function: break on a space (skip it) or inside a word (skip nothing). |
| 10 | 📌 `wrap several lines and long words in sentences` | Multi-line sentences and a long word inside a sentence: green. |
| 11 | 🔴 `reject a column below 1` | `StackOverflowError`: with a 0 column the recursion never progresses. |
| 12 | 🟢 `require a positive column` | A `require` up front. |

## Solution

```kotlin
fun wrap(text: String, column: Int): String {
    require(column >= 1) { "The column must be at least 1, got $column" }
    if (text.length <= column) return text
    val space = text.lastIndexOf(' ', column)
    return if (space >= 0) breakAt(text, space, skip = 1, column) else breakAt(text, column, skip = 0, column)
}
```

## Takeaways

The whole kata fits in test 7: an off-by-one in the space search. Test 11 is a reminder that a recursive function must always guarantee progress.

## Running the tests

```bash
./gradlew :katas:word-wrap:test
git log --reverse --format='%s%n%b' strict-tdd-start.. -- katas/word-wrap   # the TDD history
```
