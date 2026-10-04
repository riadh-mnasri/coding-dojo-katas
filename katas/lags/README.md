# Lags

🇬🇧 [English version](README.en.md) · Énoncé : [codingdojo.org/kata/Lags](https://codingdojo.org/kata/Lags/)

## Le kata

ABEAS Corp n'a qu'un avion. Ses clients envoient des demandes de location : un départ, une durée, un prix. Trouver la combinaison de demandes compatibles (l'avion ne fait qu'un vol à la fois) qui maximise le gain.

```
AF514 0 5 10
CO5 3 7 14
AF515 5 9 7
BA01 6 9 8
```

Meilleure combinaison : AF514 + BA01 = 18.

## Démarche TDD

🔴 rouge · 🟢 vert · 🔵 refactor · 📌 test vert du premier coup

« Ce kata paraît simple au premier abord » : la version simple a été écrite et gardée tant qu'elle suffisait, et c'est un test de taille réaliste qui a imposé la bonne.

| # | Étape | Ce qui s'est passé |
|---|---|---|
| 1 | 🔴 `earn nothing without requests` | Compilation impossible. |
| 2 | 🟢 `earn nothing` | `return 0`. |
| 3 | 🔴 `earn the price of a single request` | Attendu 10. |
| 4 | 🟢 `add up the prices` | La somme de tous les prix. |
| 5 | 🔴 `keep only one of two overlapping requests` | Deux vols qui se chevauchent : 24 au lieu de 14. |
| 6 | 🟢 `try with and without each request, recursively` | Pour la première demande : soit on la refuse, soit on l'accepte et on ne garde que les demandes qui partent après son retour. Juste, mais exponentiel. |
| 7 | 📌 `solve the kata sample and chain back-to-back flights` | L'exemple (18), et un vol qui repart à l'instant où le précédent atterrit. |
| 8 | 🔴 `handle ten thousand requests in two seconds` | 20 000 demandes dont l'optimum est connu **par construction** (10 000 vols d'une heure enchaînés, plus des leurres qui les chevauchent) : `StackOverflowError`, avant même la limite de temps. |
| 9 | 🟢 `compute the best gain bottom-up with a binary search` | Programmation dynamique : `best[i] = max(best[i + 1], prix[i] + best[suivante(i)])`, remplie de la fin vers le début, la demande suivante compatible étant trouvée par dichotomie. O(n log n), sans récursion. |
| 10 | 🔴 `read the request file format` | `parse` n'existe pas. |
| 11 | 🟢 `read one request per line` | Quatre champs par ligne. |

## Solution

```kotlin
for (i in sorted.indices.reversed()) {
    val next = firstStartingFrom(starts, sorted[i].end)
    best[i] = maxOf(best[i + 1], sorted[i].price + best[next])
}
```

## Ce que j'en retiens

- La récursion de l'étape 6 est exactement la relation de la programmation dynamique de l'étape 9 : le premier jet a donné la formule, le test de performance a imposé la façon de la calculer.
- Pour un test de volume, construire l'entrée pour **connaître** l'optimum évite de figer la sortie du code.

## Lancer les tests

```bash
./gradlew :katas:lags:test
git log --reverse --format='%s%n%b' strict-tdd-start.. -- katas/lags   # l'historique TDD
```
