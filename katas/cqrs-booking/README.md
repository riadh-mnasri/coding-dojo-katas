# CQRS Booking

🇬🇧 [English version](README.en.md) · Énoncé : [codingdojo.org/kata/CQRS_Booking](https://codingdojo.org/kata/CQRS_Booking/)

## Le kata

Une solution de réservation pour un hôtel, en architecture **CQRS** (*Command Query Responsibility Segregation*) :

- une **commande** change l'état et ne renvoie rien : `bookARoom(Booking)` ;
- une **requête** renvoie des données et ne change rien : `freeRooms(arrival, departure)`.

Le code est séparé en deux : le service de commande écrit dans un `WriteRegistry`, qui **notifie** le `ReadRegistry` interrogé par le service de requête.

## Architecture

```
CommandService ──► WriteRegistry ──(notification)──► ReadRegistry ◄── QueryService
                   règles d'écriture                 modèle de lecture
```

## Démarche TDD

🔴 rouge · 🟢 vert · 🔵 refactor · 📌 test vert du premier coup

Tous les tests passent par les deux services publics, comme le ferait un client : on réserve par une commande, on vérifie par une requête.

| # | Étape | Ce qui s'est passé |
|---|---|---|
| 1 | 🔴 `list every room as free before any booking` | Le test assemble déjà les quatre pièces. Compilation impossible. |
| 2 | 🟢 `split the write and read sides, every room free` | Les deux côtés, reliés par une interface `BookingListener`. La requête renvoie toutes les chambres. |
| 3 | 🔴 `hide a booked room during its stay` | `bookARoom` n'existe pas. |
| 4 | 🟢 `record bookings and project them on the read side` | Le registre d'écriture garde la réservation et notifie ; le registre de lecture la range par chambre (sa « projection »), et c'est elle qu'interroge la requête. |
| 5 | 📌 `free a room again on the departure day` | Le jour du départ est libre pour une nouvelle arrivée, en avant comme en arrière. |
| 6 | 🔴 `refuse a booking that overlaps another one` | Une double réservation passe. |
| 7 | 🟢 `check overlaps on the write side before notifying` | Point important : la règle est vérifiée **côté écriture**, sur ses propres données, jamais en interrogeant le modèle de lecture. La lecture n'est notifiée que si l'écriture a réussi. |
| 8 | 🔴 `refuse a stay that ends before it starts` | Arrivée et départ le même jour acceptés. |
| 9 | 🟢 `require a departure after the arrival` | Invariant de `Booking`. (Ce commit retire aussi un commentaire du côté lecture, un nettoyage qui aurait dû faire l'objet d'un refactor séparé.) |
| 10 | 📌 `notify every listener of the write side` | Un second abonné (un journal d'audit) reçoit la même notification : on peut ajouter d'autres modèles de lecture sans toucher à l'écriture. |

## Ce que j'en retiens

- La règle de chevauchement apparaît deux fois, sous deux formes : côté écriture pour **refuser**, côté lecture pour **filtrer**. En CQRS, cette duplication est assumée : chaque côté a son modèle, taillé pour son usage.
- La notification découple les deux côtés : ici elle est synchrone, mais elle pourrait passer par un bus de messages sans changer les tests.

## Lancer les tests

```bash
./gradlew :katas:cqrs-booking:test
git log --reverse --format='%s%n%b' strict-tdd-start.. -- katas/cqrs-booking   # l'historique TDD
```
