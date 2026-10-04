# CQRS Booking

🇫🇷 [Version française](README.md) · Kata: [codingdojo.org/kata/CQRS_Booking](https://codingdojo.org/kata/CQRS_Booking/)

## The kata

A booking solution for a hotel, using **CQRS** (*Command Query Responsibility Segregation*):

- a **command** changes state and returns nothing: `bookARoom(Booking)`;
- a **query** returns data and changes nothing: `freeRooms(arrival, departure)`.

The code is split in two: the command service writes into a `WriteRegistry`, which **notifies** the `ReadRegistry` queried by the query service.

## Architecture

```
CommandService ──► WriteRegistry ──(notification)──► ReadRegistry ◄── QueryService
                   write rules                       read model
```

## TDD walkthrough

🔴 red · 🟢 green · 🔵 refactor · 📌 test green on first run

Every test goes through the two public services, as a client would: book through a command, check through a query.

| # | Step | What happened |
|---|---|---|
| 1 | 🔴 `list every room as free before any booking` | The test already wires the four pieces. Does not compile. |
| 2 | 🟢 `split the write and read sides, every room free` | Both sides, linked by a `BookingListener` interface. The query returns every room. |
| 3 | 🔴 `hide a booked room during its stay` | `bookARoom` does not exist. |
| 4 | 🟢 `record bookings and project them on the read side` | The write registry stores the booking and notifies; the read registry files it by room (its "projection"), which is what the query reads. |
| 5 | 📌 `free a room again on the departure day` | The departure day is free for a new arrival, forwards and backwards. |
| 6 | 🔴 `refuse a booking that overlaps another one` | A double booking goes through. |
| 7 | 🟢 `check overlaps on the write side before notifying` | Key point: the rule is checked **on the write side**, against its own data, never by querying the read model. The read side is notified only when the write succeeded. |
| 8 | 🔴 `refuse a stay that ends before it starts` | Same-day arrival and departure accepted. |
| 9 | 🟢 `require a departure after the arrival` | A `Booking` invariant. (This commit also removes a comment on the read side, a clean-up that should have been a separate refactoring.) |
| 10 | 📌 `notify every listener of the write side` | A second subscriber (an audit log) receives the same notification: more read models can be added without touching the write side. |

## Takeaways

- The overlap rule appears twice, in two shapes: on the write side to **refuse**, on the read side to **filter**. In CQRS that duplication is deliberate: each side has its own model, shaped for its use.
- The notification decouples both sides: synchronous here, it could go through a message bus without changing the tests.

## Running the tests

```bash
./gradlew :katas:cqrs-booking:test
git log --reverse --format='%s%n%b' strict-tdd-start.. -- katas/cqrs-booking   # the TDD history
```
