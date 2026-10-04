# Depth First Search (parcours en profondeur)

🇬🇧 [English version](README.en.md) · Énoncé : [codingdojo.org/kata/DepthFirstSearch](https://codingdojo.org/kata/DepthFirstSearch/)

## Le kata

Un parcours en profondeur, avec deux conseils de l'énoncé :

- s'appuyer sur la **pile d'appels** (récursion) plutôt que sur une pile explicite ;
- ne pas construire de classe `Graph` ou `Maze`, mais **poser des questions** à un humain (« où sommes-nous ? », « quelles sont les sorties ? »), et simuler cette conversation dans les tests.

En bonus : une version « événementielle », dont chaque appel `step()` pose au plus une question.

## Démarche TDD

🔴 rouge · 🟢 vert · 🔵 refactor · 📌 test vert du premier coup

Les tests suivent les cas suggérés : graphe à un nœud, à deux nœuds, labyrinthe 2 × 2, arbre binaire complet, labyrinthe 3 × 3. Le `ScriptedGuide` répond aux questions et les **enregistre**, ce qui permet de vérifier l'ordre du parcours.

| # | Étape | Ce qui s'est passé |
|---|---|---|
| 1 | 🔴 `stop at once when the start is the goal` | Compilation impossible : ni `Guide`, ni `DepthFirstSearch`. |
| 2 | 🟢 `answer the start as the path` | `listOf(start)`. |
| 3 | 🔴 `find no path in a one-node graph without goal` | Attendu `null`. |
| 4 | 🟢 `ask whether the start is the goal` | Première question posée au guide. |
| 5 | 🔴 `follow an exit in the two-node graph` | Aucun chemin trouvé. |
| 6 | 🟢 `explore exits recursively on the call stack` | Pour chaque sortie, on explore récursivement ; le chemin se reconstruit en remontant les appels. |
| 7 | 📌 `backtrack out of a dead end in a 2x2 maze` | Le retour arrière est gratuit : c'est le retour des appels. |
| 8 | 🔴 `survive the loops of a 3x3 maze` | Couloirs à double sens : `StackOverflowError`. |
| 9 | 🟢 `never go back to a visited place` | Un ensemble des lieux visités. |
| 10 | 📌 `ask questions in depth-first order on a binary tree` | La conversation enregistrée montre l'ordre exact : R, L, a, b, puis M, c. |
| 11 | 🔴 `search step by step, one question at a time` | `StepByStepSearch` n'existe pas. |
| 12 | 🟢 `keep the search state in an explicit stack of frames` | Comme l'annonçait l'énoncé, les tests changent peu mais l'intérieur change complètement : chaque cadre retient son lieu, son chemin, si le but a été demandé et les sorties restantes. |
| 13 | 🔴 `end the step-by-step search when the goal is unreachable` | `NotFound` n'existe pas. |
| 14 | 🟢 `report an unreachable goal once the stack is empty` | Pile vide : la recherche est terminée. |
| 15 | 🔵 `add a console guide for exploratory testing` | Un `ConsoleGuide` qui pose les questions au terminal (non testé, sans logique) : on explore à la main, les tests rejouent. |

## Solution

```kotlin
private fun explore(place: String, visited: MutableSet<String>): List<String>? {
    visited += place
    if (guide.isGoal(place)) return listOf(place)
    return guide.exitsOf(place)
        .filter { it !in visited }
        .firstNotNullOfOrNull { exit -> explore(exit, visited)?.let { listOf(place) + it } }
}
```

## Ce que j'en retiens

La version récursive tient en cinq lignes parce que la pile d'appels fait le travail. La version pas à pas oblige à rendre cet état explicite, et montre tout ce que la récursion cachait.

## Lancer les tests

```bash
./gradlew :katas:depth-first-search:test
git log --reverse --format='%s%n%b' strict-tdd-start.. -- katas/depth-first-search   # l'historique TDD
```
