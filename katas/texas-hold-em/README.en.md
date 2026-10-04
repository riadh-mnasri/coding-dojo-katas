# Texas Hold'em

🇫🇷 [Version française](README.md) · Kata: [codingdojo.org/kata/TexasHoldEm](https://codingdojo.org/kata/TexasHoldEm/)

## The kata

Each line gives a player's cards: two hole cards, then the community cards they saw. A folded player has fewer than 7 cards. Repeat each line with the name of the best hand (5 cards out of 7) and mark the winner(s):

```
Kc 9s Ks Kd 9d 3c 6d Full House (winner)
9c Ah Ks Kd 9d 3c 6d Two Pair
Ac Qc Ks Kd 9d 3c
9h 5s
4d 2d Ks Kd 9d 3c 6d Flush
7s Ts Ks Kd 9d
```

Option not done: reordering the cards (used cards, then kickers, then unused cards). Lines are also printed without the example's trailing space.

## TDD walkthrough

🔴 red · 🟢 green · 🔵 refactor · 📌 test green on first run

| # | Step | What happened |
|---|---|---|
| 1 | 🔴 `repeat a folded hand without rank` | Does not compile. |
| 2 | 🟢 `repeat the input` | `return input`. |
| 3 | 🔴 `rank the best five cards of a full hand` | The example's three ranked hands: full house, two pair, flush. |
| 4 | 🟢 `rank the best of every five-card combination` | The 21 five-card combinations out of 7, each ranked (value group shape + flush), keeping the best. Only the categories required by tests and those falling straight out of the shape exist at this point: no straight yet. |
| 5 | 🔴 `recognise straights, including the wheel` | Straights, including the A-2-3-4-5 "wheel", rank as high cards. |
| 6 | 🟢 `recognise straights and straight flushes, ace high or low` | A spread of 4 between extreme values, or the wheel (the ace then counts 1 for tie-breaks). |
| 7 | 🔴 `announce the kata round with its winner` | The kata's full output. |
| 8 | 🟢 `rank full hands and mark the winner` | Folded players are not ranked; everyone whose hand equals the best is marked `(winner)`. |
| 9 | 📌 `mark every winner of a split pot` | Two identical straight flushes split the pot. |
| 10 | 📌 `let the kicker decide between equal pairs` | Same pair of kings: the ace kicker wins. |
| 11 | 🔴 `reject unknown cards` | `1h` and `5x` are accepted. |
| 12 | 🟢 `reject unknown cards` | Value and suit validation. |

## Solution

```kotlin
fun best(cards: List<Card>): Hand = combinations(cards, 5).map(::Hand).max()
```

A 5-card `Hand` compares by category first, then by its values ordered "by group size, then value": that single order handles the kickers of every category.

## Takeaways

With 7 cards, looking directly for "the best hand" is tricky; enumerating the 21 combinations and comparing 5-card hands reduces the problem to Poker Hands, already well understood.

## Running the tests

```bash
./gradlew :katas:texas-hold-em:test
git log --reverse --format='%s%n%b' strict-tdd-start.. -- katas/texas-hold-em   # the TDD history
```
