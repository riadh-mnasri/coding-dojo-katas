# ORM

🇫🇷 [Version française](README.md) · Kata: [codingdojo.org/kata/orm](https://codingdojo.org/kata/orm/)

## The kata

Learn to use an ORM on a small contact book (name, surname, birth date):

- save and read back persons, with a **test** database (`tests.db`, fresh for each test) separate from the **production** database (whose URL is given by the `DB_URL` environment variable);
- fill the production database with the kata's contacts;
- evolve the schema (adding an email) through versioned **migrations**, which can be applied and undone.

ORM chosen: [Exposed](https://github.com/JetBrains/Exposed) (JetBrains), on SQLite, so no server needs installing to run the tests.

## TDD walkthrough

🔴 red · 🟢 green · 🔵 refactor · 📌 test green on first run

Tests run on real SQLite files, in a JUnit temporary folder (`@TempDir`): each test starts from an empty database.

| # | Step | What happened |
|---|---|---|
| 1 | 🔴 `save a person and read it back` | Does not compile. |
| 2 | 🟢 `save and find persons with Exposed on SQLite` | The `Person` business object, the `Persons` table and the `Contacts` book translating between them; the table is created on start. |
| 3 | 🔴 `read the production database URL from DB_URL` | `forProduction` does not exist. The environment is passed as a parameter (`System.getenv()` by default) to be testable. |
| 4 | 🟢 `connect to the production database given by DB_URL` | Without `DB_URL`, an explicit error. |
| 5 | 🔴 `seed the production contacts` | `ProductionContacts` and `all` do not exist. |
| 6 | 🟢 `list contacts and seed the production ones` | |
| 7 | 🔴 `record the schema version in the database` | `Migrator` does not exist. |
| 8 | 🟢 `apply numbered migrations and record the schema version` | A `schema_version` table; migration 1 creates the table. |
| 9 | 🔵 `create the schema through the migrations` | `Contacts` no longer creates the table itself: it migrates the database to the latest version. |
| 10 | 🔴 `store an email with migration 2` | `Person` has no email. |
| 11 | 🟢 `add the email column with migration 2` | For migration 2 to add the column, migration 1 could no longer create the table from the current Exposed definition (which already has the email): each migration is now written as frozen SQL. |
| 12 | 🔴 `migrate back down and keep the data` | A first red came from an import missing in the test: fixed by amending the commit (before pushing). The real red: the `email` column remains after going back to version 1. |
| 13 | 🟢 `undo migrations down to a target version` | The `down`s run in reverse order. |
| 14 | 📌 `upgrade a populated version 1 database` | The production case: a v1 database with data moves to v2 without losing anything. |

## Solution

- `Person` is a pure business object; `Persons` describes the table; `Contacts` links them. The rest of the code never sees a database row.
- `Migrator` reads the recorded version, then runs the needed `up`s (to go up) or `down`s (to go down), in a single transaction.
- **A migration is a historical fact**: its SQL is frozen. Generating migration 1 from today's table would have made migration 2 fail on a fresh database (duplicate column), and step 11 revealed exactly that trap.

## Takeaways

- Test against a real database (here a throwaway SQLite file) rather than a fake: the database's details (columns, `DROP COLUMN`) are what is at stake.
- Injecting the environment rather than reading `System.getenv()` directly makes the production configuration testable without touching the process variables.
- In a real project, a dedicated tool (Flyway, Liquibase) would do `Migrator`'s job; writing it once shows what it does.

## Running the tests

```bash
./gradlew :katas:orm:test
git log --reverse --format='%s%n%b' strict-tdd-start.. -- katas/orm   # the TDD history
```
