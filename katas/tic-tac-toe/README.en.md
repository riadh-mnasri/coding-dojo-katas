# Tic Tac Toe

🇫🇷 [Version française](README.md) · Kata: [codingdojo.org/kata/tic-tac-toe](https://codingdojo.org/kata/tic-tac-toe/)

## The kata

Two players, X and O, take turns taking a free field of a 3 × 3 board numbered 1 to 9. The game ends when a player owns a full row, column or diagonal, or when every field is taken.

The kata presents itself as an introduction to **double-loop TDD**.

## TDD walkthrough: double loop

🔴 red · 🟢 green · 🔵 refactor · 📌 test green on first run

- **Outer loop**: an acceptance test describes a whole game from the user's point of view. It stays red for a long time.
- **Inner loop**: unit tests, one rule at a time, build what it needs to pass.

My guard refuses any green commit while a test fails. The acceptance test was therefore **parked** (`@Disabled`) in an explicit commit, then re-enabled at the end.

| # | Step | What happened |
|---|---|---|
| 1 | 🔴 `describe a whole game won by X as the acceptance test` | Outer loop: X wins on the diagonal, with the expected final board. Does not compile. |
| 2 | 🟢 `sketch the game API and park the acceptance test` | The API (`play`, `board`, `isOver`, `winner`) as `TODO()`s. I checked, without committing, that the acceptance test then fails with `NotImplementedError`, then parked it. |
| 3 | 🔴 `let X take a field first` | Inner loop. `ownerOf` does not exist. |
| 4 | 🟢 `record the field taken by X` | A field → player `Map`. |
| 5 | 🔴 `let players take turns` | The second field also goes to X. |
| 6 | 🟢 `alternate X and O` | A current player that alternates. |
| 7 | 🔴 `refuse a field already taken` | A taken field can be taken again. |
| 8 | 🟢 `refuse taken fields` | A `require`. |
| 9 | 🔴 `win with a full row` | `winner()` is still a `TODO()`. |
| 10 | 🟢 `win with a full row` | A list of winning lines, limited to the three rows. |
| ⛔ | red step redone | My first "column" test held a scenario where X actually owned `4 5 6`: it wrongly expected "no winner". Undone before pushing, then replayed with a real column scenario. |
| 11 | 🔴 `win with a full column` | O owns `1 4 7`: no winner detected. |
| 12 | 🟢 `win with a full column` | Three more lines in the list. |
| 13 | 🔴 `win with a full diagonal` | No winner detected. |
| 14 | 🟢 `win with a full diagonal` | Two more lines. |
| 15 | 🔴 `end the game when all fields are taken` | A draw: the game does not end. |
| 16 | 🟢 `end the game when the board is full` | `isOver` = a winner **or** 9 taken fields. |
| 17 | 🔴 `refuse moves once the game is over` | Moves are allowed after a win. |
| 18 | 🟢 `refuse moves after the end` | A `check`. |
| 19 | 🔴 `draw the board after X plays on 5` | The kata's test case; `board()` is a `TODO()`. |
| 20 | 🟢 `draw the board` | Row-by-row rendering, the number for a free field. |
| 21 | 📌 `re-enable the acceptance test, now green` | **End of the outer loop**: the test parked at step 2 passes with no extra code. |

## Solution

```kotlin
fun winner(): Player? = LINES.firstNotNullOfOrNull { line ->
    fields[line.first()]?.takeIf { player -> line.all { fields[it] == player } }
}

fun isOver(): Boolean = winner() != null || fields.size == 9
```

## Takeaways

The acceptance test acted as a **goal**: it said when to stop. The inner loop made the rules emerge one by one, including two it did not cover (draw, move after the end).

## Running the tests

```bash
./gradlew :katas:tic-tac-toe:test
git log --reverse --format='%s%n%b' strict-tdd-start.. -- katas/tic-tac-toe   # the TDD history
```
