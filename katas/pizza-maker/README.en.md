# Pizza Maker

🇫🇷 [Version française](README.md) · Kata: [codingdojo.org/kata/pizza-maker](https://codingdojo.org/kata/pizza-maker/)

## The kata

An interactive program to understand the **asynchronous** style: put pizzas in the oven ("cook a Margherita Pizza"), look at the oven ("show queue"), take a pizza out ("Get out the pizza").

- a pizza cooks in 45 seconds, and the program says so;
- left 15 more seconds, it burns;
- every pizza taken out cooked earns a point.

## TDD walkthrough

🔴 red · 🟢 green · 🔵 refactor · 📌 test green on first run

Each pizza is a **coroutine** that waits (`delay`) then changes state. Tests use the **virtual time** of `kotlinx-coroutines-test`: `advanceTimeBy(45_000)` lets 45 seconds pass instantly, and the suite runs in milliseconds.

| # | Step | What happened |
|---|---|---|
| 1 | 🔴 `announce a pizza cooked after 45 seconds` | Nothing at 44.999 s; the alert at 45 s. Does not compile. |
| 2 | 🟢 `announce a pizza after 45 seconds in the oven` | `scope.launch { delay(45.seconds); alert(...) }`. |
| 3 | 🔴 `earn a point for a pizza taken out in time` | `takeOut` and `points` do not exist. |
| 4 | 🟢 `take pizzas out and score the cooked ones` | The oven keeps its slots; the coroutine changes the pizza's state. |
| 5 | 🔴 `burn a pizza left 15 seconds too long` | No burned state. |
| 6 | 🟢 `burn a pizza 15 seconds after it is cooked` | A second `delay`. |
| 7 | 🔴 `never burn a pizza already taken out` | **The asynchronous trap**: the pizza was taken out at 50 s, but its coroutine is still running and calls it burned at 60 s. |
| 8 | 🟢 `stop the timer of a pizza taken out` | Each pizza keeps its `Job`, cancelled when taken out. |
| 9 | 🔴 `show the queue of the oven` | `queue` does not exist. |
| 10 | 🟢 `show the queue of the oven` | The list of pizzas and their state. |
| 11 | 🔴 `refuse to take a pizza out of an empty oven` | `NoSuchElementException` instead of a clear message. |
| 12 | 🟢 `report an empty oven` | A `check`. |
| 13 | 🔴 `understand the three commands of the kata` | The command layer does not exist. |
| ⛔ | rejected green | My first draft also held a terminal `main` that did not compile, and an "empty oven" message no test asked for. Refused by the guard; redone without those additions. |
| 14 | 🟢 `understand the three commands of the kata` | A regular expression for "cook a … pizza", and two fixed commands. |
| 15 | 🔵 `add an interactive terminal over the commands` | The terminal loop, in its own commit: a logic-free, untested adapter where alerts show up while you type. |

To play: run `main` in `Console.kt` (real time, so wait 45 s).

## Takeaways

- Virtual time makes asynchronous code as easy to test as synchronous code, and much faster than real waits.
- Test 7 shows what is specific to asynchrony: a launched task keeps living until you stop it.

## Running the tests

```bash
./gradlew :katas:pizza-maker:test
git log --reverse --format='%s%n%b' strict-tdd-start.. -- katas/pizza-maker   # the TDD history
```
