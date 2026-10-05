# Christmas Delivery

🇫🇷 [Version française](README.md) · Kata: [codingdojo.org/kata/christmas-delivery](https://codingdojo.org/kata/christmas-delivery/)

## The kata

Santa needs to go from one elf to several to load his sleigh:

1. **Current system**: a toy machine gives a present to an elf, who takes a while to load it, then becomes available again.
2. **Several elves**: Mrs Claus receives the presents from the machines and hands them to free elves; when none is free, she keeps the presents.
3. **Families**: presents of the same family should leave together when possible, without leaving any elf idle (they are expensive).
4. **Naughty families**: Santa can have their presents discarded.

The sleigh is provided as an interface: `SantasSleigh.pack(present)`.

## TDD walkthrough

🔴 red · 🟢 green · 🔵 refactor · 📌 test green on first run

As in Pizza Maker, each delivery is a **coroutine** and tests use **virtual time**: an elf takes 10 seconds to load a present, and tests move the clock forward without waiting. The test sleigh records the loaded presents.

| # | Step | What happened |
|---|---|---|
| 1 | 🔴 `story 1, an elf packs a present after a while` | At 9.999 s the sleigh is empty, at 10 s it holds the present. Does not compile. |
| 2 | 🟢 `story 1, let an elf pack a present after a while` | `delay(packingTime)` then `sleigh.pack(present)`. |
| 3 | 🔴 `story 2, Mrs Claus hands presents to free elves in parallel` | `MrsClaus` does not exist; two elves must load two presents at the same time. |
| 4 | 🟢 `story 2, dispatch presents to free elves` | A queue of presents and a queue of free elves; an elf who is done becomes free and triggers dispatching again. |
| 5 | 📌 `story 2, hold presents until an elf is free` | A single elf: the second present waits for it to come back. |
| 6 | 🔴 `story 3, finish a family before starting another` | With one elf and the order Smith, Jones, Smith, the Jones present went before the second Smith. |
| 7 | 🟢 `story 3, prefer families already started` | A free elf first takes a present from a family already started, otherwise the oldest one. |
| 8 | 📌 `story 3, keep elves busy rather than idle` | Two elves, two families: they still work in parallel, as the kata asks. |
| 9 | 🔴 `story 4, discard the presents of naughty families` | `cancel` does not exist. |
| 10 | 🟢 `story 4, discard queued and future presents of naughty families` | Waiting presents are discarded, and so are those arriving later. |

## Choices and limits

- Toy machines are just callers of `MrsClaus.receive`: several machines mean several calls.
- Mrs Claus's state (queues, families) is changed from the elves' coroutines. That is safe on a single-threaded dispatcher (the tests', or a confined context); on a thread pool it would need protecting (a `Mutex`, or a `Channel`-based actor).
- A present already handed to an elf is not recalled by a cancellation: it is already on its way to the sleigh.

## Takeaways

The family strategy fits in one function (`nextPresent`) because dispatching was already isolated: adding a priority rule did not touch the concurrency mechanics.

## Running the tests

```bash
./gradlew :katas:christmas-delivery:test
git log --reverse --format='%s%n%b' strict-tdd-start.. -- katas/christmas-delivery   # the TDD history
```
