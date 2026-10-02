# Tennis

🇫🇷 [Version française](README.md) · Kata: [codingdojo.org/kata/Tennis](https://codingdojo.org/kata/Tennis/)

## The kata

Call the score of a tennis game:

- points are called `Love`, `Fifteen`, `Thirty`, `Forty`;
- a tie is called `All` (`Fifteen-All`), and from 40-40 on it is `Deuce`;
- after deuce, a one-point lead is `Advantage`, a two-point lead wins;
- a game is won with at least 4 points and a 2-point lead.

## TDD walkthrough

🔴 red · 🟢 green · 🔵 refactor · 📌 test green on first run

| # | Step | What happened |
|---|---|---|
| 1 | 🔴 `start at love all` | Does not compile: `TennisGame` does not exist. |
| 2 | 🟢 `announce love all` | `return "Love-All"`. |
| 3 | 🔴 `call each running score` | Expected `"Fifteen-Love"`, got `"Love-All"`. (I first committed this test with a stray expression and a `Thirty-Thirty` expectation that broke the `All` convention; fixed by amending the red commit before pushing.) |
| 4 | 🟢 `name the points of each player` | Two counters and a list of names. |
| 5 | 🔴 `call equal scores all` | Expected `"Thirty-All"`, got `"Thirty-Thirty"`. |
| 6 | 🟢 `call equal scores all` | A tie case, which also covers `Love-All`. |
| 7 | 🔴 `call deuce from forty all` | Expected `"Deuce"`, got `"Forty-All"`. |
| 8 | 🟢 `call deuce` | A tie from 3 points on. |
| 9 | 🔴 `give advantage after deuce` | Index 4 outside the name list. |
| 10 | 🟢 `give advantage to the leader after deuce` | Both players at 3 points or more, no tie: advantage to the leader. |
| 11 | 🔴 `win with four points and a two-point lead` | 4-0 crashes, 3-5 still calls an advantage. |
| 12 | 🟢 `win with four points and a two-point lead` | The winning rule, checked first. |
| 13 | 🔵 `express the score rules in tennis vocabulary` | `hasWinner`, `isDeuce`, `bothReachedForty`, `leader`: the `when` reads like the rules. |
| 14 | 📌 `go back to deuce when the advantage is lost` | 4-4 is `Deuce` again with no extra code: points are counted, states are not stored. |
| 15 | 🔴 `refuse points after the game and from strangers` | No guard at all. |
| 16 | 🟢 `guard against late points and unknown players` | A `require` (invalid argument) and a `check` (invalid state). |

## Solution

```kotlin
fun score(): String = when {
    hasWinner() -> "Win for ${leader()}"
    isDeuce() -> "Deuce"
    points1 == points2 -> "${CALLS[points1]}-All"
    bothReachedForty() -> "Advantage ${leader()}"
    else -> "${CALLS[points1]}-${CALLS[points2]}"
}
```

## Takeaways

A state machine (`Deuce`, `Advantage`...) was an option. Keeping two counters and **deriving** the call made the return to deuce free (step 14). The order of the `when` branches carries the whole rule priority.

## Running the tests

```bash
./gradlew :katas:tennis:test
git log --reverse --format='%s%n%b' strict-tdd-start.. -- katas/tennis   # the TDD history
```
