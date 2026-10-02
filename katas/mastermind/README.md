# Mastermind

🇬🇧 [English version](README.en.md) · Énoncé : [codingdojo.org/kata/Mastermind](https://codingdojo.org/kata/Mastermind/)

## Le kata

Jouer le rôle ennuyeux du *codemaker* : pour un code secret et une proposition, répondre le nombre de couleurs **bien placées** et le nombre de couleurs **présentes mais mal placées**.

## Démarche TDD

🔴 rouge · 🟢 vert · 🔵 refactor · 📌 test vert du premier coup

L'énoncé conseille de commencer par les bien placés, et rappelle que les mal placés sont « une affaire de comptage ».

| # | Étape | Ce qui s'est passé |
|---|---|---|
| 1 | 🔴 `answer nothing for a wrong single peg` | Compilation impossible : ni `Color`, ni `Answer`, ni `Mastermind`. |
| 2 | 🟢 `answer nothing` | `Answer(0, 0)`. |
| 3 | 🔴 `count a well placed peg` | `[blue]` contre `[blue]` : attendu `(1, 0)`. |
| 4 | 🟢 `count pegs matching position by position` | `zip` puis comptage des paires égales. |
| 5 | 🔴 `count a misplaced peg` | `[red, yellow]` contre `[blue, red]` : attendu `(0, 1)`. |
| 6 | 🟢 `count guessed colors present elsewhere in the secret` | Version naïve : une couleur proposée, mal placée, et présente dans le secret. |
| 7 | 📌 `answer the kata example` | L'exemple de l'énoncé passe avec la version naïve. |
| 8 | 🔴 `count a repeated guessed color only once` | Secret `[red, blue, green]`, proposition `[green, green, yellow]` : la version naïve compte deux mal placés pour un seul vert. |
| 9 | 🟢 `count misplaced colors by occurrences` | On écarte les paires bien placées, puis pour chaque couleur on prend le minimum de ses occurrences restantes dans le secret et dans la proposition. |
| 10 | 📌 `never count a well placed peg as misplaced too` | `[red, blue]` contre `[red, red]` : `(1, 0)`, déjà garanti par l'écartement des bien placés. |
| 11 | 🔴 `refuse combinations of different sizes` | Une proposition plus courte passe sans erreur. |
| 12 | 🟢 `require combinations of the same size` | Un `require`. |
| 13 | 🔵 `name the unmatched pegs` | `wellPlaced` / `unmatched` et un commentaire sur la règle du minimum. |

## Solution

```kotlin
val (wellPlaced, unmatched) = secret.zip(guess).partition { (s, g) -> s == g }
val secretLeft = unmatched.groupingBy { (s, _) -> s }.eachCount()
val guessLeft = unmatched.groupingBy { (_, g) -> g }.eachCount()
val misplaced = guessLeft.entries.sumOf { (color, count) -> minOf(count, secretLeft[color] ?: 0) }
```

## Ce que j'en retiens

L'exemple de l'énoncé ne contient aucun doublon, et la version naïve le fait passer. C'est le test choisi exprès avec une couleur répétée (étape 8) qui oblige à passer du test d'appartenance au comptage.

## Lancer les tests

```bash
./gradlew :katas:mastermind:test
git log --reverse --format='%s%n%b' strict-tdd-start.. -- katas/mastermind   # l'historique TDD
```
