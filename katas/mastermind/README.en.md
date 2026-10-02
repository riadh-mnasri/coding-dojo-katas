# Mastermind

🇫🇷 [Version française](README.md) · Kata: [codingdojo.org/kata/Mastermind](https://codingdojo.org/kata/Mastermind/)

## The kata

Play the boring *codemaker* role: for a secret and a guess, answer how many colors are **well placed** and how many are **present but misplaced**.

## TDD walkthrough

🔴 red · 🟢 green · 🔵 refactor · 📌 test green on first run

The kata advises starting with well placed colors, and reminds that misplaced ones are "about counting".

| # | Step | What happened |
|---|---|---|
| 1 | 🔴 `answer nothing for a wrong single peg` | Does not compile: no `Color`, `Answer` or `Mastermind`. |
| 2 | 🟢 `answer nothing` | `Answer(0, 0)`. |
| 3 | 🔴 `count a well placed peg` | `[blue]` against `[blue]`: expected `(1, 0)`. |
| 4 | 🟢 `count pegs matching position by position` | `zip`, then count equal pairs. |
| 5 | 🔴 `count a misplaced peg` | `[red, yellow]` against `[blue, red]`: expected `(0, 1)`. |
| 6 | 🟢 `count guessed colors present elsewhere in the secret` | Naive version: a guessed color, misplaced, and present in the secret. |
| 7 | 📌 `answer the kata example` | The kata's example passes with the naive version. |
| 8 | 🔴 `count a repeated guessed color only once` | Secret `[red, blue, green]`, guess `[green, green, yellow]`: the naive version counts two misplaced for a single green. |
| 9 | 🟢 `count misplaced colors by occurrences` | Drop the well placed pairs, then for each color take the minimum of its remaining occurrences in the secret and in the guess. |
| 10 | 📌 `never count a well placed peg as misplaced too` | `[red, blue]` against `[red, red]`: `(1, 0)`, already guaranteed by dropping well placed pairs. |
| 11 | 🔴 `refuse combinations of different sizes` | A shorter guess goes through. |
| 12 | 🟢 `require combinations of the same size` | A `require`. |
| 13 | 🔵 `name the unmatched pegs` | `wellPlaced` / `unmatched` and a comment on the minimum rule. |

## Solution

```kotlin
val (wellPlaced, unmatched) = secret.zip(guess).partition { (s, g) -> s == g }
val secretLeft = unmatched.groupingBy { (s, _) -> s }.eachCount()
val guessLeft = unmatched.groupingBy { (_, g) -> g }.eachCount()
val misplaced = guessLeft.entries.sumOf { (color, count) -> minOf(count, secretLeft[color] ?: 0) }
```

## Takeaways

The kata's example holds no duplicate, and the naive version passes it. The test deliberately built with a repeated color (step 8) is what forces the move from a membership check to counting.

## Running the tests

```bash
./gradlew :katas:mastermind:test
git log --reverse --format='%s%n%b' strict-tdd-start.. -- katas/mastermind   # the TDD history
```
