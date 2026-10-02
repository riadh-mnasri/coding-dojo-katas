# Tennis

🇬🇧 [English version](README.en.md) · Énoncé : [codingdojo.org/kata/Tennis](https://codingdojo.org/kata/Tennis/)

## Le kata

Annoncer le score d'un jeu de tennis :

- les points s'appellent `Love`, `Fifteen`, `Thirty`, `Forty` ;
- à égalité on dit `All` (`Fifteen-All`), et à partir de 40-40 c'est `Deuce` ;
- après deuce, un point d'avance donne `Advantage`, deux points d'avance la victoire ;
- on gagne avec au moins 4 points et 2 points d'écart.

## Démarche TDD

🔴 rouge · 🟢 vert · 🔵 refactor · 📌 test vert du premier coup

| # | Étape | Ce qui s'est passé |
|---|---|---|
| 1 | 🔴 `start at love all` | Compilation impossible : `TennisGame` n'existe pas. |
| 2 | 🟢 `announce love all` | `return "Love-All"`. |
| 3 | 🔴 `call each running score` | Attendu `"Fifteen-Love"`, obtenu `"Love-All"`. (J'avais d'abord commité ce test avec une expression parasite et un attendu `Thirty-Thirty` contraire à la convention `All` ; corrigé en amendant le commit rouge avant de le pousser.) |
| 4 | 🟢 `name the points of each player` | Deux compteurs et une liste de noms. |
| 5 | 🔴 `call equal scores all` | Attendu `"Thirty-All"`, obtenu `"Thirty-Thirty"`. |
| 6 | 🟢 `call equal scores all` | Cas d'égalité, qui couvre aussi `Love-All`. |
| 7 | 🔴 `call deuce from forty all` | Attendu `"Deuce"`, obtenu `"Forty-All"`. |
| 8 | 🟢 `call deuce` | Égalité à partir de 3 points. |
| 9 | 🔴 `give advantage after deuce` | Indice 4 hors de la liste des noms. |
| 10 | 🟢 `give advantage to the leader after deuce` | Les deux joueurs à 3 points ou plus, sans égalité : avantage au meneur. |
| 11 | 🔴 `win with four points and a two-point lead` | 4-0 plante, 3-5 annonce encore un avantage. |
| 12 | 🟢 `win with four points and a two-point lead` | La règle de victoire, testée en premier. |
| 13 | 🔵 `express the score rules in tennis vocabulary` | `hasWinner`, `isDeuce`, `bothReachedForty`, `leader` : le `when` se lit comme l'énoncé. |
| 14 | 📌 `go back to deuce when the advantage is lost` | 4-4 redonne `Deuce` sans code supplémentaire : on compte des points, pas des états. |
| 15 | 🔴 `refuse points after the game and from strangers` | Aucune protection. |
| 16 | 🟢 `guard against late points and unknown players` | Un `require` (argument invalide) et un `check` (état invalide). |

## Solution

```kotlin
fun score(): String = when {
    hasWinner() -> "Win for ${leader()}"
    isDeuce() -> "Deuce"
    points1 == points2 -> "${CALLS[points1]}-All"
    bothReachedForty() -> "Advantage ${leader()}"
    else -> "${CALLS[points1]}-${CALLS[points2]}"
}
```

## Ce que j'en retiens

On aurait pu modéliser une machine à états (`Deuce`, `Advantage`...). Garder deux compteurs et **dériver** l'annonce a rendu le retour à deuce gratuit (étape 14). L'ordre des branches du `when` porte toute la priorité des règles.

## Lancer les tests

```bash
./gradlew :katas:tennis:test
git log --reverse --format='%s%n%b' strict-tdd-start.. -- katas/tennis   # l'historique TDD
```
