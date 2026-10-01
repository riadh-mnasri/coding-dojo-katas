# FooBarQix

🇫🇷 [Version française](README.md) · Kata: [codingdojo.org/kata/FooBarQix](https://codingdojo.org/kata/FooBarQix/)

## The kata

`compute(String): String` applies, in this order:

- divisible by 3, 5, 7 → `Foo`, `Bar`, `Qix`;
- then for each digit 3, 5, 7 of the number, in digit order → `Foo`, `Bar`, `Qix`.

**Step 2**: each 0 becomes `*` (`101 → 1*1`, `105 → FooBarQix*Bar`).

## TDD walkthrough

1. `1`, `2` → unchanged.
2. `6 → Foo`, `10 → Bar`: divisibility rule, first with two `if`s, then a `3→Foo, 5→Bar, 7→Qix` table once 7 shows up.
3. `3 → FooFoo`: the same table is reused for digits. Both parts (divisors, then digits) are concatenated.
4. `53 → BarFoo`, `33 → FooFooFoo`: digits are walked in order, which was already the case.
5. **Step 2**: `101 → 1*1`. Trap: when no word is produced, the number is returned with its `*`s, but when there are words, the `*`s are interleaved with them (`303 → FooFoo*Foo`). The "did we produce a word?" check becomes "does the result contain anything other than `*`?".
6. Keeping the step 1 tests made me keep both behaviours (`step1` and `step2`), since `10` is `Bar` in step 1 and `Bar*` in step 2.

## Solution

- One ordered table (`linkedMapOf`) is shared by divisors and digits.
- The digit `0` produces `*` only in step 2 mode (`traceZeros`).
- The fallback (no word) replaces `0`s with `*` in step 2.

## Takeaways

Step 2 breaks one step 1 example (`10`). Instead of editing a green test, I made both versions of the business rule explicit, which is what usually happens with a real change request.

## Running the tests

```bash
./gradlew :katas:foo-bar-qix:test
```
