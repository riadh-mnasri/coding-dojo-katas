# Hello

🇫🇷 [Version française](README.md) · Kata: [codingdojo.org/kata/Hello](https://codingdojo.org/kata/Hello/)

## The kata

Print "Hello, World!". The kata is mostly a way to check the environment and to introduce test doubles: the display should be stubbed out.

## TDD walkthrough

1. **The only test**: "when I greet, the display receives `Hello, World!`". Writing it forces a decision about *where* the message goes. Rather than capturing `System.out`, a `Display` port is introduced.
2. To make it pass: `Greeter` receives a `Display` and sends it the message.
3. `main` wires the real output (`::println`): the only untested line, and a trivial one.

## Solution

- `Display` is a `fun interface`, so `::println` can be passed directly.
- The test uses a *hand-written* double (`RecordingDisplay`) instead of a mocking framework: for a one-method interface it reads better.

## Takeaways

Even for a one-line program, TDD pushes to split the logic (what to say) from the side effect (where to write it). Hexagonal architecture in miniature.

## Running the tests

```bash
./gradlew :katas:hello:test
```
