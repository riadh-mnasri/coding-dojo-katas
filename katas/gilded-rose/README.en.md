# Gilded Rose

🇫🇷 [Version française](README.md) · Kata: [codingdojo.org/kata/gilded-rose](https://codingdojo.org/kata/gilded-rose/)

## The kata

A **legacy code** kata: we inherit an `updateQuality()` method made of nested `if`s that ages an inn's items every day (brie gets better, concert passes gain value then drop to 0, Sulfuras is legendary...). A new category must be added, **Conjured** items, which degrade twice as fast, without touching the `Item` class (it belongs to the goblin in the corner).

## Approach: characterise, refactor, then TDD

🔴 red · 🟢 green · 🔵 refactor · 📌 test green on first run

The code already exists, without tests: we cannot start with a red test describing behaviour to write. We **first freeze what the code does**, restructure it under that safety net, and only then add the feature test-first.

| # | Step | What happened |
|---|---|---|
| 0 | `chore: import the legacy code to refactor` | The legacy code, faithfully translated into Kotlin from Emily Bache's reference repository. Committed outside the guard (no test yet). |
| 1 | 🔴 `compare 30 days of the reference fixture with an approved output` | **Golden master**: 30 days of the reference fixture's items, compared with an approved output that does not exist yet. The test writes the received output into `build/`. (My first draft renamed "Conjured Mana Cake" to "Mana Cake"; fixed by amending this commit before pushing, to keep the original fixture.) |
| 2 | 📌 `approve the legacy output as the golden master` | The received output, reviewed by hand against the requirements, becomes the reference. It freezes what the code does, **bugs included**. |
| 3 | 📌 `describe each rule of the requirements on the legacy code` | One test per rule (14 cases): a readable specification and a finer net than the golden master. |
| 4 | 🔵 `extract the update of a single item` | The loop delegates to `updateItem(item)`; `items[i]` becomes `item`. |
| 5 | 🔵 `give each kind of item its own aging rule` | The heart of the refactoring: a `when` on the name, one function per category (`ageNormally`, `ageBrie`, `ageBackstagePasses`), and two small `increaseQuality` / `decreaseQuality` functions carrying the 0 and 50 bounds. All 15 tests stay green. |
| 6 | 🔴 `make conjured items degrade twice as fast` | At last a real TDD cycle: the Conjured Mana Cake loses 1 instead of 2. |
| 7 | 🟢 `degrade conjured items twice as fast` | One more category. The golden master breaks, as expected: the diff touches **only** the four Conjured Mana Cake lines, and the output is re-approved in the same commit, with the explanation in its body. |

## Solution

```kotlin
when (item.name) {
    SULFURAS -> Unit
    AGED_BRIE -> ageBrie(item)
    BACKSTAGE_PASSES -> ageBackstagePasses(item)
    else -> if (item.name.startsWith(CONJURED)) ageConjured(item) else ageNormally(item)
}
```

## Takeaways

- On legacy code, the first test is not red: it is a 📌 freezing what exists. A golden master is quick to set up and protects everything, but says nothing about **intent**; per-rule tests make it readable.
- The requested feature, which would have been risky in the original code, became one `when` branch and a four-line function.
- When a golden master changes, the diff must be limited to what you meant to change: that is what gets checked before re-approving.

## Running the tests

```bash
./gradlew :katas:gilded-rose:test
git log --reverse --format='%s%n%b' strict-tdd-start.. -- katas/gilded-rose   # the history
```
