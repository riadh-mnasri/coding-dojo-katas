# Poker Hands

🇬🇧 [English version](README.en.md) · Énoncé : [codingdojo.org/kata/PokerHands](https://codingdojo.org/kata/PokerHands/)

## Le kata

Comparer deux mains de poker de 5 cartes et annoncer le gagnant, avec la raison :

```
Black: 2H 3D 5S 9C KD  White: 2C 3H 4S 8C AH    →  White wins. - with high card: Ace
Black: 2H 4S 4C 2D 4H  White: 2S 8S AS QS 3S    →  Black wins. - with full house: 4 over 2
Black: 2H 3D 5S 9C KD  White: 2C 3H 4S 8C KH    →  Black wins. - with high card: 9
Black: 2H 3D 5S 9C KD  White: 2D 3H 5C 9S KH    →  Tie.
```

Catégories, de la plus faible à la plus forte : carte haute, paire, deux paires, brelan, suite, couleur, full, carré, quinte flush.

## Démarche TDD

🔴 rouge · 🟢 vert · 🔵 refactor · 📌 test vert du premier coup

D'abord la comparaison des mains (`Hand`, `Comparable`), ensuite l'annonce (`Game`).

| # | Étape | Ce qui s'est passé |
|---|---|---|
| 1 | 🔴 `rank high cards by their highest card` | Compilation impossible. |
| 2 | 🟢 `compare hands by their highest card` | Comparaison des maximums. |
| 3 | 🔴 `break high card ties with the next cards` | Même roi : la main au 9 doit battre celle au 8. |
| 4 | 🟢 `compare values from the highest down` | Comparaison des valeurs triées, une à une. |
| 5 | 🔴 `rank a pair above high cards` | Une paire de 2 perd contre un as seul. |
| 6 | 🟢 `compare the shape of value groups before the values` | **L'idée centrale** : grouper les valeurs, trier les groupes par taille puis par valeur. Le « profil » (`[2,1,1,1]` pour une paire) se compare avant les valeurs, et les valeurs ordonnées ainsi (paire d'abord, puis les cartes isolées) donnent directement le départage de chaque catégorie. |
| 7 | 📌 `order two pairs, three of a kind, full house and four of a kind` | Le profil classe déjà correctement deux paires `[2,2,1]` < brelan `[3,1,1]` < full `[3,2]` < carré `[4,1]`. |
| 8 | 🔵 `remove a confusing expression from the groups test` | J'avais laissé dans le test un `.let` qui jetait son résultat : nettoyé. |
| 9 | 🔴 `rank straights and flushes` | Suite et couleur n'ont pas de profil particulier (`[1,1,1,1,1]`) : elles sont classées comme des cartes hautes. |
| 10 | 🟢 `introduce categories with straights and flushes` | Une énumération `Category` explicite, calculée à partir du profil, des couleurs et de l'écart entre valeurs extrêmes. Le profil cède la place à la catégorie dans la comparaison. |
| 11 | 🔴 `rank a straight flush above four of a kind` | La quinte flush est vue comme une simple couleur. |
| 12 | 🟢 `recognise straight flushes` | Nouvelle catégorie en tête. |
| 13 | 🔵 `name the flush and straight conditions` | `isFlush`, `isStraight`. |
| 14 | 🔴 `announce a tie` | `Game` n'existe pas. |
| 15 | 🟢 `announce a tie` | `return "Tie."`. |
| 16 | 🔴 `announce the winner as in the kata sample` | Les trois autres lignes de l'énoncé. |
| 17 | 🟢 `name the winner and the deciding card` | La raison : la catégorie du gagnant, puis la première valeur qui diffère (« high card: 9 ») ou, pour un full, « 4 over 2 ». |
| 18 | 🔴 `reject malformed lines and hands` | Une ligne mal formée lève une `NullPointerException` (le `!!` de l'étape 17), les mauvaises cartes passent. |
| 19 | 🟢 `validate lines and hands` | Cinq cartes, valeurs et couleurs connues, pas de doublon. |

## Solution

```kotlin
private val groups = values.groupingBy { it }.eachCount().entries
    .sortedWith(compareByDescending<Map.Entry<Int, Int>> { it.value }.thenByDescending { it.key })
val ordered = groups.map { it.key }   // l'ordre de départage
override fun compareTo(other: Hand) =
    category.compareTo(other.category).takeIf { it != 0 } ?: compareLists(ordered, other.ordered)
```

Choix assumé : l'as ne compte que comme carte haute, comme dans l'énoncé (pas de suite A-2-3-4-5).

## Ce que j'en retiens

Ordonner les valeurs « par taille de groupe puis par valeur » donne en une seule liste le départage de toutes les catégories : paire, deux paires, full, kickers. C'est ce qui garde le code court quand les catégories s'ajoutent.

## Lancer les tests

```bash
./gradlew :katas:poker-hands:test
git log --reverse --format='%s%n%b' strict-tdd-start.. -- katas/poker-hands   # l'historique TDD
```
