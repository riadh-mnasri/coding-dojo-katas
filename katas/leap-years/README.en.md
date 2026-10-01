# Leap Years

🇫🇷 [Version française](README.md) · Kata: [codingdojo.org/kata/LeapYears](https://codingdojo.org/kata/LeapYears/)

## The kata

Tell whether a year is a leap year in the Gregorian calendar:

1. divisible by 400: leap year (2000);
2. divisible by 100 but not by 400: not a leap year (1900, 2100);
3. divisible by 4 but not by 100: leap year (2012);
4. not divisible by 4: not a leap year (2019).

Extension: years divisible by 4000 are not leap years.

## TDD walkthrough

🔴 red · 🟢 green · 🔵 refactor · 📌 test green on first run

The kata lists the criteria from the most specific to the most general. I took the reverse order, from the general case to its exceptions: each test then adds a single exception.

| # | Step | What happened |
|---|---|---|
| 1 | 🔴 `reject years not divisible by 4` | Does not compile: `LeapYear` does not exist. |
| 2 | 🟢 `no year is a leap year yet` | `return false` is enough for 2017, 2018, 2019. |
| 3 | 🔴 `accept years divisible by 4` | 2008, 2012, 2016: expected `true`, got `false`. |
| 4 | 🟢 `years divisible by 4 are leap years` | `year % 4 == 0`. |
| 5 | 🔴 `reject centuries` | 1700, 1800, 1900, 2100: expected `false`, got `true`. |
| 6 | 🟢 `centuries are not leap years` | `&& year % 100 != 0`. |
| 7 | 🔴 `accept years divisible by 400` | 1600, 2000, 2400: expected `true`, got `false`. |
| 8 | 🟢 `years divisible by 400 are leap years` | `year % 400 == 0 \|\| (...)`: it passes, but the expression gets hard to read. |
| 9 | 🔵 `read the rules in priority order` | A `when` where each branch is one rule, from highest priority to most general, with an `isDivisibleBy` extension. |
| 10 | 🔴 `reject years divisible by 4000 with the extra rule` | The `withFourThousandYearRule` parameter does not exist. |
| 11 | 🟢 `add the optional 4000-year rule` | A branch at the top of the `when`, active only when the option is set: the Gregorian behaviour stays the default. |
| 12 | 📌 `keep other leap years under the 4000-year rule` | 2000, 2024, 4004 remain leap years with the option. |

## Solution

```kotlin
fun isLeap(year: Int): Boolean = when {
    withFourThousandYearRule && year.isDivisibleBy(4000) -> false
    year.isDivisibleBy(400) -> true
    year.isDivisibleBy(100) -> false
    else -> year.isDivisibleBy(4)
}
```

## Takeaways

Going from the general rule to its exceptions gives tiny green steps, but a boolean expression that grows with each test. The step 9 refactoring brings back the readability of the statement: one rule per line, in priority order.

## Running the tests

```bash
./gradlew :katas:leap-years:test
git log --reverse --format='%s%n%b' strict-tdd-start.. -- katas/leap-years   # the TDD history
```
