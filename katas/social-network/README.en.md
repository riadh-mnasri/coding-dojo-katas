# Social Network

🇫🇷 [Version française](README.md) · Kata: [codingdojo.org/kata/social-network](https://codingdojo.org/kata/social-network/)

## The kata

A very light social network, split into *epics*, originally for practising **example mapping**:

- **Posting**: Thomas publishes a message;
- **Reading**: Alice sees all of Thomas's messages;
- **Following**: Charlie subscribes to Thomas and Alice and sees an aggregated wall;
- **Mentions**: Alice mentions Charlie with `@Charlie`;
- **Links**: Thomas shares a link to a message;
- **Direct messages**: Alice writes privately to Thomas.

## TDD walkthrough

🔴 red · 🟢 green · 🔵 refactor · 📌 test green on first run

One test per epic, named after it, using the kata's example: example mapping turned into tests. The clock is injected (it moves forward one minute per message), so the chronological order is deterministic.

| # | Step | What happened |
|---|---|---|
| 1 | 🔴 `let Thomas publish a message` | Does not compile. |
| 2 | 🟢 `publish messages` | A list of `Post`s. |
| 3 | 🔴 `let Alice read Thomas's messages, newest first` | The order is chronological instead of newest first. |
| 4 | 🟢 `show a timeline newest first` | Sort by descending date. |
| 5 | 🔴 `aggregate the followed users on Charlie's wall` | `follow` and `wall` do not exist. |
| 6 | 🟢 `follow users and aggregate them on a wall` | The wall gathers one's own messages and those of followed users. |
| 7 | 🔴 `find the messages mentioning Charlie` | `mentionsOf` does not exist; the test also checks that "Charlie" without `@` and "@Charlotte" do not count. |
| 8 | 🟢 `find mentions written as @name` | A regular expression on whole words. |
| 9 | 🔴 `share a link to a message` | `linkTo` and `open` do not exist. |
| 10 | 🟢 `link to a message and open the link` | A `https://social.example/<author>/messages/<id>` link leading back to the message. **Slip**: I also wrote the rejection of an unknown link, without a red test. |
| 11 | 🔴 `send a private message` | `sendDirectMessage` and `inbox` do not exist. |
| 12 | 🟢 `keep private messages out of timelines` | Direct messages are stored separately and only show up in the recipient's inbox. |
| 13 | 📌 `cover unknown links, rejected without a red test` | Making up for the step 10 slip. |

## Takeaways

With an injected clock, "newest first" becomes testable without `Thread.sleep` or approximate dates. And naming each test after its epic yields documentation the business can read.

## Running the tests

```bash
./gradlew :katas:social-network:test
git log --reverse --format='%s%n%b' strict-tdd-start.. -- katas/social-network   # the TDD history
```
