# Depth First Search

🇫🇷 [Version française](README.md) · Kata: [codingdojo.org/kata/DepthFirstSearch](https://codingdojo.org/kata/DepthFirstSearch/)

## The kata

A depth-first search, with two pieces of advice from the kata:

- rely on the **call stack** (recursion) rather than an explicit stack;
- do not build a `Graph` or `Maze` class, but **ask questions** of a human ("where are we?", "what are the exits?"), and mock that conversation in the tests.

Bonus: an "event-driven" version whose every `step()` call asks at most one question.

## TDD walkthrough

🔴 red · 🟢 green · 🔵 refactor · 📌 test green on first run

Tests follow the suggested cases: one-node graph, two-node graph, 2 × 2 maze, full binary tree, 3 × 3 maze. The `ScriptedGuide` answers the questions and **records** them, which lets the tests check the walk order.

| # | Step | What happened |
|---|---|---|
| 1 | 🔴 `stop at once when the start is the goal` | Does not compile: no `Guide`, no `DepthFirstSearch`. |
| 2 | 🟢 `answer the start as the path` | `listOf(start)`. |
| 3 | 🔴 `find no path in a one-node graph without goal` | Expected `null`. |
| 4 | 🟢 `ask whether the start is the goal` | The first question asked of the guide. |
| 5 | 🔴 `follow an exit in the two-node graph` | No path found. |
| 6 | 🟢 `explore exits recursively on the call stack` | Each exit is explored recursively; the path is rebuilt as calls return. |
| 7 | 📌 `backtrack out of a dead end in a 2x2 maze` | Backtracking comes for free: it is the return of the calls. |
| 8 | 🔴 `survive the loops of a 3x3 maze` | Two-way corridors: `StackOverflowError`. |
| 9 | 🟢 `never go back to a visited place` | A set of visited places. |
| 10 | 📌 `ask questions in depth-first order on a binary tree` | The recorded conversation shows the exact order: R, L, a, b, then M, c. |
| 11 | 🔴 `search step by step, one question at a time` | `StepByStepSearch` does not exist. |
| 12 | 🟢 `keep the search state in an explicit stack of frames` | As the kata predicted, the tests barely change but the inside changes completely: each frame keeps its place, its path, whether the goal was asked and the remaining exits. |
| 13 | 🔴 `end the step-by-step search when the goal is unreachable` | `NotFound` does not exist. |
| 14 | 🟢 `report an unreachable goal once the stack is empty` | Empty stack: the search is over. |
| 15 | 🔵 `add a console guide for exploratory testing` | A `ConsoleGuide` asking the questions at the terminal (untested, logic-free): explore by hand, the tests replay. |

## Solution

```kotlin
private fun explore(place: String, visited: MutableSet<String>): List<String>? {
    visited += place
    if (guide.isGoal(place)) return listOf(place)
    return guide.exitsOf(place)
        .filter { it !in visited }
        .firstNotNullOfOrNull { exit -> explore(exit, visited)?.let { listOf(place) + it } }
}
```

## Takeaways

The recursive version fits in five lines because the call stack does the work. The step-by-step version forces that state into the open, and shows everything recursion was hiding.

## Running the tests

```bash
./gradlew :katas:depth-first-search:test
git log --reverse --format='%s%n%b' strict-tdd-start.. -- katas/depth-first-search   # the TDD history
```
