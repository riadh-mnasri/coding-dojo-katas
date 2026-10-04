# Pagination Seven

🇬🇧 [English version](README.en.md) · Énoncé : [codingdojo.org/kata/PaginationSeven](https://codingdojo.org/kata/PaginationSeven/)

## Le kata

Afficher une pagination sur **7 cases au plus**, la page courante entre parenthèses :

| Cas | Exemple |
|---|---|
| I. 7 pages ou moins | `1 (2) 3 4 5` |
| II. au milieu | `1 … 41 (42) 43 … 100` |
| III. près du début | `1 2 3 (4) 5 … 9` |
| IV. près de la fin | `1 … 5 (6) 7 8 9` |

## Démarche TDD

🔴 rouge · 🟢 vert · 🔵 refactor · 📌 test vert du premier coup

Les tests suivent les quatre parties de l'énoncé, dans l'ordre.

| # | Étape | Ce qui s'est passé |
|---|---|---|
| 1 | 🔴 `show a single page` | Compilation impossible. |
| 2 | 🟢 `show a hard-coded single page` | `return "(1)"`. |
| 3 | 🔴 `show every page up to seven` | Partie I. |
| 4 | 🟢 `list every page and mark the current one` | Toutes les pages, la courante entre parenthèses. |
| 5 | 🔴 `fold far pages into ellipses around the middle` | Partie II : les 100 pages s'affichent. |
| 6 | 🟢 `use seven slots with ellipses around the current page` | Une liste de 7 « cases » où `null` représente l'ellipse, puis un seul rendu. |
| 7 | 🔴 `drop the first ellipsis near the start` | Partie III : page 2 de 9 donne `1 … 1 (2) 3 … 9`. |
| 8 | 🟢 `show the first five pages near the start` | Pages 1 à 5, `…`, dernière page. |
| 9 | 🔴 `drop the last ellipsis near the end` | Partie IV : page 100 de 100 affiche une page 101. |
| 10 | 🟢 `show the last five pages near the end` | Cas symétrique. |
| 11 | 🔵 `name the slot count and the edge size` | Les nombres magiques 7, 4, 5 et 3 deviennent `SLOTS` et `EDGE = SLOTS - 2`, avec l'explication du calcul. |
| 12 | 🔴 `reject a page outside the pagination` | Page 0 ou 10 sur 9 acceptées. |
| 13 | 🟢 `require an existing page` | Un `require`. |

## Solution

```kotlin
val slots = when {
    total <= SLOTS -> (1..total).toList()
    page < EDGE -> (1..EDGE).toList() + listOf(ELLIPSIS, total)
    page > total - EDGE + 1 -> listOf(1, ELLIPSIS) + (total - EDGE + 1..total).toList()
    else -> listOf(1, ELLIPSIS, page - 1, page, page + 1, ELLIPSIS, total)
}
```

## Ce que j'en retiens

Séparer le **choix des cases** (une liste où `null` est une ellipse) de leur **rendu** a évité de manipuler des chaînes dans chaque cas. Les bornes (4 et 6 sur 9 pages) sont les tests qui comptent : ce sont elles qui fixent `<` ou `<=`.

## Lancer les tests

```bash
./gradlew :katas:pagination-seven:test
git log --reverse --format='%s%n%b' strict-tdd-start.. -- katas/pagination-seven   # l'historique TDD
```
