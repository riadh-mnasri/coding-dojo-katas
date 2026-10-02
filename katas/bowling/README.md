# Bowling

🇬🇧 [English version](README.en.md) · Énoncé : [codingdojo.org/kata/Bowling](https://codingdojo.org/kata/Bowling/)

## Le kata

Calculer le score d'une partie de bowling de 10 frames :

- une frame normale vaut le nombre de quilles tombées en deux lancers ;
- un **spare** (10 quilles en deux lancers) ajoute en bonus le lancer suivant ;
- un **strike** (10 quilles au premier lancer) ajoute en bonus les deux lancers suivants ;
- la dixième frame donne droit aux lancers bonus nécessaires.

## Démarche TDD

🔴 rouge · 🟢 vert · 🔵 refactor · 📌 test vert du premier coup

La progression suit celle, devenue classique, d'Uncle Bob.

| # | Étape | Ce qui s'est passé |
|---|---|---|
| 1 | 🔴 `score a gutter game` | Compilation impossible : `BowlingGame` n'existe pas. |
| 2 | 🟢 `score zero` | `roll` ne fait rien, `score` rend 0. |
| 3 | 🔴 `score a game of ones` | Attendu 20, obtenu 0. |
| 4 | 🟢 `sum knocked down pins` | On mémorise les lancers et on les additionne. |
| 5 | 🔴 `add the next roll after a spare` | `5, 5, 3` : attendu 16, obtenu 13. Additionner les lancers ne suffit plus, il faut raisonner **par frame**. |
| 6 | 🟢 `walk the game frame by frame to score spares` | Une boucle sur 10 frames de deux lancers, avec le bonus de spare. Point clé : on garde les lancers bruts et on calcule le score à la fin, plutôt que de calculer au fil des lancers. |
| 7 | 🔴 `add the next two rolls after a strike` | `IndexOutOfBoundsException` : la boucle avance de deux lancers même après un strike. |
| 8 | 🟢 `score strikes as one-roll frames` | Un strike est une frame d'un seul lancer. |
| 9 | 🔵 `name strike, spare and their bonuses` | `isStrike`, `isSpare`, `strikeBonus`, `spareBonus`, `pinsInFrame`, constantes `FRAMES` et `ALL_PINS` : la méthode `score` se lit comme les règles. |
| 10 | 📌 `score a perfect game and a game of spares` | 300 et 150 passent : la dixième frame et ses bonus sont gérés sans cas particulier. |
| 11 | 🔴 `reject a roll outside 0 to 10 pins` | `roll(11)` est accepté. |
| 12 | 🟢 `accept only 0 to 10 pins per roll` | Un `require`. |
| 13 | 🔴 `reject a frame with more than 10 pins` | `6` puis `5` dans la même frame est accepté. |
| 14 | 🟢 `track frames to cap the pins of a frame at 10` | `roll` suit la frame courante et le premier lancer en attente, pour les neuf premières frames. |

## Solution

```kotlin
fun score(): Int {
    var score = 0
    var frameStart = 0
    repeat(FRAMES) {
        when {
            isStrike(frameStart) -> { score += ALL_PINS + strikeBonus(frameStart); frameStart += 1 }
            isSpare(frameStart) -> { score += ALL_PINS + spareBonus(frameStart); frameStart += 2 }
            else -> { score += pinsInFrame(frameStart); frameStart += 2 }
        }
    }
    return score
}
```

Limite assumée : la validation des quilles ne couvre pas les lancers bonus de la dixième frame.

## Ce que j'en retiens

La décision la plus importante arrive au test du spare : stocker les lancers et calculer le score à la demande. Le bonus devient une simple lecture en avant dans la liste, et la dixième frame ne demande aucun code particulier (étape 10).

## Lancer les tests

```bash
./gradlew :katas:bowling:test
git log --reverse --format='%s%n%b' strict-tdd-start.. -- katas/bowling   # l'historique TDD
```
