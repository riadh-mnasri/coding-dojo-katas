# Mathematical AST

🇫🇷 [Version française](README.md) · Kata: [codingdojo.org/kata/mathematical-ast](https://codingdojo.org/kata/mathematical-ast/)

## The kata

Originally written to practise the **Visitor** pattern:

1. build the syntax tree of an RPN expression: `3 6 -6 * +` gives `+(3, ×(6, -6))`;
2. print the tree back in RPN and in infix notation, and evaluate it (−33);
3. print infix with the **minimum** of parentheses;
4. add the exponent `^` and Knuth's up-arrows `↑`.

## TDD walkthrough

🔴 red · 🟢 green · 🔵 refactor · 📌 test green on first run

| # | Step | What happened |
|---|---|---|
| 1 | 🔴 `parse a lone number` | Does not compile. |
| 2 | 🟢 `parse a lone number` | `Operand(token.toLong())`. |
| 3 | 🔴 `parse an addition` | `Operation` and `Operator` do not exist. |
| 4 | 🟢 `build operations with a stack` | A stack, as in the RPN kata, but pushing **trees** instead of numbers. |
| 5 | 🔴 `parse the nested example of the kata` | `*` (or `×`) unknown. |
| 6 | 🟢 `parse multiplications written with * or ×` | An operator can have spelling aliases. |
| 7 | 🔴 `print the tree back in RPN` | `accept` and `RpnPrinter` do not exist. |
| 8 | 🟢 `visit the tree to print it in RPN` | The **Visitor**: `Expression.accept(visitor)` and a `Visitor<R>` with one method per node type. The tree knows no processing. |
| 9 | 🔴 `evaluate the tree` | `Evaluator` does not exist. |
| 10 | 🟢 `evaluate the tree with a visitor` | A second visitor, without touching the tree. |
| 11 | 🔴 `print the tree in infix notation` | `InfixPrinter` does not exist. |
| 12 | 🟢 `print the tree in infix notation` | Every nested operation in parentheses: `3 + (6 × -6)`. |
| 13 | 🔴 `print infix with the minimum of parentheses` | Step 3: `MinimalInfixPrinter` does not exist. |
| 14 | 🟢 `add parentheses only around lower-precedence operations` | One precedence per operator; a child binding less tightly than its parent gets parentheses. |
| 15 | 🔴 `keep the parentheses subtraction needs on its right` | Subtraction is unknown, and `3 - (6 - 2)` must keep its parentheses. |
| 16 | 🟢 `subtract, parenthesising its right side when needed` | A non-associative operation requires a strictly higher precedence on its right. |
| 17 | 🔴 `handle the right-associative exponent` | `^` unknown; `(2 ^ 3) ^ 2` and `2 ^ 3 ^ 2` differ. |
| 18 | 🟢 `add the right-associative exponent` | The "associative" boolean becomes a `Grouping` (`ANY`, `LEFT`, `RIGHT`): each one says on which side equal precedence requires parentheses. |
| 19 | 🔴 `evaluate Knuth's up-arrows` | `↑` and `↑↑` unknown. |
| 20 | 🟢 `evaluate single and double up-arrows as hyperoperations` | a ↑ b = a^b; a ↑↑ b = a ↑ (a ↑↑ (b − 1)), hence 3 ↑↑ 3 = 3^27 = 7,625,597,484,987. |
| 21 | 🔴 `reject malformed expressions` | A lone `+` throws a `NoSuchElementException`. |
| 22 | 🟢 `report missing operands and leftover values` | Explicit messages. |

## Solution

```kotlin
interface Visitor<R> {
    fun visit(operand: Operand): R
    fun visit(operation: Operation): R
}

val tree = Mathematical.parse("3 6 -6 * +")
tree.accept(RpnPrinter)          // "3 6 -6 × +"
tree.accept(InfixPrinter)        // "3 + (6 × -6)"
tree.accept(MinimalInfixPrinter) // "3 + 6 × -6"
tree.accept(Evaluator)           // -33
```

In Kotlin, a `sealed interface` and a `when` would have been enough. I kept the explicit Visitor, since it is the point of the kata: each new processing (steps 10, 12, 14) was a new class, with no change to the tree.

## Takeaways

Minimal parentheses boil down to two notions: precedence, and the **grouping direction** at equal precedence. Moving from a boolean to three values (step 18) was forced by the exponent, which groups to the right.

## Running the tests

```bash
./gradlew :katas:mathematical-ast:test
git log --reverse --format='%s%n%b' strict-tdd-start.. -- katas/mathematical-ast   # the TDD history
```
