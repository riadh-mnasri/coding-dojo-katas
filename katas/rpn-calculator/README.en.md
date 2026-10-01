# RPN Calculator

🇫🇷 [Version française](README.md) · Kata: [codingdojo.org/kata/RPN](https://codingdojo.org/kata/RPN/)

## The kata

Evaluate a Reverse Polish Notation expression: `3 5 8 * 7 + *` is `((5 × 8) + 7) × 3 = 141`. Then add `SQRT` (`9 SQRT = 3`) and `MAX` (`5 3 4 2 9 1 MAX = 9`, `4 5 MAX 1 2 MAX * = 10`).

## TDD walkthrough

🔴 red · 🟢 green · 🔵 refactor · 📌 test green on first run

| # | Step | What happened |
|---|---|---|
| 1 | 🔴 `evaluate a lone number` | Does not compile: `RpnCalculator` does not exist. |
| 2 | 🟢 `parse a lone number` | `expression.toDouble()`. |
| 3 | 🔴 `add the two previous values` | `NumberFormatException` on `"1 2 +"`. |
| 4 | 🟢 `push numbers on a stack and add them` | The **stack** appears: numbers are pushed, `+` pops two values. |
| 5 | 🔴 `subtract, multiply and divide in operand order` | `-`, `*`, `/` unknown. |
| 6 | 🟢 `support the four arithmetic operators` | A four-branch `when`; mind the operand order (`5 3 -` is 2). |
| 7 | 🔵 `map each symbol to a binary operation` | The four identical branches become a `symbol → (Double, Double) -> Double` table. |
| 8 | 📌 `chain expressions` | `4 2 + 3 -` and `3 5 8 * 7 + *`: the stack does the work. |
| 9 | 🔴 `compute a square root` | `SQRT` unknown. |
| 10 | 🟢 `compute square roots` | A special case in the loop, accepted to get to green fast. |
| 11 | 🔵 `let every operation work on the whole stack` | `SQRT` does not fit the "two operands" model. An `Operation` interface receives the stack, with `binary` and `unary` factories. |
| 12 | 🔴 `take the max of the whole stack` | `MAX` unknown. |
| ⛔ | rejected green attempt | My first version took the max of the **whole** stack. `4 5 MAX 1 2 MAX *` fails: the second `MAX` also swallows the `5` computed earlier, leaving `*` with a single value. I had misread the kata, and `scripts/tdd.sh` refused the commit. |
| 13 | 🟢 `take the max of the operands pushed since the last operation` | An `OperandStack` remembers where the last result ends; `MAX` only takes the operands pushed since. |
| 14 | 🔴 `reject invalid expressions` | Missing operands (`NoSuchElementException`), division by zero accepted, `MAX` without operands. |
| 15 | 🟢 `report missing operands and division by zero` | `require`s with a clear message at each checkpoint. |
| 16 | 🔴 `plug in a new operation` | The constructor takes no operation table. |
| 17 | 🟢 `inject the operation table` | `RpnCalculator(operations = defaultOperations)`: `%` can be added without touching the class. |

## Solution

- `OperandStack`: the stack, plus the position of the last result.
- `Operation`: a functional interface acting on the stack. The `binary` and `unary` factories cover the common cases, `MAX` is written on its own.
- `RpnCalculator`: walks the tokens, applies the known operation or pushes the number.

## Takeaways

- Moving from "an operator takes two numbers" to "an operation receives the stack" was triggered by `SQRT`, and it is what made `MAX` possible without breaking the rest.
- The second `MAX` example pins down a rule the first one left ambiguous. Without it, I would have shipped the wrong semantics.

## Running the tests

```bash
./gradlew :katas:rpn-calculator:test
git log --reverse --format='%s%n%b' strict-tdd-start.. -- katas/rpn-calculator   # the TDD history
```
