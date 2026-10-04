# Wallet

🇬🇧 [English version](README.en.md) · Énoncé : [codingdojo.org/kata/Wallet](https://codingdojo.org/kata/Wallet/)

## Le kata

Un portefeuille contient des actifs (`Stock` : une quantité et un type, par exemple pétrole, bitcoin, euros, dollars). Calculer sa valeur dans une devise, à l'aide d'un fournisseur de taux de change :

```
Wallet(Stock(5, PETROLEUM)).value(EUR, rateProvider)
rateProvider.rate(FromStockType, ToCurrency) -> Amount
```

## Démarche TDD

🔴 rouge · 🟢 vert · 🔵 refactor · 📌 test vert du premier coup

Le fournisseur de taux est un **port** : les tests utilisent une table figée, jamais une API réelle (celles citées par l'énoncé ont d'ailleurs disparu ou changé depuis).

| # | Étape | Ce qui s'est passé |
|---|---|---|
| 1 | 🔴 `value an empty wallet at zero` | Compilation impossible : ni `Wallet`, ni `RateProvider`, ni `Value`. |
| 2 | 🟢 `value an empty wallet at zero` | Les types du domaine, et `Value(0, EUR)` en dur. |
| 3 | 🔴 `value a stock with its exchange rate` | 5 barils à 62,50 : attendu 312,50, obtenu 0. |
| 4 | 🟢 `sum the stocks converted at their rate` | Un `fold` qui ajoute `quantité × taux`. `BigDecimal` partout : on parle d'argent. |
| 5 | 📌 `add up several stocks` | Pétrole, bitcoin et dollars additionnés. |
| 6 | 🔴 `value cash in its own currency without asking for a rate` | Le faux fournisseur n'a pas de taux EUR → EUR et lève une exception. |
| 7 | 🟢 `count cash of the target currency at face value` | Un `StockType` qui représente une devise connaît sa `Currency` ; dans ce cas le taux vaut 1 sans interroger le fournisseur. |
| 8 | 🔴 `round the value to cents` | 0,125 $ × 0,92 donne 0,11500. |
| 9 | 🟢 `round values to cents, comparing amounts numerically` | `Value` arrondit au centime (arrondi bancaire `HALF_EVEN` : 0,115 → 0,12). Mais `0.00` et `0` ne sont pas `equals` en `BigDecimal` : plutôt que de modifier le premier test, `Value` compare désormais les montants numériquement, ce qui est le bon sens métier. |

## Solution

```kotlin
fun value(currency: Currency, rates: RateProvider): Value =
    Value(stocks.fold(BigDecimal.ZERO) { total, stock -> total + stock.quantity * rateOf(stock.type, currency, rates) }, currency)

private fun rateOf(type: StockType, currency: Currency, rates: RateProvider) =
    if (type.currency == currency) BigDecimal.ONE else rates.rate(type, currency)
```

## Ce que j'en retiens

Le test du premier cycle (`Value(0, EUR)` comparé par égalité) a joué le rôle de garde-fou au cycle 9 : l'arrondi aurait cassé l'égalité, ce qui a poussé vers une vraie sémantique de valeur monétaire.

## Lancer les tests

```bash
./gradlew :katas:wallet:test
git log --reverse --format='%s%n%b' strict-tdd-start.. -- katas/wallet   # l'historique TDD
```
