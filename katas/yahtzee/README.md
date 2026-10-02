# Yahtzee

🇬🇧 [English version](README.en.md) · Énoncé : [codingdojo.org/kata/Yahtzee](https://codingdojo.org/kata/Yahtzee/)

## Le kata

Pour un lancer de 5 dés et une catégorie, donner le score : chance, yahtzee (50), as à six, paire, deux paires, brelan, carré, petite suite (15), grande suite (20), full. Un lancer incompatible avec la catégorie rapporte 0.

## Démarche TDD

🔴 rouge · 🟢 vert · 🔵 refactor · 📌 test vert du premier coup

| # | Étape | Ce qui s'est passé |
|---|---|---|
| 1 | 🔴 `score chance as the sum of the dice` | Compilation impossible. |
| 2 | 🟢 `sum the dice for chance` | `dice.sum()`, sans regarder la catégorie. |
| 3 | 🔴 `score 50 for a yahtzee` | `YAHTZEE` n'existe pas. |
| 4 | 🟢 `score a yahtzee` | Un `when` sur la catégorie. |
| 5 | 🔴 `score the upper section by face` | `ONES` à `SIXES` n'existent pas. |
| 6 | 🟢 `let each category carry its scoring rule, upper section included` | Plutôt qu'un `when` qui allait atteindre quinze branches, chaque valeur de l'`enum` porte sa fonction de score. Les six faces partagent une fabrique `sumOf(face)`. |
| 7 | 🔴 `score the highest pair` | `PAIR` n'existe pas. |
| 8 | 🟢 `score the highest pair` | Fabrique `ofAKind(count)` : la plus haute face présente au moins `count` fois. |
| 9 | 🔴 `score three and four of a kind` | Catégories absentes. |
| 10 | 🟢 `reuse the of-a-kind rule for three and four` | `ofAKind(3)` et `ofAKind(4)` : deux lignes. |
| 11 | 🔴 `score two different pairs` | Catégorie absente. |
| 12 | 🟢 `score two different pairs` | Deux faces différentes présentes au moins deux fois. Choix : un carré n'est pas « deux paires ». |
| 13 | 🔴 `score small and large straights` | Catégories absentes. |
| 14 | 🟢 `score both straights` | Fabrique `straight(1..5)` / `straight(2..6)`, insensible à l'ordre des dés. |
| 15 | 🔴 `score a full house` | Catégorie absente. |
| 16 | 🟢 `score a full house` | Les effectifs des faces doivent valoir exactement `[2, 3]`, ce qui exclut le yahtzee `4,4,4,4,4`. |
| 17 | 🔵 `share face counting between categories` | Un `faceCounts` commun au lieu de quatre `groupingBy`. |
| 18 | 🔴 `require five dice from 1 to 6` | 4 dés ou une face 7 acceptés. |
| 19 | 🟢 `validate the roll before scoring` | Un `require` dans `Yahtzee.score`. |

## Solution

```kotlin
enum class Category(val score: (List<Int>) -> Int) {
    CHANCE({ dice -> dice.sum() }),
    PAIR(ofAKind(2)),
    SMALL_STRAIGHT(straight(1..5)),
    FULL_HOUSE({ dice -> if (faceCounts(dice).values.sorted() == listOf(2, 3)) dice.sum() else 0 }),
    // ...
}
```

Ajouter une catégorie revient à ajouter une ligne dans l'`enum`, souvent à partir d'une fabrique existante.

## Ce que j'en retiens

Le passage d'un `when` à une `enum` qui porte son comportement (étape 6) a été déclenché par l'arrivée de six catégories d'un coup. À partir de là, chaque cycle a ajouté une catégorie sans toucher aux autres.

## Lancer les tests

```bash
./gradlew :katas:yahtzee:test
git log --reverse --format='%s%n%b' strict-tdd-start.. -- katas/yahtzee   # l'historique TDD
```
