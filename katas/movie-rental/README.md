# Movie Rental

🇬🇧 [English version](README.en.md) · Énoncé : [codingdojo.org/kata/movie-rental](https://codingdojo.org/kata/movie-rental/)

## Le kata

L'exemple qui ouvre *Refactoring* de Martin Fowler : la méthode `statement()` d'un vidéoclub produit un relevé texte. On veut en ajouter une version **HTML**. La consigne : « d'abord refactorer le programme pour rendre l'ajout facile, puis ajouter la fonctionnalité ».

## Démarche

🔴 rouge · 🟢 vert · 🔵 refactor · 📌 test vert du premier coup

| # | Étape | Ce qui s'est passé |
|---|---|---|
| 0 | `chore: import the legacy code to refactor` | Le code du livre, traduit en Kotlin. Commit hors garde-fou. |
| 1 | 📌 `characterise the text statement with hand-computed amounts` | Quatre relevés dont les montants sont calculés **à la main** à partir des règles (et non recopiés depuis l'exécution) : l'exemple de l'énoncé, une nouveauté, des films pour enfants, un client sans location. |
| 2 | 🔵 `move the charge computation to the rental` | Le calcul du montant ne dépend que de la location : il va dans `Rental.charge()`. |
| 3 | 🔵 `move the frequent renter points to the rental` | Idem pour les points de fidélité. |
| 4 | 🔵 `replace the running totals with queries` | Les variables cumulées deviennent `totalCharge()` et `totalFrequentRenterPoints()`. `statement()` ne fait plus que **mettre en forme**. |
| 5 | 🔵 `replace the price code switch with price objects` | Le `when` sur le code de tarif devient une interface `Price` (normal, nouveauté, enfants), chacune avec son calcul. Les codes entiers d'origine restent acceptés. **Écart** : un code inconnu lève désormais une exception, alors que le code d'origine comptait 0 € sans rien dire. Un changement de comportement glissé dans un refactor. |
| 6 | 📌 `pin the behaviour change on unknown price codes` | Rend l'écart de l'étape 5 visible. |
| 7 | 🔴 `print the statement in HTML` | La fonctionnalité demandée, au format de l'énoncé. |
| 8 | 🟢 `print the statement in HTML` | Une méthode de mise en forme de plus, qui réutilise `charge()`, `totalCharge()` et `totalFrequentRenterPoints()`. Avant le refactoring, il aurait fallu dupliquer toute la logique de calcul. |

Détail de format : les montants s'affichent comme dans le relevé texte d'origine (`2.0`), là où l'exemple HTML de l'énoncé écrit `2`.

## Solution

```kotlin
fun htmlStatement(): String {
    val header = "<h1>Rental Record for <em>$name</em></h1>\n<table>\n"
    val lines = rentals.joinToString("") { "  <tr><td>${it.movie.title}</td><td>${it.charge()}</td></tr>\n" }
    val footer = "</table>\n<p>Amount owed is <em>${totalCharge()}</em></p>\n" +
        "<p>You earned <em>${totalFrequentRenterPoints()}</em> frequent renter points</p>"
    return header + lines + footer
}
```

## Ce que j'en retiens

- « Rendre le changement facile, puis faire le changement facile » : quatre refactorings ont transformé l'ajout du HTML en une simple méthode de mise en forme.
- Le remplacement d'un `switch` par du polymorphisme est le moment où l'on est tenté de « corriger » au passage. Si on le fait, il faut le dire, et un test 📌 le rend visible.

## Lancer les tests

```bash
./gradlew :katas:movie-rental:test
git log --reverse --format='%s%n%b' strict-tdd-start.. -- katas/movie-rental   # l'historique
```
