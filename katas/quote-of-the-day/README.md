# Quote of the Day

🇬🇧 [English version](README.en.md) · Énoncé : [codingdojo.org/kata/QotdCgi](https://codingdojo.org/kata/QotdCgi/)

## Le kata

Un service web qui renvoie une citation **différente à chaque visite**, et, avec un paramètre `?q=foobar`, une citation au hasard qui contient « foobar ».

Matteo Vaccari a conçu ce kata pour enseigner le web à des étudiants, sans TDD (le retour vient du rechargement de la page). Ici, le même exercice est fait en TDD : le cœur métier d'abord, l'adaptateur HTTP ensuite.

## Démarche TDD

🔴 rouge · 🟢 vert · 🔵 refactor · 📌 test vert du premier coup

L'aléatoire est injecté : en test, un `Random` qui renvoie toujours le premier candidat rend les tirages prévisibles.

| # | Étape | Ce qui s'est passé |
|---|---|---|
| 1 | 🔴 `give a quote from the collection` | Compilation impossible. |
| 2 | 🟢 `draw a quote at random` | `collection.random(random)`. |
| 3 | 🔴 `never give the same quote twice in a row` | Avec le tirage « toujours le premier », la même citation revient. |
| 4 | 🟢 `draw among the quotes other than the last one` | On exclut la dernière citation donnée. **Écart** : j'y ai ajouté le cas d'une collection d'une seule citation sans test rouge. |
| 5 | 🔴 `pick a quote containing the searched word` | `next(containing)` n'existe pas ; aucune correspondance donne `null`. |
| 6 | 🟢 `search quotes containing a word` | Filtrage, puis même tirage. **Écart** : la recherche est insensible à la casse sans qu'un test l'ait demandé. |
| 7 | 📌 `cover the single-quote collection, handled without a red test` | Rattrapage de l'écart de l'étape 4. |
| 8 | 📌 `cover the case-insensitive search written ahead of its test` | Rattrapage de l'écart de l'étape 6. |
| 9 | 🔴 `serve quotes over HTTP` | Test d'intégration : un vrai serveur sur un port libre (`port = 0`), un vrai client HTTP. |
| 10 | 🟢 `serve quotes with the JDK HTTP server` | `com.sun.net.httpserver`, sans framework : lecture du paramètre `q`, 200 avec la citation ou 404. Le `main` assemble le tout (non testé, sans logique). |

## Lancer

```bash
./gradlew :katas:quote-of-the-day:test
# puis lancer main() de QuoteServer.kt et ouvrir http://localhost:8080/?q=make
```

## Ce que j'en retiens

- Séparer `Quotes` (le métier) de `QuoteServer` (le web) permet de tester toutes les règles sans réseau ; seuls deux tests démarrent un vrai serveur.
- Deux 📌 de rattrapage de plus : les « petites évidences » (casse, collection d'un seul élément) sont justement celles qu'on écrit sans test.

## Historique

```bash
git log --reverse --format='%s%n%b' strict-tdd-start.. -- katas/quote-of-the-day
```
