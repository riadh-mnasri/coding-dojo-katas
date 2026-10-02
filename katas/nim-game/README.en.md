# NimGame

🇫🇷 [Version française](README.md) · Kata: [codingdojo.org/kata/Nim](https://codingdojo.org/kata/Nim/)

## The kata

A two-player Nim game, 10 sticks to start with, optionally a chosen number of sticks and player names, and a front end (here a terminal).

The kata does not fix the end rule. I picked the matchstick variant well known in France (Fort Boyard): each player takes **1 to 3** sticks, and **whoever takes the last one loses**.

## TDD walkthrough

🔴 red · 🟢 green · 🔵 refactor · 📌 test green on first run

| # | Step | What happened |
|---|---|---|
| 1 | 🔴 `start with ten sticks and the first player` | Does not compile. |
| 2 | 🟢 `set up ten sticks and the first player` | Two hard-coded values. |
| 3 | 🔴 `take sticks and hand over the turn` | `take` does not exist. |
| 4 | 🟢 `take sticks and alternate players` | Countdown and alternation. |
| 5 | 🔴 `take one to three sticks only` | 0, 4 and -1 accepted. |
| 6 | 🟢 `allow one to three sticks per turn` | A `require`. |
| 7 | 🔴 `never take more sticks than remain` | Taking 2 with 1 left is accepted. |
| 8 | 🟢 `cap a move at the remaining sticks` | A second `require`. |
| 9 | 🔴 `make the player taking the last stick lose` | `winner` does not exist. |
| 10 | 🟢 `declare the other player the winner` | When nothing is left, the player whose turn it is (who did not take the last one) has won: no extra state. |
| 11 | 🔴 `refuse moves once the game is over` | A move after the end throws the wrong exception (`IllegalArgumentException` instead of `IllegalStateException`). |
| 12 | 🟢 `refuse moves once there is a winner` | A `check` on the state, distinct from the `require`s on the argument. |
| 13 | 🔴 `start with a chosen number of sticks` | The `sticks` parameter does not exist. |
| 14 | 🟢 `choose the starting number of sticks` | A parameter defaulting to 10. **Slip**: I also added a `require(sticks > 0)` that no test asked for yet. |
| 15 | 🔵 `add a thin console front end over the tested game` | `Console.kt`: a terminal read loop, logic-free and untested. |
| 16 | 📌 `cover the check on the starting sticks added without a red test` | Making up for the step 14 slip: the test can only pass on first run, which is exactly the point. That `require` was not driven by a test. |

## Solution

```kotlin
val winner: String? get() = if (sticks == 0) currentPlayer else null

fun take(count: Int) {
    check(winner == null) { "The game is over, $winner won" }
    require(count in 1..3) { "A player takes 1 to 3 sticks, not $count" }
    require(count <= sticks) { "Only $sticks sticks left" }
    sticks -= count
    currentPlayer = if (currentPlayer == first) second else first
}
```

To play: run `main` in `Console.kt` from the IDE.

## Takeaways

- The winner is **derived** from the state (no sticks left, whose turn it is) instead of being stored.
- Telling `require` (wrong argument) from `check` (wrong moment) makes errors clearer, and a test forced the distinction.
- A 📌 test on a validation rule often reveals code written ahead of the tests.

## Running the tests

```bash
./gradlew :katas:nim-game:test
git log --reverse --format='%s%n%b' strict-tdd-start.. -- katas/nim-game   # the TDD history
```
