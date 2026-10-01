# Hello

🇫🇷 [Version française](README.md) · Kata: [codingdojo.org/kata/Hello](https://codingdojo.org/kata/Hello/)

## The kata

Print "Hello, World!". The kata checks that the environment works and introduces test doubles: the display should be stubbed out.

## TDD walkthrough

| # | Step | What happened |
|---|---|---|
| 1 | 🔴 `expect the greeting to reach the display` | The test decides *where* the message goes: a `Display` port handed to `Greeter`. Compilation fails: `Greeter` does not exist. |
| 2 | 🟢 `send the greeting to a display port` | `Display` as a `fun interface` (the test passes a lambda), `Greeter.greet()` sends it the text. |
| 3 | 🔵 `wire the console as the real display` | `main` plugs `::println` in as the real display. The only untested line, and it holds no logic. |

## Solution

```kotlin
fun interface Display {
    fun show(message: String)
}

class Greeter(private val display: Display) {
    fun greet() = display.show("Hello, World!")
}
```

The test uses no mocking framework: for a one-method interface, a lambda recording the messages is enough and reads better.

## Takeaways

Even for a one-line program, writing the test first forces a split between the logic (what to say) and the side effect (where to write it). Hexagonal architecture in miniature.

## Running the tests

```bash
./gradlew :katas:hello:test
git log --reverse --format='%s%n%b' -- katas/hello   # the TDD history
```
