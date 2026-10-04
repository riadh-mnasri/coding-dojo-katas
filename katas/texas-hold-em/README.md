# Texas Hold'em

🇬🇧 [English version](README.en.md) · Énoncé : [codingdojo.org/kata/TexasHoldEm](https://codingdojo.org/kata/TexasHoldEm/)

## Le kata

Chaque ligne donne les cartes d'un joueur : ses deux cartes, puis les cartes de la table qu'il a vues. Un joueur couché a moins de 7 cartes. Il faut répéter chaque ligne avec le nom de la meilleure main (formée de 5 cartes parmi 7) et marquer le ou les gagnants :

```
Kc 9s Ks Kd 9d 3c 6d Full House (winner)
9c Ah Ks Kd 9d 3c 6d Two Pair
Ac Qc Ks Kd 9d 3c
9h 5s
4d 2d Ks Kd 9d 3c 6d Flush
7s Ts Ks Kd 9d
```

Option non traitée : réordonner les cartes (cartes utilisées, puis kickers, puis cartes inutiles). Les lignes sont aussi rendues sans l'espace final de l'exemple.

## Démarche TDD

🔴 rouge · 🟢 vert · 🔵 refactor · 📌 test vert du premier coup

| # | Étape | Ce qui s'est passé |
|---|---|---|
| 1 | 🔴 `repeat a folded hand without rank` | Compilation impossible. |
| 2 | 🟢 `repeat the input` | `return input`. |
| 3 | 🔴 `rank the best five cards of a full hand` | Les trois mains classées de l'exemple : full, deux paires, couleur. |
| 4 | 🟢 `rank the best of every five-card combination` | Les 21 combinaisons de 5 cartes parmi 7, chacune classée (profil des groupes de valeurs + couleur), et on garde la meilleure. Seules les catégories exigées par les tests et celles qui découlent directement du profil existent à ce stade : pas encore de suite. |
| 5 | 🔴 `recognise straights, including the wheel` | Les suites, y compris la « roue » A-2-3-4-5, sont vues comme des cartes hautes. |
| 6 | 🟢 `recognise straights and straight flushes, ace high or low` | Écart de 4 entre valeurs extrêmes, ou roue (l'as vaut alors 1 pour le départage). |
| 7 | 🔴 `announce the kata round with its winner` | La sortie complète de l'énoncé. |
| 8 | 🟢 `rank full hands and mark the winner` | Les joueurs couchés ne sont pas classés ; tous ceux dont la main égale la meilleure sont marqués `(winner)`. |
| 9 | 📌 `mark every winner of a split pot` | Deux quintes flush identiques partagent le pot. |
| 10 | 📌 `let the kicker decide between equal pairs` | Même paire de rois : l'as en kicker l'emporte. |
| 11 | 🔴 `reject unknown cards` | `1h` et `5x` sont acceptés. |
| 12 | 🟢 `reject unknown cards` | Validation de la valeur et de la couleur. |

## Solution

```kotlin
fun best(cards: List<Card>): Hand = combinations(cards, 5).map(::Hand).max()
```

Une `Hand` de 5 cartes se compare d'abord par catégorie, puis par ses valeurs ordonnées « par taille de groupe puis par valeur » : ce seul ordre gère les kickers de toutes les catégories.

## Ce que j'en retiens

Avec 7 cartes, chercher directement « la meilleure main » est compliqué ; énumérer les 21 combinaisons et comparer des mains de 5 cartes ramène le problème à celui de Poker Hands, déjà bien compris.

## Lancer les tests

```bash
./gradlew :katas:texas-hold-em:test
git log --reverse --format='%s%n%b' strict-tdd-start.. -- katas/texas-hold-em   # l'historique TDD
```
