# Wallet

🇫🇷 [Version française](README.md) · Kata: [codingdojo.org/kata/Wallet](https://codingdojo.org/kata/Wallet/)

## The kata

A wallet holds stocks (`Stock`: a quantity and a type, for instance petroleum, bitcoin, euros, dollars). Compute its value in a currency, using an exchange rate provider:

```
Wallet(Stock(5, PETROLEUM)).value(EUR, rateProvider)
rateProvider.rate(FromStockType, ToCurrency) -> Amount
```

## TDD walkthrough

🔴 red · 🟢 green · 🔵 refactor · 📌 test green on first run

The rate provider is a **port**: tests use a fixed table, never a real API (the ones the kata mentions have since disappeared or changed).

| # | Step | What happened |
|---|---|---|
| 1 | 🔴 `value an empty wallet at zero` | Does not compile: no `Wallet`, `RateProvider` or `Value`. |
| 2 | 🟢 `value an empty wallet at zero` | The domain types, and a hard-coded `Value(0, EUR)`. |
| 3 | 🔴 `value a stock with its exchange rate` | 5 barrels at 62.50: expected 312.50, got 0. |
| 4 | 🟢 `sum the stocks converted at their rate` | A `fold` adding `quantity × rate`. `BigDecimal` everywhere: this is money. |
| 5 | 📌 `add up several stocks` | Petroleum, bitcoin and dollars summed. |
| 6 | 🔴 `value cash in its own currency without asking for a rate` | The fake provider has no EUR → EUR rate and throws. |
| 7 | 🟢 `count cash of the target currency at face value` | A `StockType` standing for a currency knows its `Currency`; then the rate is 1 without asking the provider. |
| 8 | 🔴 `round the value to cents` | $0.125 × 0.92 gives 0.11500. |
| 9 | 🟢 `round values to cents, comparing amounts numerically` | `Value` rounds to cents (banker's `HALF_EVEN`: 0.115 → 0.12). But `0.00` and `0` are not `equals` as `BigDecimal`s: instead of editing the first test, `Value` now compares amounts numerically, which is the right business meaning. |

## Solution

```kotlin
fun value(currency: Currency, rates: RateProvider): Value =
    Value(stocks.fold(BigDecimal.ZERO) { total, stock -> total + stock.quantity * rateOf(stock.type, currency, rates) }, currency)

private fun rateOf(type: StockType, currency: Currency, rates: RateProvider) =
    if (type.currency == currency) BigDecimal.ONE else rates.rate(type, currency)
```

## Takeaways

The first cycle's test (`Value(0, EUR)` compared by equality) acted as a safety net at cycle 9: rounding would have broken equality, which pushed toward real money-value semantics.

## Running the tests

```bash
./gradlew :katas:wallet:test
git log --reverse --format='%s%n%b' strict-tdd-start.. -- katas/wallet   # the TDD history
```
