# Brainfuck

🇫🇷 [Version française](README.md) · Kata: [codingdojo.org/kata/Brainfuck](https://codingdojo.org/kata/Brainfuck/)

## The kata

Write a Brainfuck interpreter that runs a program and returns the memory state: 30,000 bytes, a pointer, and eight commands (`+ - > < . , [ ]`).

Extra constraints:

- `<` on the first cell goes to the last one;
- a cell holds 0 to 255 (decrementing 0 gives 255);
- instructions must be easy to **rename** ("OooWee" syntax);
- it must be easy to **add** an instruction, for instance `!` jumping to the end of memory.

## TDD walkthrough

🔴 red · 🟢 green · 🔵 refactor · 📌 test green on first run

| # | Step | What happened |
|---|---|---|
| 1 | 🔴 `leave memory blank for an empty program` | Does not compile. |
| 2 | 🟢 `give a blank machine` | A `Machine` with 30,000 zeroed cells. |
| 3 | 🔴 `increment and decrement the current cell` | Nothing changes. |
| 4 | 🟢 `increment and decrement the current cell` | A `when` on the character. |
| 5 | 🔴 `wrap cell values between 0 and 255` | `-` gives -1. |
| 6 | 🟢 `wrap cell values modulo 256` | `Math.floorMod` in `Machine.add`. |
| 7 | 🔴 `move the pointer and wrap it at the left edge` | No pointer. |
| 8 | 🟢 `move the pointer around a circular memory` | `floorMod` for the pointer too: memory is circular. |
| 9 | 🔴 `read input and write output as ASCII` | No input, no output. |
| 10 | 🟢 `read and write bytes as ASCII` | An input queue and an output buffer in the machine. |
| 11 | 🔴 `loop while the current cell is not zero` | Brackets are ignored. |
| 12 | 🟢 `jump between matching brackets` | `forEach` becomes a loop with an instruction counter; bracket pairs are precomputed with a stack. |
| 13 | 📌 `skip a loop on zero, nest loops and print hello world` | A skipped loop, nested loops and the classic "Hello World!". |
| 14 | 🔴 `reject unbalanced brackets` | `NoSuchElementException` instead of a clear error. |
| 15 | 🟢 `report unbalanced brackets` | Explicit messages for an orphan `]` or `[`. |
| 16 | 🔵 `separate the syntax from the instructions` | The heart of the kata: a `Syntax` maps **tokens** (a character or a word) to `Instruction`s. The program is first split into instructions, then run. Loops stay markers handled by the interpreter. |
| 17 | 🔴 `rename every instruction` | `renamed` does not exist. |
| 18 | 🟢 `rename tokens while keeping their instructions` | `Syntax.BRAINFUCK.renamed("+" to "Ooo", ...)`: a plain key rename. Tokenisation tries the longest tokens first (`OooWee` before `Ooo`). |
| 19 | 🔴 `add a jump-to-the-end instruction` | `with` does not exist. |
| 20 | 🟢 `extend a syntax with new instructions` | `Syntax.BRAINFUCK.with("!" to Instruction.Action { ... })`. |

## Solution

```kotlin
val oooWee = Syntax.BRAINFUCK.renamed("+" to "Ooo", "-" to "Wee", ">" to "OooWee", /* ... */)
val withJump = Syntax.BRAINFUCK.with("!" to Instruction.Action { it.pointer = it.memory.size - 1 })
Interpreter(withJump).run("!+>+")
```

- `Machine`: circular byte memory, pointer, input and output.
- `Instruction`: an `Action` on the machine, or a loop marker.
- `Syntax`: tokens → instructions, with `renamed` and `with`.
- `Interpreter`: tokenises, matches loops, runs.

## Takeaways

The last two requirements (rename, extend) are **design** requirements. The step 16 refactoring, done on green, made them trivial: each one only needed a two-line function.

## Running the tests

```bash
./gradlew :katas:brainfuck:test
git log --reverse --format='%s%n%b' strict-tdd-start.. -- katas/brainfuck   # the TDD history
```
