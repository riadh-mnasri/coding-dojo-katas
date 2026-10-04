# Employee Report

🇬🇧 [English version](README.en.md) · Énoncé : [codingdojo.org/kata/Employee-Report](https://codingdojo.org/kata/Employee-Report/)

## Le kata

Un rapport pour une épicerie qui ouvre le dimanche, où les moins de 18 ans ne peuvent pas travailler. Quatre user stories, à prendre une par une sans lire la suite :

1. lister les employés qui peuvent travailler le dimanche ;
2. les trier par nom ;
3. mettre les noms en majuscules ;
4. trier par nom **décroissant** au lieu de croissant.

Le vrai sujet du kata : montrer comment des **assertions trop précises** rendent les tests fragiles.

## Démarche TDD

🔴 rouge · 🟢 vert · 🔵 refactor · 📌 test vert du premier coup

Chaque test ne vérifie **que** l'exigence de sa story : le filtre ignore l'ordre et la casse, le tri ignore le contenu, la casse ignore l'ordre. Ainsi, une story ne casse jamais le test d'une autre.

| # | Étape | Ce qui s'est passé |
|---|---|---|
| 1 | 🔴 `keep only employees allowed to work on Sundays` | Compilation impossible. Assertion : `containsExactlyInAnyOrder` sur les noms passés en minuscules. |
| 2 | 🟢 `keep employees of 18 or more` | `age >= 18`. L'énoncé dit « plus de 18 ans », mais la règle légale interdit les **moins** de 18 ans : Sepp, 18 ans, peut travailler. |
| 3 | 🔴 `sort the report by name` | Assertion : la liste est triée (`isSortedAccordingTo`), sans figer son contenu. |
| 4 | 🟢 `sort names` | `sorted()`. |
| 5 | 🔴 `capitalize the names` | Assertion : chaque nom est en majuscules (`allSatisfy`). |
| 6 | 🟢 `capitalize names` | `uppercase()`. Les tests 1 et 3 restent verts **sans modification**, grâce à leurs assertions ciblées. |
| 7 | 🔴 `sort names in descending order` | L'exigence de la story 2 change : je **modifie** son test (ordre inversé) plutôt que d'en ajouter un second qui contredirait le premier. |
| 8 | 🟢 `sort names in descending order` | `sortedDescending()`. |
| 9 | 🔵 `name the legal Sunday age` | Constante `MINIMUM_SUNDAY_AGE` et commentaire sur la règle. |
| 10 | 📌 `show the whole report in one readable example` | Un seul test fige la sortie complète (`SEPP`, `MIKE`), comme documentation. |

## Solution

```kotlin
fun sundayWorkers(): List<String> = employees
    .filter { it.age >= MINIMUM_SUNDAY_AGE }
    .map { it.name.uppercase() }
    .sortedDescending()
```

## Ce que j'en retiens

Si le premier test avait été `containsExactly("Sepp", "Mike")`, les stories 2, 3 et 4 l'auraient cassé tour à tour, sans qu'aucune régression réelle n'ait eu lieu. Une assertion doit décrire l'exigence testée, pas la sortie actuelle.

## Lancer les tests

```bash
./gradlew :katas:employee-report:test
git log --reverse --format='%s%n%b' strict-tdd-start.. -- katas/employee-report   # l'historique TDD
```
