# Args

🇫🇷 [Version française](README.md) · Kata: [codingdojo.org/kata/Args](https://codingdojo.org/kata/Args/)

## The kata

The argument parser from chapter 14 of *Clean Code*. A schema describes the expected flags and their types; the parser checks the arguments and returns typed values:

```kotlin
val args = Args("l,p#,d*", listOf("-l", "-p", "8080", "-d", "/usr/logs"))
args.boolean('l')   // true
args.int('p')       // 8080
args.string('d')    // "/usr/logs"
```

The schema format is left open: I reused Uncle Bob's (`l` boolean, `p#` integer, `d*` string), with `[*]` and `[#]` for lists. An absent flag takes a default value, and every error must be explained precisely.

## TDD walkthrough

🔴 red · 🟢 green · 🔵 refactor · 📌 test green on first run

| # | Step | What happened |
|---|---|---|
| 1 | 🔴 `read a boolean flag` | Does not compile. |
| 2 | 🟢 `say every boolean flag is present` | `return true`. |
| 3 | 🔴 `default an absent boolean to false` | Exposes the hard-coded value. |
| 4 | 🟢 `find a flag among the arguments` | `"-l" in arguments`. |
| 5 | 🔴 `read an integer and a string value` | `int` and `string` do not exist. |
| 6 | 🟢 `parse values according to the schema` | The schema gives a type code per flag; arguments are walked, the value being the next argument. |
| 7 | 🔴 `give defaults for absent flags` | `NullPointerException` on an absent integer. |
| 8 | 🟢 `default to 0 and empty string` | Default values. |
| 9 | 📌 `accept negative integers and any argument order` | `-p -3`: since the value is read by the flag expecting it, `-3` is never mistaken for a flag. |
| 10 | 🔴 `explain exactly what is wrong` | Five expected errors, with their exact message. |
| 11 | 🟢 `report unknown flags, missing and invalid values` | An `ArgsException` and one message per case. |
| 12 | 🔵 `give each value type its own marshaler` | One `Marshaler` per type (default value + parsing), a code → marshaler table. **Slip**: this refactoring also introduced two new checks (unknown type in the schema, flag outside the schema), new behaviour that has no place in a refactoring. |
| 13 | 🔴 `read lists of strings and integers` | `strings` and `ints` do not exist. |
| 14 | 🟢 `add list marshalers for strings and integers` | A `ListMarshaler` delegating to an element marshaler: `[*]` and `[#]` are **two rows of the table**. **Slip**: I also wrote an error message no test asked for. |
| 15 | 📌 `cover the schema errors added by the marshaler refactoring` | Making up for the step 12 slip. |
| 16 | 📌 `cover the missing list error written ahead of its test` | Making up for the step 14 slip. |

## Solution

```kotlin
val MARSHALERS: Map<String, Marshaler> = mapOf(
    "" to BooleanMarshaler,
    "#" to IntMarshaler,
    "*" to StringMarshaler,
    "[*]" to ListMarshaler(StringMarshaler),
    "[#]" to ListMarshaler(IntMarshaler),
)
```

Adding a type (a `double`, a date...) means writing a marshaler and adding it to the table, without touching the argument walk: the extensibility the kata asks for.

## Takeaways

The catch-up 📌 tests (steps 15 and 16) point to the same habit: writing a bit of "common sense" along the way, without a red test first. That code is not wrong, but it was not test-driven, and I would rather the history showed it.

## Running the tests

```bash
./gradlew :katas:args:test
git log --reverse --format='%s%n%b' strict-tdd-start.. -- katas/args   # the TDD history
```
