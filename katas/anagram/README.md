# Anagram

🇬🇧 [English version](README.en.md) · Énoncé : [codingdojo.org/kata/Anagram](https://codingdojo.org/kata/Anagram/)

## Le kata

Trouver toutes les anagrammes en **deux mots** de « documenting » à partir d'une liste de mots, en réfléchissant au compromis entre performance et lisibilité.

## Une découverte en chemin

La liste de mots proposée par l'énoncé (1 633 mots) ne contient **aucune** anagramme en deux mots de « documenting ». Je l'ai vérifié avec un petit script Python indépendant avant d'écrire le test, et il en est devenu un : la liste est fournie en ressource de test et le résultat attendu est vide.

Avec le dictionnaire `web2` (Webster 1934, domaine public, environ 235 000 mots, livré avec macOS), le même script trouve **52 paires**, par exemple `document + gin`, `coming + tuned`, `medoc + tuning`. Ce test est ignoré (`assumeTrue`) si la machine n'a pas ce dictionnaire, comme sur la CI Linux : un autre dictionnaire (`/usr/share/dict/words`) donnerait d'autres paires.

## Démarche TDD

🔴 rouge · 🟢 vert · 🔵 refactor · 📌 test vert du premier coup

| # | Étape | Ce qui s'est passé |
|---|---|---|
| 1 | 🔴 `find nothing in an empty dictionary` | Compilation impossible. |
| 2 | 🟢 `find nothing yet` | `emptySet()`. |
| 3 | 🔴 `find a pair of words using every letter` | `document + gin` attendu, rien trouvé. |
| 4 | 🟢 `compare sorted letters of every pair of words` | Version naïve et très lisible : toutes les paires `(a, b)` avec `a <= b` dont les lettres triées valent celles de la cible. |
| 5 | 📌 `allow the same word twice` | `ab + ab` pour `abab` : déjà couvert par `a <= b`. (Ce commit annonçait d'abord aussi la casse, qui n'était pas testée ; message corrigé avant de pousser.) |
| 6 | 🔴 `ignore the case of dictionary words` | `Document`, `GIN` : rien trouvé. |
| 7 | 🟢 `lower-case the dictionary` | Dictionnaire normalisé en minuscules et dédoublonné. |
| 8 | 📌 `find no two-word anagram in the kata word list` | Résultat vide sur la liste de l'énoncé, conforme à l'oracle. |
| ⛔ | deux faux rouges écartés | Mes deux premières versions du test suivant ne compilaient pas (inférence de type de `assertTimeoutPreemptively`) : un rouge pour une mauvaise raison. Je les ai annulées avant de pousser et rejoué la phase rouge avec un `ThrowingSupplier` explicite. |
| 9 | 🔴 `search the 235,000-word web2 dictionary in seconds` | **Le bon rouge** : la version naïve dépasse la limite de 10 secondes (environ 5 × 10¹⁰ paires). |
| 10 | 🟢 `index words by their sorted letters to look up the remainder` | Deux idées : ne garder que les mots écrits avec les lettres de la cible (663 sur 235 000), puis les indexer par lettres triées. Pour chaque premier mot, on calcule les lettres restantes et on cherche le second dans l'index. Le test passe en 0,5 s. |
| 11 | 📌 `run the web2 test only on the web2 dictionary` | Ajouté avec la CI : le test retombait sur `/usr/share/dict/words` en l'absence de `web2`, et aurait comparé les 52 paires attendues avec une autre liste de mots. Il est maintenant ignoré sans `web2`. |

## Solution

```kotlin
val candidates = dictionary.filter { it.isWrittenWithLettersOf(target) }
val bySignature = candidates.groupBy(::signature)
return candidates.flatMap { first ->
    val remainder = target.minusLettersOf(first)
    bySignature[signature(remainder)].orEmpty().map { second -> ordered(first, second) }
}.toSet()
```

## Ce que j'en retiens

- La version naïve a été écrite en premier et gardée tant qu'elle suffisait : c'est un **test de performance** qui a justifié l'optimisation, et les tests fonctionnels existants ont garanti qu'elle ne changeait pas le résultat.
- Vérifier un attendu avec un oracle indépendant évite de « figer » dans un test ce que le code produit.

## Lancer les tests

```bash
./gradlew :katas:anagram:test
git log --reverse --format='%s%n%b' strict-tdd-start.. -- katas/anagram   # l'historique TDD
```
