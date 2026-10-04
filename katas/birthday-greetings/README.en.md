# Birthday Greetings

🇫🇷 [Version française](README.md) · Kata: [codingdojo.org/kata/birthday-greetings](https://codingdojo.org/kata/birthday-greetings/)

## The kata

Automatically send birthday greetings to your friends, listed in a flat file, while being able to easily change **where friends come from** (file, SQLite database...) and **how the message is sent** (email, SMS...). Then: people born on February 29 greeted on the 28th in other years, a reminder to the other friends, and finally a single reminder listing every birthday of the day.

Inspired by Matteo Vaccari's work on hexagonal architecture.

## Architecture

```
          ┌──────────────── domain ────────────────┐
file → │ FriendRepository → BirthdayService → MessageSender │ → console / SMTP / SMS
          └────────────────────────────────────────┘
```

- **Domain**: `Friend`, `Message`, `BirthdayService`. No input/output.
- **Ports**: `FriendRepository`, `MessageSender`.
- **Adapters**: `FlatFileFriendRepository`, `ConsoleMessageSender` (in tests: an in-memory address book and an outbox).
- **Wiring**: `Main.kt`, the only place choosing the adapters.

## TDD walkthrough

🔴 red · 🟢 green · 🔵 refactor · 📌 test green on first run

"Would you use mocks?" asks the kata. Here, hand-written doubles are enough: an in-memory address book and an outbox keeping the messages.

| # | Step | What happened |
|---|---|---|
| 1 | 🔴 `send nothing when nobody is known` | The test sets up the ports from the start. Does not compile. |
| 2 | 🟢 `define the ports and send nothing` | The domain types and the two ports. |
| 3 | 🔴 `greet a friend on their birthday` | No message. |
| 4 | 🟢 `greet every friend` | Everybody is greeted. |
| 5 | 🔴 `leave out friends whose birthday is another day` | Mary, born in September, is greeted in October. |
| 6 | 🟢 `greet only friends born on this day` | `Friend.hasBirthdayOn(day)` compares month and day. |
| 7 | 🔴 `greet people born on February 29 on February 28` | Nobody is greeted on February 28, 2027. |
| 8 | 🟢 `move February 29 birthdays to February 28 in other years` | `MonthDay.atYear()` does exactly that. |
| 9 | 🔴 `remind the other friends of a birthday` | No reminder. (Wording choice: the kata says "send **him** a message", which assumes the person's gender; I used "them", which it already uses for several birthdays. Test amended before pushing.) |
| 10 | 🟢 `remind every other friend of each birthday` | One reminder per birthday to every other friend. |
| 11 | 🔴 `send a single reminder listing every birthday` | Mary gets three reminders instead of a single "John Doe, Lea Leap and Max Power's birthday". |
| 12 | 🟢 `gather every birthday of the day in one reminder` | One reminder per reader, listing the other people celebrated that day (not themselves). |
| 13 | 🔴 `read friends from the flat file` | An adapter integration test, on a real temporary file. |
| 14 | 🟢 `read friends from the flat file` | Line-by-line reading, header skipped, `yyyy/MM/dd` dates. |
| 15 | 🔴 `print messages as a swappable sender` | `ConsoleMessageSender` does not exist. |
| 16 | 🟢 `print messages to a stream` | The sending adapter, which an SMTP or SMS adapter would replace. |
| 17 | 🔵 `wire the adapters in a main` | The wiring. |

## Takeaways

- Every domain test runs without file or network: changing the source or the channel does not touch them.
- Adapters have their own small, focused integration tests.
- No mocking framework needed: for one-method ports, a hand-written double reads better.

## Running the tests

```bash
./gradlew :katas:birthday-greetings:test
git log --reverse --format='%s%n%b' strict-tdd-start.. -- katas/birthday-greetings   # the TDD history
```
