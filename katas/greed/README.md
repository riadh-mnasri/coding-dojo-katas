# Greed

🇬🇧 [English version](README.en.md) · Énoncé : [codingdojo.org/kata/Greed](https://codingdojo.org/kata/Greed/)

## Le kata

Calculer le score d'un lancer du jeu de dés Greed (jusqu'à 6 dés) :

| Combinaison | Score |
|---|---|
| un 1 / un 5 | 100 / 50 |
| brelan de 1 | 1000 |
| brelan de 2 à 6 | face × 100 |
| carré, 5 et 6 dés identiques | score du brelan × 2, × 4, × 8 |
| trois paires | 800 |
| suite 1 à 6 | 1200 |

## Démarche TDD

🔴 rouge · 🟢 vert · 🔵 refactor · 📌 test vert du premier coup

| # | Étape | Ce qui s'est passé |
|---|---|---|
| 1 | 🔴 `score nothing for no dice` | Compilation impossible. |
| 2 | 🟢 `score zero` | `return 0`. |
| 3 | 🔴 `score single ones and fives` | Attendu 100, obtenu 0. |
| ⛔ | tentative de vert refusée | `sumOf` avec un `when` qui renvoie des littéraux : ambiguïté de surcharge `Int`/`Long`, le code ne compile pas. |
| 4 | 🟢 `score single ones and fives` | `map { ... }.sum()`. |
| 5 | 🔴 `score triples` | `1 1 1` donne 300 au lieu de 1000. |
| 6 | 🟢 `score triples by face, then the remaining singles` | On regroupe les dés par face (`groupingBy`) : un brelan compte selon la face, le reste en simples. |
| 7 | 🔴 `double the triple score for each extra die` | Quatre 2 donnent 200 au lieu de 400. |
| 8 | 🟢 `multiply the triple score for four, five and six of a kind` | × 2, × 4, × 8 : c'est un décalage de bits, `tripleScore shl (count - 3)`. |
| 9 | 🔴 `score three pairs and a straight` | 0 et 150 au lieu de 800 et 1200. |
| 10 | 🟢 `score three pairs and straights as whole rolls` | Ces deux combinaisons portent sur le lancer entier : elles sont testées avant le calcul face par face. Constantes et commentaires au passage. |
| 11 | 🔴 `accept up to six real dice only` | Sept dés ou une face 7 acceptés. |
| 12 | 🟢 `validate the number of dice and their faces` | Deux `require`. |
| 13 | 📌 `mix triples and singles` | `1 1 1 5 1 = 2050`, `3 4 5 3 3 = 350`... |

## Solution

```kotlin
fun score(dice: List<Int>): Int {
    val counts = dice.groupingBy { it }.eachCount()
    return when {
        counts.size == 6 -> STRAIGHT
        counts.size == 3 && counts.values.all { it == 2 } -> THREE_PAIRS
        else -> counts.map { (face, count) -> scoreOf(face, count) }.sum()
    }
}

private fun scoreOf(face: Int, count: Int) =
    if (count >= 3) tripleScore(face) shl (count - 3) else count * singleScore(face)
```

## Ce que j'en retiens

Regrouper les dés par face dès les brelans rend toutes les règles suivantes locales à une face, sauf deux (trois paires et suite) qui concernent le lancer entier. Les distinguer explicitement évite de mélanger ces deux niveaux.

## Lancer les tests

```bash
./gradlew :katas:greed:test
git log --reverse --format='%s%n%b' strict-tdd-start.. -- katas/greed   # l'historique TDD
```
