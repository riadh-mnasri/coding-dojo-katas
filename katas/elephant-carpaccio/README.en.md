# Elephant Carpaccio

🇫🇷 [Version française](README.md) · Kata: [codingdojo.org/kata/elephant-carpaccio](https://codingdojo.org/kata/elephant-carpaccio/)

## The kata

Originally a team workshop: slice a product into **very thin slices**, each demoable within minutes ("no UI mock-up, no bare data table: real input, real output").

The product: a point of sale printing a receipt. For each item, a label, a quantity and a price; for the order, a US state that sets the tax. A discount applies depending on the amount before taxes:

| Amount | > 1,000 | > 5,000 | > 7,000 | > 10,000 | > 50,000 |
|---|---|---|---|---|---|
| Discount | 3% | 5% | 7% | 10% | 15% |

| State | UT | NV | TX | AL | CA |
|---|---|---|---|---|---|
| Tax | 6.85% | 8.00% | 6.25% | 4.00% | 8.25% |

## Approach: one slice = one TDD cycle

🔴 red · 🟢 green · 🔵 refactor · 📌 test green on first run

Alone, without a demo every 8 minutes, the spirit of the kata lives in the slicing: each slice is a red/green cycle and brings visible value.

| # | Slice | What happened |
|---|---|---|
| 1 | 🔴🟢 `price one line` | Quantity × price of an item. The first green only looks at the first item. |
| 2 | 🔴🟢 `add up several lines` | Sum of the lines. |
| 3 | 🔴🟢 `add the Utah sales tax` | A single state, hard-coded rate. |
| 4 | 🔴🟢 `tax the other states` | A rate table. |
| 5 | 🔴🟢 `discount big orders` | The highest threshold **strictly exceeded**: exactly 1,000 gets no discount. |
| 6 | 🔴🟢 `tax the discounted amount` | Tax applies to the amount **after** discount; rounded to the cent. Example worked out by hand in the test. |
| 7 | 🔴🟢 `print the receipt` | The receipt in the kata's format. (My test mixed 53- and 54-character lines: fixed by amending the red commit before pushing.) |
| 8 | 🔴🟢 `reject unknown states and empty orders` | Validation at construction time. |

## Solution

```kotlin
val discount = percentOf(totalWithoutTaxes, discountRate)
val tax = percentOf(totalWithoutTaxes - discount, taxRate)
val totalPrice = totalWithoutTaxes - discount + tax
```

```
Laptop                   2       1000.00       2000.00
Mouse                    1         25.00         25.00
------------------------------------------------------
Total without taxes                            2025.00
Discount 3%                                     -60.75
Tax 6.25%                                      +122.77
------------------------------------------------------
Total price                                    2087.02
```

## Takeaways

Slicing matters more than code: each slice could have shipped on its own. Taxing a single state before building the full table (slices 3 and 4) is TDD's "fake it" discipline, applied to the product.

## Running the tests

```bash
./gradlew :katas:elephant-carpaccio:test
git log --reverse --format='%s%n%b' strict-tdd-start.. -- katas/elephant-carpaccio   # the TDD history
```
