# Mars Rover

🇬🇧 [English version](README.en.md) · Énoncé : [codingdojo.org/kata/mars-rover](https://codingdojo.org/kata/mars-rover/)

## Le kata

Simuler un rover martien à partir d'une carte en émojis et d'une suite de commandes :

```
🟩🟩🌳🟩🟩
🟩🟩🟩🟩🟩
🟩🟩🟩🌳🟩
🟩🌳🟩🟩🟩
➡️🟩🟩🟩🟩
```

- la flèche indique la position et la direction de départ ;
- `⬆️` avance, `➡️` tourne à droite, `⬅️` tourne à gauche ;
- devant un obstacle (🌳, 🪨), le rover ne fait rien.

Choix faits là où l'énoncé ne dit rien : x va de gauche à droite et y **de bas en haut** (la dernière ligne est y = 0), et le bord de la carte se comporte comme un obstacle.

## Démarche TDD

🔴 rouge · 🟢 vert · 🔵 refactor · 📌 test vert du premier coup

| # | Étape | Ce qui s'est passé |
|---|---|---|
| 1 | 🔴 `find the rover on the map` | Compilation impossible. |
| 2 | 🟢 `read emoji tiles and locate the rover` | Le piège technique : `🟩` occupe deux `char` (paire de substitution), et `➡️` est `➡` suivi d'un sélecteur de variante `U+FE0F`. On découpe donc par **points de code** en ignorant `U+FE0F`. |
| 3 | 🔴 `move forward` | `execute` n'existe pas. |
| 4 | 🟢 `move forward` | Une case dans la direction courante. |
| 5 | 🔴 `turn right and left` | La direction ne change pas. |
| 6 | 🟢 `turn right and left` | `right()` / `left()` sur l'énumération des directions. |
| 7 | 🔴 `stay put in front of an obstacle` | Le rover traverse l'arbre. |
| 8 | 🟢 `stop in front of trees and rocks` | La mission garde les tuiles ; une case 🌳 ou 🪨 bloque l'avancée. |
| 9 | 🔴 `stay on the map at its edges` | Le rover sort en x = -1. |
| 10 | 🟢 `treat the edge of the map as a wall` | Une case hors carte bloque aussi. |
| 11 | 🔵 `give the rover its moves and the map its terrain` | `Rover.forward(terrain)`, `turnLeft`, `turnRight` et une classe `Terrain` ; `execute` devient un `fold` sur les commandes. **Écart** : j'y ai glissé le rejet des commandes inconnues, un nouveau comportement dans un refactor. |
| 12 | 📌 `drive across both maps of the kata` | Les deux cartes de l'énoncé, trajets vérifiés à la main. |
| 13 | 🔵 `fix the comment describing the second map's route` | Mon commentaire décrivait mal le trajet (l'assertion était juste). |
| 14 | 📌 `cover unknown commands, rejected by the previous refactoring` | Rattrapage de l'écart de l'étape 11. |
| 15 | 🔴 `reject a map without a rover` | `NoSuchElementException` peu parlante. |
| 16 | 🟢 `report a map without a rover` | Message explicite. |

## Solution

```kotlin
fun execute(commands: String): Rover = tiles(commands).fold(rover) { current, command ->
    when (command) {
        "⬆" -> current.forward(terrain)
        "➡" -> current.turnRight()
        "⬅" -> current.turnLeft()
        else -> throw IllegalArgumentException("Unknown command $command")
    }
}
```

## Ce que j'en retiens

- Un émoji n'est pas un `char`. Le premier test, sur une carte réelle, l'a imposé tout de suite.
- Un refactor ne doit pas ajouter de comportement ; quand ça m'arrive, le test 📌 qui suit le rend visible plutôt que de le cacher.

## Lancer les tests

```bash
./gradlew :katas:mars-rover:test
git log --reverse --format='%s%n%b' strict-tdd-start.. -- katas/mars-rover   # l'historique TDD
```
