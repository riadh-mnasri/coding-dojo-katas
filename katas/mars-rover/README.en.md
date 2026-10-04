# Mars Rover

🇫🇷 [Version française](README.md) · Kata: [codingdojo.org/kata/mars-rover](https://codingdojo.org/kata/mars-rover/)

## The kata

Simulate a Mars rover from an emoji map and a list of commands:

```
🟩🟩🌳🟩🟩
🟩🟩🟩🟩🟩
🟩🟩🟩🌳🟩
🟩🌳🟩🟩🟩
➡️🟩🟩🟩🟩
```

- the arrow gives the starting position and direction;
- `⬆️` moves forward, `➡️` turns right, `⬅️` turns left;
- in front of an obstacle (🌳, 🪨), the rover does nothing.

Choices where the kata says nothing: x goes left to right and y goes **bottom to top** (the last line is y = 0), and the edge of the map behaves like an obstacle.

## TDD walkthrough

🔴 red · 🟢 green · 🔵 refactor · 📌 test green on first run

| # | Step | What happened |
|---|---|---|
| 1 | 🔴 `find the rover on the map` | Does not compile. |
| 2 | 🟢 `read emoji tiles and locate the rover` | The technical trap: `🟩` takes two `char`s (a surrogate pair), and `➡️` is `➡` followed by a `U+FE0F` variation selector. Tiles are therefore split by **code points**, ignoring `U+FE0F`. |
| 3 | 🔴 `move forward` | `execute` does not exist. |
| 4 | 🟢 `move forward` | One tile in the current direction. |
| 5 | 🔴 `turn right and left` | The direction does not change. |
| 6 | 🟢 `turn right and left` | `right()` / `left()` on the direction enum. |
| 7 | 🔴 `stay put in front of an obstacle` | The rover drives through the tree. |
| 8 | 🟢 `stop in front of trees and rocks` | The mission keeps the tiles; a 🌳 or 🪨 tile blocks the move. |
| 9 | 🔴 `stay on the map at its edges` | The rover leaves to x = -1. |
| 10 | 🟢 `treat the edge of the map as a wall` | A tile outside the map blocks too. |
| 11 | 🔵 `give the rover its moves and the map its terrain` | `Rover.forward(terrain)`, `turnLeft`, `turnRight` and a `Terrain` class; `execute` becomes a `fold` over the commands. **Slip**: I slipped in the rejection of unknown commands, new behaviour inside a refactoring. |
| 12 | 📌 `drive across both maps of the kata` | Both maps of the kata, routes checked by hand. |
| 13 | 🔵 `fix the comment describing the second map's route` | My comment misdescribed the route (the assertion was right). |
| 14 | 📌 `cover unknown commands, rejected by the previous refactoring` | Making up for the step 11 slip. |
| 15 | 🔴 `reject a map without a rover` | An unhelpful `NoSuchElementException`. |
| 16 | 🟢 `report a map without a rover` | An explicit message. |

## Solution

```kotlin
fun execute(commands: String): Rover = tiles(commands).fold(rover) { current, command ->
    when (command) {
        "⬆" -> current.forward(terrain)
        "➡" -> current.turnRight()
        "⬅" -> current.turnLeft()
        else -> throw IllegalArgumentException("Unknown command $command")
    }
}
```

## Takeaways

- An emoji is not a `char`. The very first test, on a real map, forced that right away.
- A refactoring must not add behaviour; when it happens to me, the 📌 test that follows makes it visible instead of hiding it.

## Running the tests

```bash
./gradlew :katas:mars-rover:test
git log --reverse --format='%s%n%b' strict-tdd-start.. -- katas/mars-rover   # the TDD history
```
