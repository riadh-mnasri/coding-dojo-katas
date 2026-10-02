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
| ⛔ | rejected green attempt | The `check` is right, but two older tests break: my `points(serena, venus)` helper played all of one player's points before the other's, so `4-3` went through `4-0`, a win. The guard refused the commit. Because of a mistake in how I chained my commands, this red code still went into the next documentation commit, already pushed; rather than rewrite published history, I fixed it in the following commit. |
| 16 | 🟢 `play points alternately in the test helper` | The helper plays points alternately, like a real game, then the leader's extra points. The whole suite is green again, with the `require` (invalid argument) and `check` (invalid state) guards. |

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

A test helper is code: this one encoded a wrong assumption (point order does not matter), which only surfaced once the code started checking the game state.

A state machine (`Deuce`, `Advantage`...) was an option. Keeping two counters and **deriving** the call made the return to deuce free (step 14). The order of the `when` branches carries the whole rule priority.

## Running the tests

```bash
./gradlew :katas:tennis:test
git log --reverse --format='%s%n%b' strict-tdd-start.. -- katas/tennis   # the TDD history
```
