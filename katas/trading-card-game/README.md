# Trading Card Game

🇬🇧 [English version](README.en.md) · Énoncé : [codingdojo.org/kata/TradingCardGame](https://codingdojo.org/kata/TradingCardGame/)

## Le kata

Un jeu de cartes à deux joueurs, inspiré d'Hearthstone :

- chaque joueur commence avec 30 points de vie, 0 emplacement de mana, un paquet de 20 cartes (coûts `0,0,1,1,2,2,2,3,3,3,3,4,4,4,5,5,6,6,7,8`) et 3 cartes en main ;
- à son tour, le joueur gagne un emplacement de mana (10 au maximum), les remplit, pioche, puis joue autant de cartes qu'il peut payer ; une carte inflige autant de dégâts que son coût ;
- **Bleeding Out** : piocher dans un paquet vide coûte 1 point de vie ;
- **Overload** : une carte piochée alors que la main en compte déjà 5 est défaussée ;
- **Dud Card** : les cartes à 0 sont gratuites et n'infligent rien ;
- un joueur dont la vie tombe à 0 ou moins a perdu.

## Démarche TDD

🔴 rouge · 🟢 vert · 🔵 refactor · 📌 test vert du premier coup

D'abord l'état d'un joueur (`Player`), ensuite la boucle de jeu (`Game`). Les tests fixent l'ordre des paquets ; seule la partie complète mélange les paquets avec un `Random` à graine fixe.

| # | Étape | Ce qui s'est passé |
|---|---|---|
| 1 | 🔴 `start with 30 health, no mana and three cards in hand` | Compilation impossible. |
| 2 | 🟢 `set up a player with three starting cards` | Les trois premières cartes du paquet. |
| 3 | 🔴 `start a turn with a new mana slot, refilled, and a new card` | `startTurn` n'existe pas. |
| 4 | 🟢 `start a turn` | Un emplacement de plus, mana pleine, une carte. |
| 5 | 🔴 `cap the mana slots at ten` | 12 emplacements après 12 tours. |
| 6 | 🟢 `cap the mana slots at ten` | `minOf(..., 10)`. |
| 7 | 🔴 `play a card to damage the opponent` | `play` n'existe pas. (Mon paquet de test était trop court pour trois pioches : corrigé en amendant le commit rouge, avant de pousser.) |
| 8 | 🟢 `play a card against the opponent` | Mana dépensée, carte retirée, dégâts infligés. |
| 9 | 🔴 `refuse cards that cannot be afforded or are not in hand` | Rien n'est vérifié. |
| 10 | 🟢 `require cards in hand and affordable` | Un `require` (mauvaise carte) et un `check` (pas assez de mana). |
| 11 | 🔴 `bleed out when drawing from an empty deck` | `NoSuchElementException` sur le paquet vide. |
| 12 | 🟢 `bleed out on an empty deck` | 1 point de vie perdu à la place. |
| 13 | 🔴 `discard a card drawn into a full hand` | La main monte à 6 cartes. |
| 14 | 🟢 `discard cards drawn into a full hand` | La pioche est extraite dans `draw()`, avec les deux règles spéciales. |
| 15 | 📌 `let dud cards cost and deal nothing` | Une carte à 0 ne coûte rien et ne fait rien, sans code particulier. |
| 16 | 🔴 `play a turn and hand over to the opponent` | `Game` n'existe pas. |
| 17 | 🟢 `play the most expensive affordable cards, then switch players` | Stratégie de l'ordinateur : la carte la plus chère abordable, tant qu'il y en a une. |
| 18 | 🔴 `declare the winner when the opponent drops to zero` | `winner` n'existe pas. |
| 19 | 🟢 `stop the game when a player's health drops to zero` | Vérification après chaque carte jouée. **Écart** : j'y ai aussi géré la mort par Bleeding Out en début de tour, sans test rouge. |
| 20 | 🔴 `play a full game with shuffled standard decks` | `withStandardDecks` n'existe pas ; 50 parties, chacune doit se terminer. |
| 21 | 🟢 `deal shuffled standard decks` | Le paquet de l'énoncé, mélangé avec le `Random` injecté. |
| 22 | 📌 `cover the death by bleeding out, handled without a red test` | Rattrapage de l'écart de l'étape 19. |

## Ce que j'en retiens

- Séparer l'état du joueur de la boucle de jeu a permis de tester chaque règle avec un paquet choisi, sans hasard.
- Le test des 50 parties mélangées est un test de **propriété** : il ne prédit pas le gagnant, il vérifie que toute partie se termine.
- La stratégie de l'ordinateur reste naïve ; l'énoncé note que c'est là que se cache la vraie difficulté.

## Lancer les tests

```bash
./gradlew :katas:trading-card-game:test
git log --reverse --format='%s%n%b' strict-tdd-start.. -- katas/trading-card-game   # l'historique TDD
```
