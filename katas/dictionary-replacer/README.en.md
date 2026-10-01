# Dictionary Replacer

🇫🇷 [Version française](README.md) · Kata: [codingdojo.org/kata/DictionaryReplacer](https://codingdojo.org/kata/DictionaryReplacer/)

## The kata

Write a method taking a string and a dictionary that replaces every key wrapped in `$` with its value: `"$temp$ here comes the name $name$"` becomes `"temporary here comes the name John Doe"`.

## TDD walkthrough

🔴 red · 🟢 green · 🔵 refactor · 📌 test green on first run

| # | Step | What happened |
|---|---|---|
| 1 | 🔴 `keep an empty text empty` | Does not compile: `DictionaryReplacer` does not exist. |
| 2 | 🟢 `return the text unchanged` | `return text`. |
| 3 | 🔴 `replace a single placeholder` | Expected `"temporary"`, got `"$temp$"`. |
| 4 | 🟢 `replace each dictionary key wrapped in dollars` | Naive solution: for each dictionary entry, `replace("$key$", value)`. |
| 5 | 📌 `replace several placeholders in a sentence` | The kata's full example passes with the loop. |
| 6 | 🔴 `never replace inside a replaced value` | With `a → "$b$"` and `b → "boom"`, the loop replaces in cascade: `"boom"` instead of `"$b$"`. The result depends on dictionary order: a bug. |
| 7 | 🟢 `replace placeholders in a single pass over the text` | Walk **the text**, not the dictionary: a single `Regex.replace`. Each placeholder is replaced once, whatever the values hold. |
| 8 | 🔴 `keep unknown placeholders` | `getValue` throws `NoSuchElementException` for a missing key. |
| 9 | 🟢 `leave unknown placeholders as they are` | `dictionary[key] ?: match.value`: nothing is silently lost. |
| 10 | 📌 `ignore lonely dollars and repeat known keys` | A lonely `$` is not a placeholder, a repeated key is replaced every time. |

## Solution

```kotlin
private val placeholder = Regex("""\$(\w+)\$""")

fun replace(text: String, dictionary: Map<String, String>): String =
    placeholder.replace(text) { match -> dictionary[match.groupValues[1]] ?: match.value }
```

## Takeaways

The naive solution passes every test from the kata. The cascading replacement test (step 6) is what shows the loop must walk the text, not the dictionary: a good next test is often one that attacks a hidden assumption of the implementation.

## Running the tests

```bash
./gradlew :katas:dictionary-replacer:test
git log --reverse --format='%s%n%b' strict-tdd-start.. -- katas/dictionary-replacer   # the TDD history
```
