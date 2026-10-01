# Leap Years

🇫🇷 [Version française](README.md) · Kata: [codingdojo.org/kata/LeapYears](https://codingdojo.org/kata/LeapYears/)

## The kata

Tell whether a year is a leap year in the Gregorian calendar:

1. divisible by 400: leap year (2000);
2. divisible by 100 but not by 400: not a leap year (1900, 2100);
3. divisible by 4 but not by 100: leap year (2012);
4. not divisible by 4: not a leap year (2019).

Extension (story 2): years divisible by 4000 are not leap years.

## TDD walkthrough

The order of the acceptance criteria is not the best order for tests. I started with the most general case:

1. 2017, 2018, 2019 → `false`: `return false` is enough.
2. 2008, 2012, 2016 → `true`: `year % 4 == 0`.
3. 1700, 1800, 1900, 2100 → `false`: an exception to the previous rule, `% 100` before `% 4`.
4. 1600, 2000, 2400 → `true`: the exception to the exception, `% 400` first.
5. *Refactoring*: the `if` cascade becomes a `when` that reads in priority order, with a small `isDivisibleBy` extension.
6. Story 2: the 4000-year rule is a constructor option, off by default, so the Gregorian behaviour is preserved.

## Solution

```kotlin
fun isLeap(year: Int): Boolean = when {
    withMillenniumRule && year.isDivisibleBy(4000) -> false
    year.isDivisibleBy(400) -> true
    year.isDivisibleBy(100) -> false
    else -> year.isDivisibleBy(4)
}
```

Each `when` branch maps to one acceptance criterion, from the most specific to the most general.

## Takeaways

Ordering tests from the general rule to its exceptions naturally reveals the order of the branches in the code.

## Running the tests

```bash
./gradlew :katas:leap-years:test
```
