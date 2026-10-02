# NimGame (jeu de Nim)

🇬🇧 [English version](README.en.md) · Énoncé : [codingdojo.org/kata/Nim](https://codingdojo.org/kata/Nim/)

## Le kata

Un jeu de Nim à deux joueurs, 10 allumettes au départ, avec en option le nombre d'allumettes et le nom des joueurs, et une interface (ici un terminal).

L'énoncé ne fixe pas la règle de fin. J'ai retenu la variante la plus connue en France, celle des allumettes (Fort Boyard) : chacun prend **1 à 3** allumettes, et **celui qui prend la dernière perd**.

## Démarche TDD

🔴 rouge · 🟢 vert · 🔵 refactor · 📌 test vert du premier coup

| # | Étape | Ce qui s'est passé |
|---|---|---|
| 1 | 🔴 `start with ten sticks and the first player` | Compilation impossible. |
| 2 | 🟢 `set up ten sticks and the first player` | Deux valeurs en dur. |
| 3 | 🔴 `take sticks and hand over the turn` | `take` n'existe pas. |
| 4 | 🟢 `take sticks and alternate players` | Décompte et alternance. |
| 5 | 🔴 `take one to three sticks only` | 0, 4 et -1 acceptés. |
| 6 | 🟢 `allow one to three sticks per turn` | Un `require`. |
| 7 | 🔴 `never take more sticks than remain` | Prendre 2 quand il en reste 1 est accepté. |
| 8 | 🟢 `cap a move at the remaining sticks` | Un second `require`. |
| 9 | 🔴 `make the player taking the last stick lose` | `winner` n'existe pas. |
| 10 | 🟢 `declare the other player the winner` | Quand il ne reste rien, le joueur dont c'est le tour (celui qui n'a pas pris la dernière) a gagné : aucun état supplémentaire. |
| 11 | 🔴 `refuse moves once the game is over` | Un coup après la fin lève la mauvaise exception (`IllegalArgumentException` au lieu de `IllegalStateException`). |
| 12 | 🟢 `refuse moves once there is a winner` | Un `check` sur l'état, distinct des `require` sur l'argument. |
| 13 | 🔴 `start with a chosen number of sticks` | Le paramètre `sticks` n'existe pas. |
| 14 | 🟢 `choose the starting number of sticks` | Paramètre avec 10 par défaut. **Écart** : j'y ai aussi ajouté un `require(sticks > 0)` qu'aucun test n'exigeait encore. |
| 15 | 🔵 `add a thin console front end over the tested game` | `Console.kt` : une boucle de lecture en terminal, sans logique, non testée. |
| 16 | 📌 `cover the check on the starting sticks added without a red test` | Rattrapage de l'écart de l'étape 14 : le test passe forcément du premier coup, et c'est justement ce qu'il faut retenir. Ce `require` n'a pas été piloté par un test. |

## Solution

```kotlin
val winner: String? get() = if (sticks == 0) currentPlayer else null

fun take(count: Int) {
    check(winner == null) { "The game is over, $winner won" }
    require(count in 1..3) { "A player takes 1 to 3 sticks, not $count" }
    require(count <= sticks) { "Only $sticks sticks left" }
    sticks -= count
    currentPlayer = if (currentPlayer == first) second else first
}
```

Pour jouer : lancer `main` dans `Console.kt` depuis l'IDE.

## Ce que j'en retiens

- Le gagnant se **déduit** de l'état (plus d'allumettes, à qui le tour) au lieu d'être stocké.
- Distinguer `require` (mauvais argument) et `check` (mauvais moment) rend les erreurs plus parlantes, et c'est un test qui a imposé la distinction.
- Un test 📌 sur une règle de validation signale souvent du code écrit en avance sur les tests.

## Lancer les tests

```bash
./gradlew :katas:nim-game:test
git log --reverse --format='%s%n%b' strict-tdd-start.. -- katas/nim-game   # l'historique TDD
```
