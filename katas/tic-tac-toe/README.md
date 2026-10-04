# Tic Tac Toe (morpion)

🇬🇧 [English version](README.en.md) · Énoncé : [codingdojo.org/kata/tic-tac-toe](https://codingdojo.org/kata/tic-tac-toe/)

## Le kata

Deux joueurs, X et O, prennent tour à tour une case libre d'une grille 3 × 3 numérotée de 1 à 9. La partie se termine quand un joueur aligne trois cases (ligne, colonne ou diagonale) ou quand toutes les cases sont prises.

L'énoncé le présente comme une introduction au **TDD en double boucle**.

## Démarche TDD : double boucle

🔴 rouge · 🟢 vert · 🔵 refactor · 📌 test vert du premier coup

- **Boucle externe** : un test d'acceptation décrit une partie complète du point de vue de l'utilisateur. Il reste rouge longtemps.
- **Boucle interne** : des tests unitaires, une règle à la fois, construisent ce qu'il faut pour le faire passer.

Mon garde-fou refuse tout commit vert tant qu'un test échoue. Le test d'acceptation a donc été **garé** (`@Disabled`) dans un commit explicite, puis réactivé à la fin.

| # | Étape | Ce qui s'est passé |
|---|---|---|
| 1 | 🔴 `describe a whole game won by X as the acceptance test` | Boucle externe : X gagne sur la diagonale, plateau final attendu. Compilation impossible. |
| 2 | 🟢 `sketch the game API and park the acceptance test` | L'API (`play`, `board`, `isOver`, `winner`) en `TODO()`. J'ai vérifié, sans commiter, que le test d'acceptation échoue alors avec `NotImplementedError`, puis je l'ai garé. |
| 3 | 🔴 `let X take a field first` | Boucle interne. `ownerOf` n'existe pas. |
| 4 | 🟢 `record the field taken by X` | Une `Map` case → joueur. |
| 5 | 🔴 `let players take turns` | La deuxième case revient aussi à X. |
| 6 | 🟢 `alternate X and O` | Un joueur courant qui alterne. |
| 7 | 🔴 `refuse a field already taken` | Une case prise peut être reprise. |
| 8 | 🟢 `refuse taken fields` | Un `require`. |
| 9 | 🔴 `win with a full row` | `winner()` est encore un `TODO()`. |
| 10 | 🟢 `win with a full row` | Une liste de lignes gagnantes, limitée aux trois rangées. |
| ⛔ | rouge refait | Mon premier test « colonne » contenait un scénario où X alignait en fait `4 5 6` : il attendait à tort « pas de gagnant ». Annulé avant de pousser, puis rejoué avec un vrai scénario de colonne. |
| 11 | 🔴 `win with a full column` | O aligne `1 4 7` : pas de gagnant détecté. |
| 12 | 🟢 `win with a full column` | Trois lignes de plus dans la liste. |
| 13 | 🔴 `win with a full diagonal` | Pas de gagnant détecté. |
| 14 | 🟢 `win with a full diagonal` | Deux lignes de plus. |
| 15 | 🔴 `end the game when all fields are taken` | Match nul : la partie ne se termine pas. |
| 16 | 🟢 `end the game when the board is full` | `isOver` = un gagnant **ou** 9 cases prises. |
| 17 | 🔴 `refuse moves once the game is over` | On peut jouer après la victoire. |
| 18 | 🟢 `refuse moves after the end` | Un `check`. |
| 19 | 🔴 `draw the board after X plays on 5` | Le cas de test de l'énoncé ; `board()` est un `TODO()`. |
| 20 | 🟢 `draw the board` | Rendu ligne par ligne, le numéro pour une case libre. |
| 21 | 📌 `re-enable the acceptance test, now green` | **Fin de la boucle externe** : le test garé à l'étape 2 passe sans code supplémentaire. |

## Solution

```kotlin
fun winner(): Player? = LINES.firstNotNullOfOrNull { line ->
    fields[line.first()]?.takeIf { player -> line.all { fields[it] == player } }
}

fun isOver(): Boolean = winner() != null || fields.size == 9
```

## Ce que j'en retiens

Le test d'acceptation a servi de **cap** : il disait quand s'arrêter. La boucle interne, elle, a fait émerger les règles une à une, dont deux qu'il ne couvrait pas (match nul, coup après la fin).

## Lancer les tests

```bash
./gradlew :katas:tic-tac-toe:test
git log --reverse --format='%s%n%b' strict-tdd-start.. -- katas/tic-tac-toe   # l'historique TDD
```
