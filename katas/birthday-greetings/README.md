# Birthday Greetings

🇬🇧 [English version](README.en.md) · Énoncé : [codingdojo.org/kata/birthday-greetings](https://codingdojo.org/kata/birthday-greetings/)

## Le kata

Envoyer automatiquement un message d'anniversaire à ses amis, listés dans un fichier plat, en pouvant changer facilement **d'où viennent les amis** (fichier, base SQLite...) et **comment part le message** (email, SMS...). Puis : les nés un 29 février fêtés le 28 les autres années, un rappel aux autres amis, et enfin un seul rappel qui liste tous les anniversaires du jour.

Inspiré du travail de Matteo Vaccari sur l'architecture hexagonale.

## Architecture

```
          ┌──────────────── domaine ────────────────┐
fichier → │ FriendRepository → BirthdayService → MessageSender │ → console / SMTP / SMS
          └─────────────────────────────────────────┘
```

- **Domaine** : `Friend`, `Message`, `BirthdayService`. Aucune entrée/sortie.
- **Ports** : `FriendRepository`, `MessageSender`.
- **Adaptateurs** : `FlatFileFriendRepository`, `ConsoleMessageSender` (en test : un carnet en mémoire et une boîte d'envoi).
- **Assemblage** : `Main.kt`, le seul endroit qui choisit les adaptateurs.

## Démarche TDD

🔴 rouge · 🟢 vert · 🔵 refactor · 📌 test vert du premier coup

« Utiliseriez-vous des mocks ? » demande l'énoncé. Ici, des doublures écrites à la main suffisent : un carnet en mémoire et une boîte d'envoi qui garde les messages.

| # | Étape | Ce qui s'est passé |
|---|---|---|
| 1 | 🔴 `send nothing when nobody is known` | Le test pose les ports dès le départ. Compilation impossible. |
| 2 | 🟢 `define the ports and send nothing` | Les types du domaine et les deux ports. |
| 3 | 🔴 `greet a friend on their birthday` | Aucun message. |
| 4 | 🟢 `greet every friend` | On souhaite l'anniversaire de tout le monde. |
| 5 | 🔴 `leave out friends whose birthday is another day` | Mary, née en septembre, est souhaitée en octobre. |
| 6 | 🟢 `greet only friends born on this day` | `Friend.hasBirthdayOn(day)` compare mois et jour. |
| 7 | 🔴 `greet people born on February 29 on February 28` | Personne n'est souhaité le 28 février 2027. |
| 8 | 🟢 `move February 29 birthdays to February 28 in other years` | `MonthDay.atYear()` fait exactement cela. |
| 9 | 🔴 `remind the other friends of a birthday` | Pas de rappel. (Choix d'écriture : l'énoncé écrit « send **him** a message », ce qui suppose le genre de la personne ; j'ai pris « them », qu'il utilise déjà pour plusieurs anniversaires. Test amendé avant de le pousser.) |
| 10 | 🟢 `remind every other friend of each birthday` | Un rappel par anniversaire à chaque autre ami. |
| 11 | 🔴 `send a single reminder listing every birthday` | Mary reçoit trois rappels au lieu d'un seul « John Doe, Lea Leap and Max Power's birthday ». |
| 12 | 🟢 `gather every birthday of the day in one reminder` | Un rappel par lecteur, listant les autres fêtés du jour (sans lui-même). |
| 13 | 🔴 `read friends from the flat file` | Test d'intégration de l'adaptateur, sur un vrai fichier temporaire. |
| 14 | 🟢 `read friends from the flat file` | Lecture ligne à ligne, en-tête ignoré, date au format `yyyy/MM/dd`. |
| 15 | 🔴 `print messages as a swappable sender` | `ConsoleMessageSender` n'existe pas. |
| 16 | 🟢 `print messages to a stream` | L'adaptateur d'envoi, qu'un adaptateur SMTP ou SMS remplacerait. |
| 17 | 🔵 `wire the adapters in a main` | L'assemblage. |

## Ce que j'en retiens

- Tous les tests du domaine tournent sans fichier ni réseau : changer de source ou de canal ne les touche pas.
- Les adaptateurs ont leurs propres tests d'intégration, petits et ciblés.
- Pas besoin de framework de mock : pour des ports d'une méthode, une doublure écrite à la main se lit mieux.

## Lancer les tests

```bash
./gradlew :katas:birthday-greetings:test
git log --reverse --format='%s%n%b' strict-tdd-start.. -- katas/birthday-greetings   # l'historique TDD
```
