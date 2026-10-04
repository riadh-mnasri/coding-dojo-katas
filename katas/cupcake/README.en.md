# Cupcake

🇫🇷 [Version française](README.md) · Kata: [codingdojo.org/kata/cupcake](https://codingdojo.org/kata/cupcake/)

## The kata

Build cakes with toppings, in an order that matters: "🧁 with 🍫 and 🥜". Each cake has a name and a price: 🧁 $1, 🍪 $2, 🍫 +$0.10, 🥜 +$0.20. Then **bundles** of cakes, 10% cheaper, and even bundles of bundles.

The kata practises two patterns: **Decorator** (toppings) and **Composite** (bundles).

## TDD walkthrough

🔴 red · 🟢 green · 🔵 refactor · 📌 test green on first run

| # | Step | What happened |
|---|---|---|
| 1 | 🔴 `name a cupcake` | Does not compile; the test stores the cupcake in a `Cake` variable straight away, as the kata suggests. |
| 2 | 🟢 `name a cupcake` | A `Cake` interface and a `Cupcake` class. |
| 3 | 🔴 `name a cookie` | `Cookie` does not exist. |
| 4 | 🟢 `name a cookie` | A second implementation. |
| 5 | 🔴 `name a cake with one topping` | `Chocolate` does not exist. |
| 6 | 🟢 `decorate a cake with chocolate` | A **decorator**: `Chocolate` wraps a `Cake` and extends its name. |
| 7 | 🔴 `chain toppings in order` | `Nuts` does not exist. |
| 8 | 🟢 `join further toppings with and` | An abstract `Topping`: "with" for the first topping, "and" afterwards (it knows it wraps a topping already). |
| 9 | 🔴 `price the base cakes` | `price()` does not exist. |
| 10 | 🟢 `price cupcakes and cookies` | `BigDecimal`. Toppings simply delegate the price for now. |
| 11 | 🔴 `add the price of each topping` | A topping costs nothing. |
| 12 | 🟢 `add each topping price` | Each topping adds its cost to the wrapped cake's. |
| 13 | 🔴 `discount a bundle by ten percent` | `Bundle` does not exist. |
| 14 | 🟢 `price a bundle ten percent below its cakes` | A **composite**: a `Bundle` is a `Cake` holding `Cake`s. Its name is left as `TODO()`: no test asks for it yet. |
| 15 | 📌 `nest bundles of bundles` | Bundles of bundles work with no code: a `Bundle` is a `Cake` like any other. |
| 16 | 🔴 `describe a bundle` | The `TODO()` throws `NotImplementedError`. |
| 17 | 🟢 `describe a bundle with its cakes` | `📦 [🧁 with 🍫, 🍪]` (a chosen format, the kata imposes none). |
| 18 | 🔴 `refuse an empty bundle` | `Bundle()` accepted. |
| 19 | 🟢 `require at least one cake in a bundle` | A `require`. |
| 20 | 🔵 `name the bundle discount` | A `DISCOUNT` constant. |

## Solution

```kotlin
val cake = Nuts(Chocolate(Cookie()))      // "🍪 with 🍫 and 🥜", $2.30
val bundle = Bundle(Bundle(Cupcake(), Cookie()), Cupcake())   // $3.33
```

## Takeaways

- The `TODO()` of step 14 is an honest way to write the minimum: it compiles, hides nothing, and the next test is what brought it down.
- Bundles of bundles (step 15) come for free: that is the Composite's promise, checked by a test.

## Running the tests

```bash
./gradlew :katas:cupcake:test
git log --reverse --format='%s%n%b' strict-tdd-start.. -- katas/cupcake   # the TDD history
```
