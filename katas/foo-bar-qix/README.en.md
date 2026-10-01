# FooBarQix

🇫🇷 [Version française](README.md) · Kata: [codingdojo.org/kata/FooBarQix](https://codingdojo.org/kata/FooBarQix/)

## The kata

`compute(String): String` applies, in this order:

- divisible by 3, 5, 7 → `Foo`, `Bar`, `Qix`;
- then, for each digit 3, 5, 7 of the number and in digit order → `Foo`, `Bar`, `Qix`.

**Step 2**: each 0 leaves a `*` trace (`101 → 1*1`, `105 → FooBarQix*Bar`).

## TDD walkthrough

🔴 red · 🟢 green · 🔵 refactor · 📌 test green on first run

| # | Step | What happened |
|---|---|---|
| 1 | 🔴 `keep a number no rule applies to` | Does not compile: `FooBarQix` does not exist. |
| 2 | 🟢 `return the number as is` | `return input`. |
| 3 | 🔴 `say Foo for 6` | Expected `"Foo"`, got `"6"`. |
| 4 | 🟢 `say Foo for multiples of 3` | One `if`. |
| 5 | 🔴 `say Bar for 10` | Expected `"Bar"`, got `"10"`. |
| 6 | 🟢 `say Bar for multiples of 5` | Concatenate `foo + bar`, fall back to the number when empty (a lesson from FizzBuzz). |
| 7 | 🔴 `add Foo for each digit 3` | `13`: expected `"Foo"`, got `"13"`. |
| 8 | 🟢 `add Foo for each digit 3` | A filter on `3` digits. |
| 9 | 🔴 `translate digits 3 and 5 in their order` | `53`: expected `"BarFoo"`, got `"Foo"`. |
| 10 | 🟢 `translate digits 3 and 5 in their order` | A `digit → word` table walked in digit order. |
| 11 | 🔵 `drive divisors and digits from one table` | The same ordered table also drives the divisors: no `if` left. |
| 12 | 🔴 `handle 7 as Qix` | `7`: expected `"QixQix"`, got `"7"`. |
| 13 | 🟢 `add Qix for 7` | **One line**: the `'7' to "Qix"` entry in the table. Refactoring 11 paid off at once. |
| 14 | 📌 `check every example of step 1` | The kata's 16 examples pass. |
| 15 | 🔵 `name the step 1 behaviour to prepare step 2` | Preparatory refactoring: `FooBarQix.step1` becomes a named configuration. Needed because step 2 changes the result for `10` (`Bar` becomes `Bar*`): both behaviours must coexist. |
| 16 | 🔴 `trace zeros of 101 in step 2` | `step2` does not exist. |
| 17 | 🟢 `replace zeros with stars when no rule applies` | The fallback turns `0`s into `*` in step 2. |
| 18 | 🔴 `interleave zero traces with digit words` | `303`: expected `"FooFoo*Foo"`, got `"FooFooFoo"`. |
| 19 | 🟢 `keep zero traces among the words` | `0` yields `*` in the digit part. Trap: for `101` the result would then be `"*"`; the result is kept only if it holds something other than `*`. |
| 20 | 🔵 `name the zero trace concepts` | `zeroTrace`, `withZeroTraces`, `hasWord`: the rule reads in the code. |
| 21 | 📌 `check every example of step 2` | `10 → Bar*`, `101`, `303`, `105`, `10101` pass. |

## Solution

```kotlin
fun compute(input: String): String {
    val number = input.toInt()
    val fromDivisors = WORDS.filterKeys { number % it.digitToInt() == 0 }.values.joinToString("")
    val fromDigits = input.mapNotNull { digit -> WORDS[digit] ?: zeroTrace(digit) }.joinToString("")
    val result = fromDivisors + fromDigits
    return if (result.hasWord()) result else withZeroTraces(input)
}
```

One ordered table `3 → Foo, 5 → Bar, 7 → Qix` drives both divisors and digits. Two configurations, `step1` and `step2`, expose both versions of the business rule.

## Takeaways

- The step 11 refactoring turned the arrival of `Qix` into a data change.
- Step 2 contradicts a step 1 example (`10`). Instead of editing a green test, both versions were made explicit, which is what happens with a real change request.

## Running the tests

```bash
./gradlew :katas:foo-bar-qix:test
git log --reverse --format='%s%n%b' strict-tdd-start.. -- katas/foo-bar-qix   # the TDD history
```
