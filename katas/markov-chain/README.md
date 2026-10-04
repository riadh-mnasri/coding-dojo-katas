# Markov Chain

🇬🇧 [English version](README.en.md) · Énoncé : [codingdojo.org/kata/MarkovChain](https://codingdojo.org/kata/MarkovChain/)

## Le kata

Un générateur de texte en deux parties :

1. **analyser** un texte : pour chaque mot, la liste des mots qui le suivent avec leur pourcentage (« libres » est suivi par « peuvent » à 50 % et « ou » à 50 %) ;
2. **générer** un texte d'un nombre de mots donné à partir de ces statistiques, éventuellement à partir d'un premier mot imposé.

## Démarche TDD

🔴 rouge · 🟢 vert · 🔵 refactor · 📌 test vert du premier coup

L'aléatoire est injecté (`kotlin.random.Random`) : en test, un `ScriptedRandom` renvoie des tirages prévus à l'avance.

| # | Étape | Ce qui s'est passé |
|---|---|---|
| 1 | 🔴 `learn nothing from a single word` | Compilation impossible. |
| 2 | 🟢 `know no follower` | `emptyMap()`. |
| 3 | 🔴 `give each follower with its percentage` | Le texte de l'énoncé : `les → hommes 100 %`, `libres → peuvent 50 %, ou 50 %`. |
| 4 | 🟢 `count word pairs into follower frequencies` | `zipWithNext` donne les paires de mots consécutifs ; on les regroupe par premier mot puis on compte. |
| 5 | 🔴 `generate the only possible text of a linear chain` | `generate` n'existe pas. |
| 6 | 🟢 `walk the chain from the first word` | On prend toujours le premier suivant : suffisant pour une chaîne sans choix. |
| 7 | 🔴 `draw the next word according to the frequencies` | Le paramètre `random` n'existe pas. |
| 8 | 🟢 `draw followers by cumulative frequency` | Chaque suivant occupe sur [0 ; 1[ une part égale à sa fréquence ; un tirage choisit la part. |
| 9 | 🔴 `restart from a random word at a dead end` | « dort » n'a pas de suivant : exception. (Mon test ne prévoyait que 3 tirages pour un scénario qui en consomme 4 : corrigé en amendant le commit rouge, avant de le pousser.) |
| 10 | 🟢 `restart from a random known word at a dead end` | Choix de conception : sur une impasse, on repart d'un mot connu tiré au hasard. |
| 11 | 🔴 `refuse to generate from a text without any pair` | Un texte d'un seul mot provoque un `IndexOutOfBoundsException`. |
| 12 | 🟢 `report a chain that learned nothing` | Un `check` avec un message clair. |

## Solution

```kotlin
val transitions = words.zipWithNext()
    .groupBy({ it.first }, { it.second })
    .mapValues { (_, followers) -> followers.groupingBy { it }.eachCount().mapValues { (_, n) -> n.toDouble() / followers.size } }
```

## Ce que j'en retiens

Injecter l'aléatoire rend la génération testable de façon déterministe, et même lisible : le test dit « avec un tirage de 0,75, on obtient *ou* ». Sans cela, on en serait réduit à vérifier des propriétés vagues sur des textes imprévisibles.

## Lancer les tests

```bash
./gradlew :katas:markov-chain:test
git log --reverse --format='%s%n%b' strict-tdd-start.. -- katas/markov-chain   # l'historique TDD
```
