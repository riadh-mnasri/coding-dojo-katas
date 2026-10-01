# Code Cracker

🇫🇷 [Version française](README.md) · Kata: [codingdojo.org/kata/CodeCracker](https://codingdojo.org/kata/CodeCracker/)

## The kata

Given a decryption key (an alphabet → symbols mapping), write a program that decrypts any message, then one that encrypts.

```
alphabet  a b c d e f g h i j k l m n o p q r s t u v w x y z
key       ! ) " ( £ * % & > < @ a b c d e f g h i j k l m n o
```

## TDD walkthrough

🔴 red · 🟢 green · 🔵 refactor · 📌 test green on first run

| # | Step | What happened |
|---|---|---|
| 1 | 🔴 `decrypt a single symbol` | Does not compile: `CodeCracker` does not exist. |
| 2 | 🟢 `fake the decryption of one symbol` | `return "a"`. |
| 3 | 🔴 `decrypt a word` | `&£aad`: expected `"hello"`, got `"a"`. |
| 4 | 🟢 `decrypt with the alphabet and key table` | `key.zip(alphabet).toMap()` and `getValue` on each character. |
| 5 | 🔴 `keep characters outside the key` | `getValue` throws on the comma. |
| 6 | 🟢 `let unknown characters through` | `table[c] ?: c`. Trap: `!` is part of the key (it means `a`), so it cannot be used as punctuation in an encrypted message. |
| 7 | 🔴 `encrypt a message` | `encrypt` does not exist. |
| 8 | 🟢 `encrypt with the reversed table` | A second table `alphabet.zip(key)`. |
| 9 | 📌 `round-trip a pangram` | `decrypt(encrypt(m)) == m` on a pangram. |
| 10 | 🔴 `reject keys with duplicate symbols` | `CodeCracker("ab", "xx")` is accepted, although `x` could not be decrypted. |
| 11 | 🟢 `require a key of unique symbols` | Two `require`s at construction time. |
| 12 | 🔵 `derive decryption from the encryption table` | One source table, its inverse computed; a `substitute` extension shared by both directions. |

## Solution

Two `Map<Char, Char>` built once (encryption and its inverse). The class accepts any alphabet/key pair; the kata's key is a ready-made instance (`CodeCracker.kata`).

## Takeaways

- The round-trip test is worth ten examples, and unique symbols are the invariant that keeps it true.
- In a first version written without TDD (since removed), I had computed an expected value wrong by hand (`d` encrypts to `(`, not `c`). Seeing each test fail for the right reason before writing code catches that kind of mistake straight away.

## Running the tests

```bash
./gradlew :katas:code-cracker:test
git log --reverse --format='%s%n%b' strict-tdd-start.. -- katas/code-cracker   # the TDD history
```
