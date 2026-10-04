# Trip Service

🇫🇷 [Version française](README.md) · Kata: [codingdojo.org/kata/TripService](https://codingdojo.org/kata/TripService/) · Original code: [sandromancuso/trip-service-kata](https://github.com/sandromancuso/trip-service-kata)

## The kata

Test then refactor a legacy class, `TripService`, which returns a user's trips provided the logged-in user is a friend. The trap: it reads the logged-in user from a **singleton** (`UserSession`) and the trips from a **static DAO** (`TripDAO`), both of which throw when called from a unit test.

Final goal: well-tested code that expresses the domain.

## Approach: test the legacy, then refactor

🔴 red · 🟢 green · 🔵 refactor · 📌 test green on first run

| # | Step | What happened |
|---|---|---|
| 0 | `chore: import the legacy code to test and refactor` | The original code, faithfully translated into Kotlin. Committed outside the guard. |
| 1 | 🔴 `refuse a request when no user is logged in` | Red, but for the "wrong" reason: `UserSession.getLoggedUser()` blows up (`CollaboratorCallException`). That is exactly the situation the kata stages. |
| 2 | 🟢 `read the logged user through an overridable seam` | A **seam**: the singleton call is extracted into a `protected open fun loggedUser()` that a test subclass overrides. Extracting a method is the only safe, mechanical change allowed without a net. |
| 3 | 📌 `show no trips to someone who is not a friend` | No DAO call in this case: green straight away. |
| 4 | 🔴 `show the trips of a friend` | This time the static DAO blows up. |
| 5 | 🟢 `find trips through an overridable seam` | A second seam, `tripsBy(user)`. All three cases are covered. |
| 6 | 🔵 `build test users with a small builder` | `aUser().friendsWith(...).withTrips(...).build()`: tests read like sentences. |
| 7 | 🔴 `let a user tell whether it is friends with another` | `TripService`'s "is he among the friends?" loop belongs to `User` (*Feature Envy*). New behaviour for `User`, hence a red test. |
| 8 | 🟢 `let a user tell whether it is friends with another` | `isFriendsWith`. |
| 9 | 🔵 `ask the user about friendship and use guard clauses` | The service shrinks from 20 lines to two: a guard clause, then an expression. |
| 10 | 🔵 `inject the trip DAO instead of overriding a seam` | The DAO gains an instance method (the historical static access is kept) and the service receives it through its constructor. The test uses a fake DAO instead of overriding a seam. |
| 11 | 🔵 `receive the logged user instead of reading the session` | The logged-in user becomes a parameter: the service no longer depends on the singleton, and the test subclass is gone. The method, renamed `getFriendTrips`, finally says what it does. |

## Solution

Before:

```kotlin
fun getTripsByUser(user: User): List<Trip> {
    var tripList: List<Trip> = ArrayList()
    val loggedUser: User? = UserSession.getInstance().getLoggedUser()
    var isFriend = false
    if (loggedUser != null) {
        for (friend in user.getFriends()) { if (friend == loggedUser) { isFriend = true; break } }
        if (isFriend) { tripList = TripDAO.findTripsByUser(user) }
        return tripList
    } else {
        throw UserNotLoggedInException()
    }
}
```

After:

```kotlin
fun getFriendTrips(friend: User, loggedInUser: User?): List<Trip> {
    if (loggedInUser == null) throw UserNotLoggedInException()
    return if (friend.isFriendsWith(loggedInUser)) tripDAO.tripsBy(friend) else emptyList()
}
```

## Takeaways

- Seams (steps 2 and 5) are **scaffolding**: essential to get the first tests in, they disappear once dependencies are injected (steps 10 and 11).
- Test the shortest path first (the guest), then the deepest (the friend): each test forces a single seam.
- Refactoring the test code (the builder) matters as much as refactoring production code.

## Running the tests

```bash
./gradlew :katas:trip-service:test
git log --reverse --format='%s%n%b' strict-tdd-start.. -- katas/trip-service   # the history
```
