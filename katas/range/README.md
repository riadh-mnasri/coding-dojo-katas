# Range

🇬🇧 [English version](README.en.md) · Énoncé : [codingdojo.org/kata/Range](https://codingdojo.org/kata/Range/)

## Le kata

Un intervalle d'entiers noté avec des bornes ouvertes `( )` ou fermées `[ ]`, par exemple `[2,6)`. Il doit savoir :

- s'il contient des valeurs (`[2,6)` contient `{2, 4}`, pas `{-1, 1, 6, 10}`) ;
- lister ses points (`{2, 3, 4, 5}`) et ses extrémités (`(2,6]` → `{3, 6}`) ;
- s'il contient un autre intervalle, s'il le chevauche, s'il lui est égal.

## Démarche TDD

🔴 rouge · 🟢 vert · 🔵 refactor · 📌 test vert du premier coup

| # | Étape | Ce qui s'est passé |
|---|---|---|
| 1 | 🔴 `contain the values inside a half-open range` | Compilation impossible. |
| 2 | 🟢 `contain everything for now` | `return true`. |
| 3 | 🔴 `exclude values outside and the open end` | Tout est « contenu ». |
| 4 | 🟢 `parse the bounds and check values against them` | Une expression régulière pour la notation et deux comparaisons selon que chaque borne est incluse ou non. |
| 5 | 🔴 `list all the points` | `allPoints` n'existe pas. |
| 6 | 🟢 `list the points between the first and the last` | **Simplification clé** : on calcule une fois le premier et le dernier point entier (`(2` donne 3, `6)` donne 5). `contains` se réduit alors à `value in first..last`. |
| 7 | 🔴 `give the end points of each kind of range` | `endPoints` n'existe pas. |
| 8 | 🟢 `expose the end points` | `first to last`, déjà calculés. |
| 9 | 🔴 `tell whether it contains another range` | `containsRange` n'existe pas. |
| 10 | 🟢 `contain a range whose end points are inside` | `contains(other.first, other.last)`. |
| 11 | 🔴 `tell whether two ranges overlap` | `overlapsRange` n'existe pas. |
| 12 | 🟢 `overlap when each range starts before the other ends` | La condition classique de chevauchement. |
| 13 | 🔴 `compare ranges by value` | Égalité par référence. |
| 14 | 🟢 `compare ranges by their points` | Égalité sur `first` et `last`. |
| 15 | 📌 `treat [3,5) and [3,4] as the same integer range` | Conséquence assumée : sur des entiers, `[3,5)`, `[3,4]` et `(2,5)` sont le même ensemble. |
| 16 | 🔴 `reject malformed or empty ranges` | `[6,2]` et `(3,4)` (aucun entier) sont acceptés. |
| 17 | 🟢 `refuse ranges without any point` | Un `require(first <= last)` à la construction. |

## Solution

Toute la classe repose sur deux valeurs dérivées de la notation :

```kotlin
private val first = if (startIncluded) start else start + 1
private val last = if (endIncluded) end else end - 1
```

Ensuite chaque opération est une ligne : appartenance, points, extrémités, inclusion, chevauchement, égalité.

## Ce que j'en retiens

Les bornes ouvertes ou fermées sont un détail de notation. Les traduire tout de suite en points entiers (étape 6) a supprimé la combinatoire « ouvert/fermé » de toutes les opérations suivantes.

## Lancer les tests

```bash
./gradlew :katas:range:test
git log --reverse --format='%s%n%b' strict-tdd-start.. -- katas/range   # l'historique TDD
```
