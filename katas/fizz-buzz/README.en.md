# FizzBuzz

🇫🇷 [Version française](README.md) · Kata: [codingdojo.org/kata/FizzBuzz](https://codingdojo.org/kata/FizzBuzz/)

## The kata

Print the numbers from 1 to 100, replacing multiples of 3 with `Fizz`, multiples of 5 with `Buzz` and multiples of both with `FizzBuzz`.

**Stage 2**: a number is also `Fizz` when it contains a 3, and `Buzz` when it contains a 5.

## TDD walkthrough

🔴 red · 🟢 green · 🔵 refactor · 📌 test green on first run

| # | Step | What happened |
|---|---|---|
| 1 | 🔴 `say 1 for 1` | Does not compile: `FizzBuzz` does not exist. |
| 2 | 🟢 `fake the answer for 1` | `return "1"`: make the test pass as simply as possible. |
| 3 | 🔴 `say 2 for 2` | Expected `"2"`, got `"1"`: the hard-coded value is exposed. |
| 4 | 🟢 `say the number itself` | `number.toString()`. |
| 5 | 🔴 `say Fizz for 3` | Expected `"Fizz"`, got `"3"`. |
| 6 | 🟢 `say Fizz for multiples of three` | Obvious implementation `number % 3 == 0` rather than an artificial `== 3`. |
| 7 | 🔴 `say Buzz for 5` | Expected `"Buzz"`, got `"5"`. |
| 8 | 🟢 `say Buzz for multiples of five` | A second branch in a `when`. |
| 9 | 🔴 `say FizzBuzz for 15` | Expected `"FizzBuzz"`, got `"Fizz"`: the first branch wins. |
| 10 | 🟢 `concatenate Fizz and Buzz` | Instead of a `% 15` case, both words are concatenated, falling back to the number when nothing applies (`ifEmpty`). |
| 11 | 🔵 `turn both conditions into rules` | Both conditions share the same shape: they become `Rule`s (`wordFor(n): String?`) in a list. |
| 12 | 🔴 `list the answers from 1 to 100` | `sequence()` does not exist. |
| 13 | 🟢 `list the answers from 1 to 100` | A `map` over `1..100`. |
| 14 | 🔵 `inject the rules to prepare stage 2` | Preparatory refactoring: the `object` becomes a class receiving its rules, with a `classic` configuration. Existing tests go through `FizzBuzz.classic`. |
| 15 | 🔴 `say Fizz for 13 in stage 2` | The `stageTwo` configuration does not exist. |
| 16 | 🟢 `add a rule for numbers containing a digit` | New `divisibleByOrContains` factory, applied to 3 only. |
| 17 | 🔴 `say Buzz for 52 in stage 2` | Expected `"Buzz"`, got `"52"`. |
| 18 | 🟢 `apply the containing rule to 5 as well` | Same factory for 5. |
| 19 | 📌 `combine stage 2 rules` | `53` and `35` give `FizzBuzz` with no change: the concatenation from step 10 does the job. |

## Solution

```kotlin
class FizzBuzz(private val rules: List<Rule>) {
    fun say(number: Int): String =
        rules.mapNotNull { it.wordFor(number) }.joinToString("").ifEmpty { number.toString() }
}

val classic = FizzBuzz(listOf(divisibleBy(3, "Fizz"), divisibleBy(5, "Buzz")))
val stageTwo = FizzBuzz(listOf(divisibleByOrContains(3, "Fizz"), divisibleByOrContains(5, "Buzz")))
```

- The list order drives the word order (`Fizz` before `Buzz`).
- Adding `Whizz` for 7 needs no change to the class.

## Takeaways

Test 15 is the turning point: an `if (n % 15 == 0)` would have worked, but concatenation made the "3 and 5" case free, and it is again what makes the last stage 2 test pass without code. The preparatory refactoring (step 14) follows Kent Beck's advice: "make the change easy, then make the easy change".

## Running the tests

```bash
./gradlew :katas:fizz-buzz:test
git log --reverse --format='%s%n%b' strict-tdd-start.. -- katas/fizz-buzz   # the TDD history
```
