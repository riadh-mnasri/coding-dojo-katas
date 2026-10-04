# Employee Report

🇫🇷 [Version française](README.md) · Kata: [codingdojo.org/kata/Employee-Report](https://codingdojo.org/kata/Employee-Report/)

## The kata

A report for a grocery store opening on Sundays, where employees under 18 may not work. Four user stories, taken one at a time without reading ahead:

1. list the employees who may work on Sundays;
2. sort them by name;
3. capitalize the names;
4. sort by name **descending** instead of ascending.

The real topic: showing how **over-specified assertions** make tests brittle.

## TDD walkthrough

🔴 red · 🟢 green · 🔵 refactor · 📌 test green on first run

Each test checks **only** its story's requirement: the filter ignores order and case, sorting ignores content, capitalization ignores order. A story therefore never breaks another story's test.

| # | Step | What happened |
|---|---|---|
| 1 | 🔴 `keep only employees allowed to work on Sundays` | Does not compile. Assertion: `containsExactlyInAnyOrder` on lower-cased names. |
| 2 | 🟢 `keep employees of 18 or more` | `age >= 18`. The kata says "older than 18", but the legal rule bans those **under** 18: Sepp, aged 18, may work. |
| 3 | 🔴 `sort the report by name` | Assertion: the list is sorted (`isSortedAccordingTo`), without freezing its content. |
| 4 | 🟢 `sort names` | `sorted()`. |
| 5 | 🔴 `capitalize the names` | Assertion: every name is upper case (`allSatisfy`). |
| 6 | 🟢 `capitalize names` | `uppercase()`. Tests 1 and 3 stay green **unchanged**, thanks to their focused assertions. |
| 7 | 🔴 `sort names in descending order` | Story 2's requirement changes: I **edit** its test (reversed order) instead of adding a second one contradicting the first. |
| 8 | 🟢 `sort names in descending order` | `sortedDescending()`. |
| 9 | 🔵 `name the legal Sunday age` | A `MINIMUM_SUNDAY_AGE` constant and a comment on the rule. |
| 10 | 📌 `show the whole report in one readable example` | A single test freezes the full output (`SEPP`, `MIKE`), as documentation. |

## Solution

```kotlin
fun sundayWorkers(): List<String> = employees
    .filter { it.age >= MINIMUM_SUNDAY_AGE }
    .map { it.name.uppercase() }
    .sortedDescending()
```

## Takeaways

Had the first test been `containsExactly("Sepp", "Mike")`, stories 2, 3 and 4 would each have broken it, without any real regression. An assertion should describe the requirement under test, not the current output.

## Running the tests

```bash
./gradlew :katas:employee-report:test
git log --reverse --format='%s%n%b' strict-tdd-start.. -- katas/employee-report   # the TDD history
```
