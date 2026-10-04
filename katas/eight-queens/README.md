# Eight Queens (les huit dames)

🇬🇧 [English version](README.en.md) · Énoncé : [codingdojo.org/kata/eight-queens](https://codingdojo.org/kata/eight-queens/)

## Le kata

Placer huit dames sur un échiquier sans qu'aucune ne puisse en prendre une autre. L'énoncé demande de trouver **toutes** les solutions, puis d'essayer plusieurs approches et de les comparer : parcours en profondeur, en largeur, une heuristique (conflits minimaux, génétique, recuit simulé...) et la force brute avec masques de bits.

Une solution est représentée par la colonne de la dame de chaque ligne.

## Démarche TDD

🔴 rouge · 🟢 vert · 🔵 refactor · 📌 test vert du premier coup

La première implémentation (profondeur) sert de référence ; les autres sont testées **contre elle**, et un validateur indépendant vérifie les plateaux.

| # | Étape | Ce qui s'est passé |
|---|---|---|
| 1 | 🔴 `solve the one-square board` | Compilation impossible. |
| 2 | 🟢 `solve the one-square board` | Réponse en dur. |
| 3 | 🔴 `find the two solutions of the four-queens board` | `[1,3,0,2]` et `[2,0,3,1]` attendues. |
| 4 | 🟢 `place queens row by row with depth-first backtracking` | Une dame par ligne, seulement sur une case sûre, et retour en arrière quand la ligne n'en a plus. La récursion porte le retour arrière. |
| ⛔ | pin refusé | J'ai voulu commiter le test des 92 solutions comme 📌, mais il utilisait un validateur `Board` pas encore écrit : refusé par le garde-fou (il ne compilait pas), puis commité comme rouge. |
| 5 | 🔴 `check the 92 solutions with an independent validator` | 92 est le nombre de solutions connu ; chaque solution est vérifiée par un validateur qui teste **toutes les paires** de dames, sans réutiliser le code de recherche. |
| 6 | 🟢 `validate a whole board pair by pair` | Le validateur. Les 92 solutions passent. |
| 7 | 🔴 `find the same solutions breadth-first` | `BreadthFirst` n'existe pas. |
| 8 | 🟢 `extend every partial board one row at a time` | Un `fold` ligne par ligne sur toutes les positions partielles. |
| 9 | 📌 `make sure the validator rejects attacking queens` | Le validateur n'avait été testé que sur des plateaux valides. |
| 10 | 🔴 `find the same solutions by brute force with bit masks` | `BruteForce` n'existe pas. |
| 11 | 🟢 `check every permutation with shifted bit masks` | Les permutations des colonnes (8! = 40 320) règlent lignes et colonnes ; les diagonales se vérifient en décalant le bit de chaque ligne de son numéro de ligne, comme décrit dans l'énoncé. |
| 12 | 🔴 `find one solution with the min-conflicts heuristic` | Une solution pour 8 **et pour 100** dames. |
| 13 | 🟢 `repair a random board by minimising conflicts` | Plateau aléatoire, puis on déplace une dame attaquée vers la colonne la moins attaquée. Graine fixée pour des tests reproductibles. |

## Comparaison

Durées relevées sur une exécution des tests (JVM chaude ou non, donc indicatives) :

| Approche | Résultat | Durée | Lisibilité |
|---|---|---|---|
| Profondeur | les 92 solutions | ~15 ms | la plus naturelle : la récursion *est* le retour arrière |
| Largeur | les 92 solutions | ~15 ms | un `fold` court, mais garde en mémoire toutes les positions partielles d'une ligne |
| Force brute + masques | les 92 solutions | ~100 ms | astucieuse mais moins évidente ; explore les 40 320 permutations sans élaguer |
| Conflits minimaux | **une** solution | ~30 ms pour 8 **et** 100 dames | la seule qui passe à l'échelle ; ne garantit ni toutes les solutions ni une durée bornée |

Non traités : l'algorithme génétique et le recuit simulé.

## Ce que j'en retiens

Avoir une implémentation de référence et un validateur indépendant a rendu chaque nouvelle approche testable en une ligne (« mêmes solutions que la référence »). Encore faut-il tester le validateur : il n'avait d'abord été vérifié que sur des plateaux valides, et le test 📌 de l'étape 9 a comblé ce trou.

## Lancer les tests

```bash
./gradlew :katas:eight-queens:test
git log --reverse --format='%s%n%b' strict-tdd-start.. -- katas/eight-queens   # l'historique TDD
```
