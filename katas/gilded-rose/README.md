# Gilded Rose

🇬🇧 [English version](README.en.md) · Énoncé : [codingdojo.org/kata/gilded-rose](https://codingdojo.org/kata/gilded-rose/)

## Le kata

Un kata de **code legacy** : on hérite d'une méthode `updateQuality()` faite d'`if` imbriqués, qui fait vieillir chaque jour les objets d'une auberge (le brie se bonifie, les places de concert prennent de la valeur puis tombent à 0, Sulfuras est légendaire...). Il faut ajouter une catégorie, les objets **Conjured**, qui se dégradent deux fois plus vite, sans toucher à la classe `Item` (elle appartient au gobelin du coin).

## Démarche : caractériser, refactorer, puis TDD

🔴 rouge · 🟢 vert · 🔵 refactor · 📌 test vert du premier coup

Le code existe déjà, sans test : on ne peut pas commencer par un test rouge qui décrit un comportement à écrire. On **fige d'abord ce que le code fait**, on le restructure à l'abri de ce filet, et seulement ensuite on ajoute la fonctionnalité en TDD.

| # | Étape | Ce qui s'est passé |
|---|---|---|
| 0 | `chore: import the legacy code to refactor` | Le code legacy, traduit fidèlement en Kotlin d'après le dépôt de référence d'Emily Bache. Commit hors garde-fou (pas encore de test). |
| 1 | 🔴 `compare 30 days of the reference fixture with an approved output` | **Golden master** : 30 jours d'évolution des objets de la fixture de référence, comparés à une sortie approuvée qui n'existe pas encore. Le test écrit la sortie reçue dans `build/`. (Mon premier jet avait renommé « Conjured Mana Cake » en « Mana Cake » ; corrigé en amendant ce commit avant de le pousser, pour garder la fixture d'origine.) |
| 2 | 📌 `approve the legacy output as the golden master` | La sortie reçue, relue à la main contre les règles de l'énoncé, devient la référence. Elle fige ce que le code fait, **bugs compris**. |
| 3 | 📌 `describe each rule of the requirements on the legacy code` | Un test par règle (14 cas) : une spécification lisible, et un filet plus fin que le golden master. |
| 4 | 🔵 `extract the update of a single item` | La boucle délègue à `updateItem(item)` ; `items[i]` devient `item`. |
| 5 | 🔵 `give each kind of item its own aging rule` | Le cœur du refactoring : un `when` sur le nom, une fonction par catégorie (`ageNormally`, `ageBrie`, `ageBackstagePasses`), et deux petites fonctions `increaseQuality` / `decreaseQuality` qui portent les bornes 0 et 50. Les 15 tests restent verts. |
| 6 | 🔴 `make conjured items degrade twice as fast` | Enfin un vrai cycle TDD : le Conjured Mana Cake perd 1 au lieu de 2. |
| 7 | 🟢 `degrade conjured items twice as fast` | Une catégorie de plus. Le golden master casse, comme prévu : la différence ne porte **que** sur les quatre lignes du Conjured Mana Cake, et la sortie est ré-approuvée dans le même commit, avec l'explication dans son corps. |

## Solution

```kotlin
when (item.name) {
    SULFURAS -> Unit
    AGED_BRIE -> ageBrie(item)
    BACKSTAGE_PASSES -> ageBackstagePasses(item)
    else -> if (item.name.startsWith(CONJURED)) ageConjured(item) else ageNormally(item)
}
```

## Ce que j'en retiens

- Sur du code legacy, le premier test n'est pas rouge : c'est un 📌 qui fige l'existant. Le golden master est rapide à poser et protège tout, mais il est muet sur l'**intention** ; les tests par règle la rendent lisible.
- La fonctionnalité demandée, qui aurait été risquée dans le code d'origine, est devenue une branche du `when` et une fonction de quatre lignes.
- Quand un golden master change, le diff doit se limiter à ce que l'on voulait changer : c'est ce qu'on vérifie avant de ré-approuver.

## Lancer les tests

```bash
./gradlew :katas:gilded-rose:test
git log --reverse --format='%s%n%b' strict-tdd-start.. -- katas/gilded-rose   # l'historique
```
