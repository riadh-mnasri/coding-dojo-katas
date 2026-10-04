# Elephant Carpaccio

🇬🇧 [English version](README.en.md) · Énoncé : [codingdojo.org/kata/elephant-carpaccio](https://codingdojo.org/kata/elephant-carpaccio/)

## Le kata

À l'origine, un atelier d'équipe : découper un produit en **tranches très fines**, chacune démontrable en quelques minutes (« pas de maquette, pas de simple table de données : une vraie entrée, une vraie sortie »).

Le produit : une caisse qui édite un ticket. Pour chaque article, un libellé, une quantité et un prix ; pour la commande, un État américain qui fixe la taxe. Une remise s'applique selon le montant hors taxes :

| Montant | > 1 000 | > 5 000 | > 7 000 | > 10 000 | > 50 000 |
|---|---|---|---|---|---|
| Remise | 3 % | 5 % | 7 % | 10 % | 15 % |

| État | UT | NV | TX | AL | CA |
|---|---|---|---|---|---|
| Taxe | 6,85 % | 8,00 % | 6,25 % | 4,00 % | 8,25 % |

## Démarche : une tranche = un cycle TDD

🔴 rouge · 🟢 vert · 🔵 refactor · 📌 test vert du premier coup

Seul, sans démo toutes les 8 minutes, l'esprit du kata se retrouve dans le découpage : chaque tranche est un cycle rouge/vert et apporte une valeur visible.

| # | Tranche | Ce qui s'est passé |
|---|---|---|
| 1 | 🔴🟢 `price one line` | Quantité × prix d'un article. Le premier vert ne regarde que le premier article. |
| 2 | 🔴🟢 `add up several lines` | Somme des lignes. |
| 3 | 🔴🟢 `add the Utah sales tax` | Un seul État, taux en dur. |
| 4 | 🔴🟢 `tax the other states` | Une table des taux. |
| 5 | 🔴🟢 `discount big orders` | Le palier le plus haut **strictement dépassé** : 1 000 tout rond n'a pas de remise. |
| 6 | 🔴🟢 `tax the discounted amount` | La taxe porte sur le montant **après** remise ; arrondi au centime. Exemple calculé à la main dans le test. |
| 7 | 🔴🟢 `print the receipt` | Le ticket au format de l'énoncé. (Mon test mélangeait des lignes de 53 et 54 caractères : corrigé en amendant le commit rouge avant de le pousser.) |
| 8 | 🔴🟢 `reject unknown states and empty orders` | Validation à la construction. |

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

## Ce que j'en retiens

Le découpage compte plus que le code : chaque tranche aurait pu être livrée seule. Faire la taxe d'un seul État avant la table complète (tranches 3 et 4), c'est la même discipline que le « fake it » du TDD, appliquée au produit.

## Lancer les tests

```bash
./gradlew :katas:elephant-carpaccio:test
git log --reverse --format='%s%n%b' strict-tdd-start.. -- katas/elephant-carpaccio   # l'historique TDD
```
