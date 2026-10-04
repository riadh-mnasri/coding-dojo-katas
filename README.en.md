# Coding Dojo Katas

🇫🇷 [Version française](README.md)

The [codingdojo.org katas](https://codingdojo.org/kata/) solved in **Kotlin** with **strict TDD**, each kata documented (in French and English) with the walkthrough, the solution and the takeaways.

## The rule: verifiable TDD

Every kata is built in small cycles:

1. **Red**: write *one* test, run it and check it fails for the right reason.
2. **Green**: write the minimal code that makes it pass.
3. **Refactor**: clean the code (and the tests) without ever leaving green.

Each step gets **its own commit**, in Angular format:

| Step | Commit type | Example |
|---|---|---|
| Red | `test(<kata>)` | `test(bowling): score a gutter game` |
| Green | `feat(<kata>)` | `feat(bowling): sum knocked down pins` |
| Refactor | `refactor(<kata>)` | `refactor(bowling): extract frame scoring` |

Red commits record the failure reason (compilation error or assertion) in their body. The git history is therefore the evidence of the process, not a reconstruction:

```bash
git log --reverse --format='%s%n%b' strict-tdd-start.. -- katas/bowling
```

### The safety net

The [`scripts/tdd.sh`](scripts/tdd.sh) script runs the kata's tests before every commit and **refuses**:

- a red step when the whole suite passes (a test that cannot fail proves nothing);
- a green or refactor step when a test fails;
- a commit message whose type does not match the step.

A test that passes on its first run is not dressed up as a red step: it is committed with the `pin` phase (`test(<kata>): ...`, with "Passed on first run" in the body). It forces no code and acts as documentation or a safety net.

```bash
scripts/tdd.sh bowling red      "test(bowling): score a gutter game"
scripts/tdd.sh bowling green    "feat(bowling): sum knocked down pins"
scripts/tdd.sh bowling refactor "refactor(bowling): extract frame scoring"
```

### Per-kata documentation

Every `katas/<kata>/` folder holds a `README.md` (French) and a `README.en.md` (English):

- **The kata**: a summary of the problem, linking to codingdojo.org;
- **TDD walkthrough**: the actual sequence of cycles, written from the kata's git history;
- **Solution**: the resulting design and the choices that matter;
- **Takeaways**: what the kata teaches.

## The katas

<!-- katas:start -->
**Progress: 26 / 61 katas.**

| Kata | What it practises | Status | Kata page |
|---|---|---|---|
| [Anagram](katas/anagram/README.en.md) | performance vs readability | ✅ done | [codingdojo.org](https://codingdojo.org/kata/Anagram/) |
| Args | parsing, extensible design | ⏳ to do | [codingdojo.org](https://codingdojo.org/kata/Args/) |
| Bank OCR | parsing, checksum, error correction | ⏳ to do | [codingdojo.org](https://codingdojo.org/kata/BankOCR/) |
| Birthday Greetings | hexagonal architecture, ports and adapters | ⏳ to do | [codingdojo.org](https://codingdojo.org/kata/birthday-greetings/) |
| [Bowling](katas/bowling/README.en.md) | stateful business rules | ✅ done | [codingdojo.org](https://codingdojo.org/kata/Bowling/) |
| [Brainfuck](katas/brainfuck/README.en.md) | interpreter, extensible instructions | ✅ done | [codingdojo.org](https://codingdojo.org/kata/Brainfuck/) |
| Christmas Delivery | concurrency, queues | ⏳ to do | [codingdojo.org](https://codingdojo.org/kata/christmas-delivery/) |
| [Code Cracker](katas/code-cracker/README.en.md) | substitution cipher, round trip | ✅ done | [codingdojo.org](https://codingdojo.org/kata/CodeCracker/) |
| CQRS Booking | CQRS, read/write split | ⏳ to do | [codingdojo.org](https://codingdojo.org/kata/CQRS_Booking/) |
| Cupcake | Decorator and Composite patterns | ⏳ to do | [codingdojo.org](https://codingdojo.org/kata/cupcake/) |
| Depth First Search | recursion, mocked conversation | ⏳ to do | [codingdojo.org](https://codingdojo.org/kata/DepthFirstSearch/) |
| [Diamond](katas/diamond/README.en.md) | property-style tests | ✅ done | [codingdojo.org](https://codingdojo.org/kata/Diamond/) |
| [Dictionary Replacer](katas/dictionary-replacer/README.en.md) | string replacement | ✅ done | [codingdojo.org](https://codingdojo.org/kata/DictionaryReplacer/) |
| Eight Queens | backtracking, tree traversal | ⏳ to do | [codingdojo.org](https://codingdojo.org/kata/eight-queens/) |
| Elephant Carpaccio | thin vertical slicing | ⏳ to do | [codingdojo.org](https://codingdojo.org/kata/elephant-carpaccio/) |
| Employee Report | focused assertions, test maintainability | ⏳ to do | [codingdojo.org](https://codingdojo.org/kata/Employee-Report/) |
| [FizzBuzz](katas/fizz-buzz/README.en.md) | baby steps, composable rules | ✅ done | [codingdojo.org](https://codingdojo.org/kata/FizzBuzz/) |
| [FooBarQix](katas/foo-bar-qix/README.en.md) | changing requirements | ✅ done | [codingdojo.org](https://codingdojo.org/kata/FooBarQix/) |
| [Game of Life](katas/game-of-life/README.en.md) | cellular automaton, immutability | ✅ done | [codingdojo.org](https://codingdojo.org/kata/GameOfLife/) |
| Gilded Rose | legacy code, characterization tests | ⏳ to do | [codingdojo.org](https://codingdojo.org/kata/gilded-rose/) |
| [Greed](katas/greed/README.en.md) | scoring rules | ✅ done | [codingdojo.org](https://codingdojo.org/kata/Greed/) |
| [Hello](katas/hello/README.en.md) | test doubles | ✅ done | [codingdojo.org](https://codingdojo.org/kata/Hello/) |
| JEE Web Authentication | mocks vs stubs, servlet filters | ⏳ to do | [codingdojo.org](https://codingdojo.org/kata/JEEWebAuthentication/) |
| Lags | dynamic programming | ⏳ to do | [codingdojo.org](https://codingdojo.org/kata/Lags/) |
| Langton Ant | cellular automaton, extensible rules | ⏳ to do | [codingdojo.org](https://codingdojo.org/kata/LangtonAnt/) |
| [Leap Years](katas/leap-years/README.en.md) | rules and exceptions | ✅ done | [codingdojo.org](https://codingdojo.org/kata/LeapYears/) |
| [Manhattan Distance](katas/manhattan-distance/README.en.md) | getter-free objects (Tell, don't ask) | ✅ done | [codingdojo.org](https://codingdojo.org/kata/manhattan-distance/) |
| Markov Chain | statistics, injected randomness | ⏳ to do | [codingdojo.org](https://codingdojo.org/kata/MarkovChain/) |
| Mars Rover | commands, obstacles, map parsing | ⏳ to do | [codingdojo.org](https://codingdojo.org/kata/mars-rover/) |
| [Mastermind](katas/mastermind/README.en.md) | counting, choosing the next test | ✅ done | [codingdojo.org](https://codingdojo.org/kata/Mastermind/) |
| Mathematical AST | syntax tree, Visitor | ⏳ to do | [codingdojo.org](https://codingdojo.org/kata/mathematical-ast/) |
| [Minesweeper](katas/minesweeper/README.en.md) | grids, neighbourhood | ✅ done | [codingdojo.org](https://codingdojo.org/kata/Minesweeper/) |
| Movie Rental | refactoring (Fowler) | ⏳ to do | [codingdojo.org](https://codingdojo.org/kata/movie-rental/) |
| Nearest Color | distance, ties | ⏳ to do | [codingdojo.org](https://codingdojo.org/kata/NearestColor/) |
| [Nim Game](katas/nim-game/README.en.md) | game rules, turns | ✅ done | [codingdojo.org](https://codingdojo.org/kata/Nim/) |
| [Number to LCD](katas/number-to-lcd/README.en.md) | text rendering, changing requirements | ✅ done | [codingdojo.org](https://codingdojo.org/kata/NumberToLCD/) |
| [Numbers in Words](katas/numbers-in-words/README.en.md) | two-way conversion | ✅ done | [codingdojo.org](https://codingdojo.org/kata/NumbersInWords/) |
| ORM | ORM, schema migrations | ⏳ to do | [codingdojo.org](https://codingdojo.org/kata/orm/) |
| PacMan | tick-based game, board state | ⏳ to do | [codingdojo.org](https://codingdojo.org/kata/PacMan/) |
| Pagination Seven | display edge cases | ⏳ to do | [codingdojo.org](https://codingdojo.org/kata/PaginationSeven/) |
| Pizza Maker | async, virtual time | ⏳ to do | [codingdojo.org](https://codingdojo.org/kata/pizza-maker/) |
| Poker Hands | ranking and comparison | ⏳ to do | [codingdojo.org](https://codingdojo.org/kata/PokerHands/) |
| Potter | discount optimisation | ⏳ to do | [codingdojo.org](https://codingdojo.org/kata/Potter/) |
| Quote of the Day | minimal web service | ⏳ to do | [codingdojo.org](https://codingdojo.org/kata/QotdCgi/) |
| [Range](katas/range/README.en.md) | value object, bounds | ✅ done | [codingdojo.org](https://codingdojo.org/kata/Range/) |
| Reversi | legal moves, directions | ⏳ to do | [codingdojo.org](https://codingdojo.org/kata/Reversi/) |
| [Roman Calculator](katas/roman-calculator/README.en.md) | finding the next test | ✅ done | [codingdojo.org](https://codingdojo.org/kata/RomanCalculator/) |
| [Roman Numerals](katas/roman-numerals/README.en.md) | greedy algorithm | ✅ done | [codingdojo.org](https://codingdojo.org/kata/RomanNumerals/) |
| [RPN Calculator](katas/rpn-calculator/README.en.md) | stack, extensible operations | ✅ done | [codingdojo.org](https://codingdojo.org/kata/RPN/) |
| RSA | modular arithmetic | ⏳ to do | [codingdojo.org](https://codingdojo.org/kata/rsa/) |
| Social Network | example mapping, domain | ⏳ to do | [codingdojo.org](https://codingdojo.org/kata/social-network/) |
| [String Calculator](katas/string-calculator/README.en.md) | incremental error handling | ✅ done | [codingdojo.org](https://codingdojo.org/kata/StringCalculator/) |
| Sudoku Concurrent Resolver | message-driven constraint propagation | ⏳ to do | [codingdojo.org](https://codingdojo.org/kata/sudoku/) |
| [Tennis](katas/tennis/README.en.md) | state machine | ✅ done | [codingdojo.org](https://codingdojo.org/kata/Tennis/) |
| Texas Hold'em | best hand out of 7 cards | ⏳ to do | [codingdojo.org](https://codingdojo.org/kata/TexasHoldEm/) |
| Tic Tac Toe | double-loop TDD | ⏳ to do | [codingdojo.org](https://codingdojo.org/kata/tic-tac-toe/) |
| Trading Card Game | game loop, test doubles | ⏳ to do | [codingdojo.org](https://codingdojo.org/kata/TradingCardGame/) |
| Trip Service | breaking legacy dependencies | ⏳ to do | [codingdojo.org](https://codingdojo.org/kata/TripService/) |
| Wallet | external port (exchange rates) | ⏳ to do | [codingdojo.org](https://codingdojo.org/kata/Wallet/) |
| [Word Wrap](katas/word-wrap/README.en.md) | recursion, edge cases | ✅ done | [codingdojo.org](https://codingdojo.org/kata/WordWrap/) |
| [Yahtzee](katas/yahtzee/README.en.md) | scoring categories | ✅ done | [codingdojo.org](https://codingdojo.org/kata/Yahtzee/) |
<!-- katas:end -->

## Stack

- Kotlin 2.0 (JVM 17), multi-module Gradle 8 build: **one Gradle module per kata**, no dependency between katas.
- JUnit 5 + AssertJ everywhere. A few katas add what their topic needs (coroutines for concurrency, MockK for test doubles, Exposed + SQLite for the ORM kata).
- Tests are named after the expected behaviour and laid out as *Given / When / Then* when they need some setup.

## Running the tests

```bash
./gradlew test                          # every kata
./gradlew :katas:bowling:test           # a single kata
```

Requirement: JDK 17 or newer. The Gradle wrapper fetches the rest.

## License

[MIT](LICENSE). © 2026 Riadh MNASRI. The kata statements belong to their respective authors, credited on [codingdojo.org](https://codingdojo.org/kata/).
