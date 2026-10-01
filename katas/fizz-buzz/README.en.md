# FizzBuzz

🇫🇷 [Version française](README.md) · Kata: [codingdojo.org/kata/FizzBuzz](https://codingdojo.org/kata/FizzBuzz/)

## The kata

Print the numbers from 1 to 100, replacing multiples of 3 with `Fizz`, multiples of 5 with `Buzz` and multiples of both with `FizzBuzz`.

**Stage 2**: a number is also `Fizz` when it contains a 3, and `Buzz` when it contains a 5.

## TDD walkthrough

1. `1 → "1"` and `2 → "2"`: return the number, `toString()` is enough.
2. `3 → Fizz`, then 6 and 9: first `if (n % 3 == 0)`.
3. `5 → Buzz`: a second `if`, mirroring the first one.
4. `15 → FizzBuzz`: instead of a third `if`, **concatenate** the words of every matching rule. The 15 case falls out for free.
5. *Refactoring*: both `if`s share the same shape, so they become a `Rule` (functional interface `wordFor(n): String?`). `FizzBuzz` no longer knows about 3 or 5; it receives a list of rules.
6. Sequence from 1 to 100: a plain `map` over the range.
7. **Stage 2**: thanks to the refactoring, the new requirement is just a new rule factory (`divisibleByOrContains`) and a new configuration (`stageTwo`). The core is untouched.

## Solution

```kotlin
fun say(number: Int): String =
    rules.mapNotNull { it.wordFor(number) }.joinToString("").ifEmpty { number.toString() }
```

- The order of the rule list drives the order of the words (`Fizz` before `Buzz`).
- `ifEmpty` removes the "no rule matched" special case.
- Adding `Whizz` for 7 needs no change to the class (open/closed principle).

## Takeaways

Test 15 is the turning point: either add an `if (n % 15 == 0)` (works, but duplicates), or switch to a composition of rules. Stage 2 confirms that choice after the fact.

## Running the tests

```bash
./gradlew :katas:fizz-buzz:test
```
