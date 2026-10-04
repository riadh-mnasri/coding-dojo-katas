# Langton Ant

🇫🇷 [Version française](README.md) · Kata: [codingdojo.org/kata/LangtonAnt](https://codingdojo.org/kata/LangtonAnt/)

## The kata

A cellular automaton: on a plane of white or black squares, an ant moves step by step.

- on a **white** square: it turns right, the square becomes black, it moves forward;
- on a **black** square: it turns left, the square becomes white, it moves forward.

**Extension**: a third color, with a white → black → red → white cycle; on a red square the ant does not turn.

## TDD walkthrough

🔴 red · 🟢 green · 🔵 refactor · 📌 test green on first run

| # | Step | What happened |
|---|---|---|
| 1 | 🔴 `turn right on a white square` | Does not compile: no `World`, `Ant` or `Color`. |
| 2 | 🟢 `turn right, flip to black and move on white` | An infinite plane stored as a `Map` of painted squares (white by default). Only the white rule exists. |
| 3 | 🔴 `turn left on a black square` | Four steps bring the ant back to the black origin; the fifth must turn left, the white rule turns right. |
| 4 | 🟢 `turn left and flip to white on black` | An `if` on the color. |
| 5 | 🔵 `describe the automaton as a table of rules` | One `Rule` (how to turn, which color to paint) per color; the `World` receives its table. |
| 6 | 🔴 `cycle through white, black and red` | `RED` and `THREE_COLORS` do not exist. (My first draft of this test did not check crossing a red square, and a comment was wrong: I fixed it by amending the red commit before pushing, with expected values worked out by hand over 9 steps.) |
| 7 | 🟢 `add red with its circular rules` | One more color and a new rule table: **not a single line** of the engine changes. |

## Solution

```kotlin
val THREE_COLORS = mapOf(
    Color.WHITE to Rule(Direction::right, Color.BLACK),
    Color.BLACK to Rule(Direction::left, Color.RED),
    Color.RED to Rule({ it }, Color.WHITE),
)

fun step() {
    val rule = rules.getValue(colorAt(ant.position))
    val direction = rule.turn(ant.direction)
    colors[ant.position] = rule.paint
    ant = Ant(ant.position.moved(direction), direction)
}
```

## Takeaways

The refactoring into a rule table happened **before** the extension was known, simply because two symmetric branches suggested it. The extension then became a matter of data.

## Running the tests

```bash
./gradlew :katas:langton-ant:test
git log --reverse --format='%s%n%b' strict-tdd-start.. -- katas/langton-ant   # the TDD history
```
