# Trip Service

🇬🇧 [English version](README.en.md) · Énoncé : [codingdojo.org/kata/TripService](https://codingdojo.org/kata/TripService/) · Code d'origine : [sandromancuso/trip-service-kata](https://github.com/sandromancuso/trip-service-kata)

## Le kata

Tester puis refactorer une classe legacy, `TripService`, qui renvoie les voyages d'un utilisateur à condition que l'utilisateur connecté soit son ami. Le piège : elle lit l'utilisateur connecté dans un **singleton** (`UserSession`) et les voyages dans un **DAO statique** (`TripDAO`), qui lèvent tous deux une exception s'ils sont appelés depuis un test unitaire.

Objectif final : un code bien testé, qui exprime le domaine.

## Démarche : tester le legacy, puis refactorer

🔴 rouge · 🟢 vert · 🔵 refactor · 📌 test vert du premier coup

| # | Étape | Ce qui s'est passé |
|---|---|---|
| 0 | `chore: import the legacy code to test and refactor` | Le code d'origine, traduit fidèlement en Kotlin. Commit hors garde-fou. |
| 1 | 🔴 `refuse a request when no user is logged in` | Rouge, mais pour la « mauvaise » raison : `UserSession.getLoggedUser()` explose (`CollaboratorCallException`). C'est la situation que le kata met en scène. |
| 2 | 🟢 `read the logged user through an overridable seam` | Une **couture** : l'appel au singleton est extrait dans une méthode `protected open fun loggedUser()`, qu'une sous-classe de test redéfinit. Extraire une méthode est le seul changement mécanique et sûr qu'on s'autorise sans filet. |
| 3 | 📌 `show no trips to someone who is not a friend` | Pas d'appel au DAO dans ce cas : vert directement. |
| 4 | 🔴 `show the trips of a friend` | Cette fois c'est le DAO statique qui explose. |
| 5 | 🟢 `find trips through an overridable seam` | Seconde couture, `tripsBy(user)`. Les trois cas sont couverts. |
| 6 | 🔵 `build test users with a small builder` | `aUser().friendsWith(...).withTrips(...).build()` : les tests se lisent comme des phrases. |
| 7 | 🔴 `let a user tell whether it is friends with another` | La boucle « est-il dans les amis ? » de `TripService` relève de `User` (*Feature Envy*). Nouveau comportement de `User`, donc un test rouge. |
| 8 | 🟢 `let a user tell whether it is friends with another` | `isFriendsWith`. |
| 9 | 🔵 `ask the user about friendship and use guard clauses` | Le service passe de 20 lignes à deux : une clause de garde, puis une expression. |
| 10 | 🔵 `inject the trip DAO instead of overriding a seam` | Le DAO gagne une méthode d'instance (l'accès statique historique est conservé) et le service le reçoit par son constructeur. Le test utilise un faux DAO au lieu de redéfinir une couture. |
| 11 | 🔵 `receive the logged user instead of reading the session` | L'utilisateur connecté devient un paramètre : le service ne dépend plus du singleton, et la sous-classe de test disparaît. La méthode, renommée `getFriendTrips`, dit enfin ce qu'elle fait. |

## Solution

Avant :

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

Après :

```kotlin
fun getFriendTrips(friend: User, loggedInUser: User?): List<Trip> {
    if (loggedInUser == null) throw UserNotLoggedInException()
    return if (friend.isFriendsWith(loggedInUser)) tripDAO.tripsBy(friend) else emptyList()
}
```

## Ce que j'en retiens

- Les coutures (étapes 2 et 5) sont des **échafaudages** : indispensables pour poser les premiers tests, elles disparaissent une fois les dépendances injectées (étapes 10 et 11).
- On teste d'abord le chemin le plus court (l'invité), puis le plus profond (l'ami) : chaque test force une seule couture.
- Le refactoring du code de test (le builder) compte autant que celui du code de production.

## Lancer les tests

```bash
./gradlew :katas:trip-service:test
git log --reverse --format='%s%n%b' strict-tdd-start.. -- katas/trip-service   # l'historique
```
