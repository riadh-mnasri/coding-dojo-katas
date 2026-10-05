# PacMan

🇫🇷 [Version française](README.md) · Kata: [codingdojo.org/kata/PacMan](https://codingdojo.org/kata/PacMan/)

## The kata

Pac-Man moves on a grid full of dots. The kata gives a deliberately incomplete list: he has a direction, moves on each tick, can be turned, eats dots, wraps around, stops on walls, will not turn into a wall, scores points; then monsters, levels, animation...

The board is text, as the kata suggests. Pac-Man is drawn with his mouth open: `V` when looking up, `^` down, `>` left, `<` right.

## TDD walkthrough

🔴 red · 🟢 green · 🔵 refactor · 📌 test green on first run

As the kata advises, the state changes in discrete steps: a `tick()` method and a text rendering make every rule easy to test, board before, board after.

| # | Step | What happened |
|---|---|---|
| 1 | 🔴 `move up on a tick and eat the dot` | Does not compile. (My expectation used an unreadable `replaceRange`: rewritten plainly by amending the commit, before pushing.) |
| 2 | 🟢 `move forward on each tick and eat dots` | A character grid, Pac-Man's position and direction. |
| 3 | 🔴 `turn pacman` | `turn` does not exist. |
| ⛔ | rejected green | My expectation forgot the dot left on the right after a step to the left: the test was wrong. Fixed by amending the red commit (not pushed), then green replayed. |
| 4 | 🟢 `turn pacman` | New direction, new drawing. |
| 5 | 🔴 `wrap around the edges of the board` | `IndexOutOfBoundsException` at the edge. |
| 6 | 🟢 `wrap around the edges` | `floorMod` on both axes. |
| 7 | 🔴 `stop in front of a wall` | Pac-Man walks through the wall. |
| 8 | 🟢 `stop in front of walls` | A wall ahead: no move. |
| 9 | 🔴 `refuse to turn towards a wall` | Turning towards a wall is accepted. |
| 10 | 🟢 `ignore a turn towards a wall` | The cell "ahead" is computed by a single function, `ahead`, shared by moving and turning. |
| 11 | 🔴 `complete the level when every dot is eaten` | `isLevelComplete` does not exist. |
| 12 | 🟢 `complete the level when no dot is left` | No dot left on the board. |
| 13 | 🔴 `end the game when pacman runs into a monster` | `isOver` does not exist. |
| 14 | 🟢 `end the game on a monster` | A monster ahead: game over, and nothing moves afterwards. |
| 15 | 🔵 `read the tick as one decision per kind of cell` | A `when` on the cell ahead: monster, wall, or move. |

## Scope

Done: direction, ticks, turning, dots, edges, walls, score, level completion, static monsters. Not done, as the kata itself admits ("you probably won't complete all of the list in one night"): moving monsters, chaining levels and the mouth animation.

## Takeaways

A text board, before and after a tick, makes tests that read like drawings. The `ahead` function, extracted when turning needed it, avoided duplicating the edge computation.

## Running the tests

```bash
./gradlew :katas:pac-man:test
git log --reverse --format='%s%n%b' strict-tdd-start.. -- katas/pac-man   # the TDD history
```
