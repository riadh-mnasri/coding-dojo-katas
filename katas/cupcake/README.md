# Cupcake

🇬🇧 [English version](README.en.md) · Énoncé : [codingdojo.org/kata/cupcake](https://codingdojo.org/kata/cupcake/)

## Le kata

Fabriquer des gâteaux garnis, dans un ordre qui compte : « 🧁 with 🍫 and 🥜 ». Chaque gâteau a un nom et un prix : 🧁 1 $, 🍪 2 $, 🍫 +0,10 $, 🥜 +0,20 $. Puis des **lots** de gâteaux, 10 % moins chers, et même des lots de lots.

Le kata sert à pratiquer deux patterns : **Décorateur** (les garnitures) et **Composite** (les lots).

## Démarche TDD

🔴 rouge · 🟢 vert · 🔵 refactor · 📌 test vert du premier coup

| # | Étape | Ce qui s'est passé |
|---|---|---|
| 1 | 🔴 `name a cupcake` | Compilation impossible ; le test range d'emblée le cupcake dans une variable de type `Cake`, comme le suggère l'énoncé. |
| 2 | 🟢 `name a cupcake` | Interface `Cake` et classe `Cupcake`. |
| 3 | 🔴 `name a cookie` | `Cookie` n'existe pas. |
| 4 | 🟢 `name a cookie` | Deuxième implémentation. |
| 5 | 🔴 `name a cake with one topping` | `Chocolate` n'existe pas. |
| 6 | 🟢 `decorate a cake with chocolate` | Un **décorateur** : `Chocolate` enveloppe un `Cake` et complète son nom. |
| 7 | 🔴 `chain toppings in order` | `Nuts` n'existe pas. |
| 8 | 🟢 `join further toppings with and` | Classe abstraite `Topping` : « with » pour la première garniture, « and » ensuite (on sait qu'on enveloppe déjà une garniture). |
| 9 | 🔴 `price the base cakes` | `price()` n'existe pas. |
| 10 | 🟢 `price cupcakes and cookies` | `BigDecimal`. Les garnitures délèguent simplement le prix pour l'instant. |
| 11 | 🔴 `add the price of each topping` | Une garniture ne coûte rien. |
| 12 | 🟢 `add each topping price` | Chaque garniture ajoute son coût à celui du gâteau qu'elle enveloppe. |
| 13 | 🔴 `discount a bundle by ten percent` | `Bundle` n'existe pas. |
| 14 | 🟢 `price a bundle ten percent below its cakes` | Un **composite** : un `Bundle` est un `Cake` qui contient des `Cake`. Son nom est laissé en `TODO()` : aucun test ne le demande encore. |
| 15 | 📌 `nest bundles of bundles` | Les lots de lots fonctionnent sans code : un `Bundle` est un `Cake` comme un autre. |
| 16 | 🔴 `describe a bundle` | Le `TODO()` lève `NotImplementedError`. |
| 17 | 🟢 `describe a bundle with its cakes` | `📦 [🧁 with 🍫, 🍪]` (format choisi, l'énoncé n'en impose pas). |
| 18 | 🔴 `refuse an empty bundle` | `Bundle()` accepté. |
| 19 | 🟢 `require at least one cake in a bundle` | Un `require`. |
| 20 | 🔵 `name the bundle discount` | Constante `DISCOUNT`. |

## Solution

```kotlin
val cake = Nuts(Chocolate(Cookie()))      // "🍪 with 🍫 and 🥜", 2,30 $
val bundle = Bundle(Bundle(Cupcake(), Cookie()), Cupcake())   // 3,33 $
```

## Ce que j'en retiens

- Le `TODO()` de l'étape 14 est une façon honnête d'écrire le minimum : il compile, ne cache rien, et c'est le test suivant qui l'a fait tomber.
- Les lots de lots (étape 15) sont gratuits : c'est la promesse du Composite, vérifiée par un test.

## Lancer les tests

```bash
./gradlew :katas:cupcake:test
git log --reverse --format='%s%n%b' strict-tdd-start.. -- katas/cupcake   # l'historique TDD
```
