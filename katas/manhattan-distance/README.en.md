# Manhattan Distance

🇫🇷 [Version française](README.md) · Kata: [codingdojo.org/kata/manhattan-distance](https://codingdojo.org/kata/manhattan-distance/)

## The kata

Write `manhattanDistance(Point, Point)`: the distance when only moving horizontally and vertically, like in Manhattan's streets.

Constraints: `Point` is immutable, with no getters, no setters and no public properties.

## TDD walkthrough

🔴 red · 🟢 green · 🔵 refactor · 📌 test green on first run

| # | Step | What happened |
|---|---|---|
| 1 | 🔴 `measure zero between a point and itself` | Does not compile: no `Point`, no `manhattanDistance`. |
| 2 | 🟢 `return zero for now` | `Point(private val x, private val y)` and `return 0`. |
| 3 | 🔴 `measure a horizontal move` | Expected 3, got 0. |
| 4 | 🟢 `let the point measure the horizontal gap` | **The key moment**: the function cannot read `x`, which is private. The computation therefore moves to `Point.distanceTo(other)` and the kata's function delegates. In Kotlin, `private` is visible to other instances of the same class: `other.x` is reachable inside `Point`. |
| 5 | 🔴 `measure a vertical move` | Expected 2, got 0. |
| 6 | 🟢 `add the vertical gap` | `+ abs(y - other.y)`. |
| 7 | 📌 `check the kata examples and symmetry` | The kata's examples and a symmetric case with negative coordinates pass. |
| 8 | 🔴 `compare points by value` | `Point(2, 3) != Point(2, 3)`: reference equality. |
| 9 | 🟢 `give points value semantics without exposing state` | Hand-written `equals`, `hashCode` and `toString`. A `data class` would be shorter, but it generates `component1()`, `component2()` and `copy()`, which exposes the state. |

## Solution

```kotlin
class Point(private val x: Int, private val y: Int) {
    fun distanceTo(other: Point): Int = abs(x - other.x) + abs(y - other.y)
    // hand-written equals / hashCode / toString
}

fun manhattanDistance(from: Point, to: Point): Int = from.distanceTo(to)
```

## Takeaways

Banning getters leads to *Tell, don't ask*: behaviour moves to where the data lives. The step 3 test forced it on its own, with no upfront design decision.

## Running the tests

```bash
./gradlew :katas:manhattan-distance:test
git log --reverse --format='%s%n%b' strict-tdd-start.. -- katas/manhattan-distance   # the TDD history
```
