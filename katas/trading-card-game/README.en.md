# Trading Card Game

🇫🇷 [Version française](README.md) · Kata: [codingdojo.org/kata/TradingCardGame](https://codingdojo.org/kata/TradingCardGame/)

## The kata

A two-player card game inspired by Hearthstone:

- each player starts with 30 health, 0 mana slots, a 20-card deck (costs `0,0,1,1,2,2,2,3,3,3,3,4,4,4,5,5,6,6,7,8`) and 3 cards in hand;
- on their turn, the player gains a mana slot (10 at most), refills them, draws, then plays as many cards as they can afford; a card deals as much damage as its cost;
- **Bleeding Out**: drawing from an empty deck costs 1 health;
- **Overload**: a card drawn while the hand already holds 5 is discarded;
- **Dud Card**: 0-cost cards are free and deal nothing;
- a player whose health drops to 0 or below loses.

## TDD walkthrough

🔴 red · 🟢 green · 🔵 refactor · 📌 test green on first run

First a player's state (`Player`), then the game loop (`Game`). Tests fix the deck order; only the full game shuffles decks with a fixed-seed `Random`.

| # | Step | What happened |
|---|---|---|
| 1 | 🔴 `start with 30 health, no mana and three cards in hand` | Does not compile. |
| 2 | 🟢 `set up a player with three starting cards` | The first three cards of the deck. |
| 3 | 🔴 `start a turn with a new mana slot, refilled, and a new card` | `startTurn` does not exist. |
| 4 | 🟢 `start a turn` | One more slot, full mana, one card. |
| 5 | 🔴 `cap the mana slots at ten` | 12 slots after 12 turns. |
| 6 | 🟢 `cap the mana slots at ten` | `minOf(..., 10)`. |
| 7 | 🔴 `play a card to damage the opponent` | `play` does not exist. (My test deck was too short for three draws: fixed by amending the red commit, before pushing.) |
| 8 | 🟢 `play a card against the opponent` | Mana spent, card removed, damage dealt. |
| 9 | 🔴 `refuse cards that cannot be afforded or are not in hand` | Nothing is checked. |
| 10 | 🟢 `require cards in hand and affordable` | A `require` (wrong card) and a `check` (not enough mana). |
| 11 | 🔴 `bleed out when drawing from an empty deck` | `NoSuchElementException` on the empty deck. |
| 12 | 🟢 `bleed out on an empty deck` | 1 health lost instead. |
| 13 | 🔴 `discard a card drawn into a full hand` | The hand grows to 6 cards. |
| 14 | 🟢 `discard cards drawn into a full hand` | Drawing moves into `draw()`, with both special rules. |
| 15 | 📌 `let dud cards cost and deal nothing` | A 0 card costs and does nothing, with no special code. |
| 16 | 🔴 `play a turn and hand over to the opponent` | `Game` does not exist. |
| 17 | 🟢 `play the most expensive affordable cards, then switch players` | The computer's strategy: the most expensive affordable card, as long as there is one. |
| 18 | 🔴 `declare the winner when the opponent drops to zero` | `winner` does not exist. |
| 19 | 🟢 `stop the game when a player's health drops to zero` | Checked after each card played. **Slip**: I also handled death by Bleeding Out at the start of the turn, without a red test. |
| 20 | 🔴 `play a full game with shuffled standard decks` | `withStandardDecks` does not exist; 50 games, each must end. |
| 21 | 🟢 `deal shuffled standard decks` | The kata's deck, shuffled with the injected `Random`. |
| 22 | 📌 `cover the death by bleeding out, handled without a red test` | Making up for the step 19 slip. |

## Takeaways

- Separating the player's state from the game loop allowed testing each rule with a chosen deck, without randomness.
- The 50 shuffled games test is a **property** test: it does not predict the winner, it checks that every game ends.
- The computer's strategy stays naive; the kata notes that this is where the real difficulty hides.

## Running the tests

```bash
./gradlew :katas:trading-card-game:test
git log --reverse --format='%s%n%b' strict-tdd-start.. -- katas/trading-card-game   # the TDD history
```
