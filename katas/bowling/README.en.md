# Bowling

🇫🇷 [Version française](README.md) · Kata: [codingdojo.org/kata/Bowling](https://codingdojo.org/kata/Bowling/)

## The kata

Score a 10-frame bowling game:

- a regular frame is worth the pins knocked down in two rolls;
- a **spare** (10 pins in two rolls) adds the next roll as a bonus;
- a **strike** (10 pins on the first roll) adds the next two rolls as a bonus;
- the tenth frame grants whatever bonus rolls are needed.

## TDD walkthrough

🔴 red · 🟢 green · 🔵 refactor · 📌 test green on first run

The sequence follows Uncle Bob's now classic one.

| # | Step | What happened |
|---|---|---|
| 1 | 🔴 `score a gutter game` | Does not compile: `BowlingGame` does not exist. |
| 2 | 🟢 `score zero` | `roll` does nothing, `score` returns 0. |
| 3 | 🔴 `score a game of ones` | Expected 20, got 0. |
| 4 | 🟢 `sum knocked down pins` | Rolls are recorded and summed. |
| 5 | 🔴 `add the next roll after a spare` | `5, 5, 3`: expected 16, got 13. Summing rolls is no longer enough, the game must be read **frame by frame**. |
| 6 | 🟢 `walk the game frame by frame to score spares` | A loop over 10 two-roll frames, with the spare bonus. Key point: raw rolls are kept and the score is computed at the end, instead of scoring as rolls come in. |
| 7 | 🔴 `add the next two rolls after a strike` | `IndexOutOfBoundsException`: the loop moves two rolls ahead even after a strike. |
| 8 | 🟢 `score strikes as one-roll frames` | A strike is a one-roll frame. |
| 9 | 🔵 `name strike, spare and their bonuses` | `isStrike`, `isSpare`, `strikeBonus`, `spareBonus`, `pinsInFrame`, constants `FRAMES` and `ALL_PINS`: `score` reads like the rules. |
| 10 | 📌 `score a perfect game and a game of spares` | 300 and 150 pass: the tenth frame and its bonuses need no special case. |
| 11 | 🔴 `reject a roll outside 0 to 10 pins` | `roll(11)` is accepted. |
| 12 | 🟢 `accept only 0 to 10 pins per roll` | A `require`. |
| 13 | 🔴 `reject a frame with more than 10 pins` | `6` then `5` in the same frame is accepted. |
| 14 | 🟢 `track frames to cap the pins of a frame at 10` | `roll` tracks the current frame and the pending first roll, for the first nine frames. |

## Solution

```kotlin
fun score(): Int {
    var score = 0
    var frameStart = 0
    repeat(FRAMES) {
        when {
            isStrike(frameStart) -> { score += ALL_PINS + strikeBonus(frameStart); frameStart += 1 }
            isSpare(frameStart) -> { score += ALL_PINS + spareBonus(frameStart); frameStart += 2 }
            else -> { score += pinsInFrame(frameStart); frameStart += 2 }
        }
    }
    return score
}
```

Known limit: pin validation does not cover the tenth frame's bonus rolls.

## Takeaways

The most important decision comes with the spare test: store the rolls and compute the score on demand. The bonus becomes a simple look-ahead in the list, and the tenth frame needs no special code (step 10).

## Running the tests

```bash
./gradlew :katas:bowling:test
git log --reverse --format='%s%n%b' strict-tdd-start.. -- katas/bowling   # the TDD history
```
