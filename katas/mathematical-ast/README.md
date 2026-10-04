# Mathematical AST

🇬🇧 [English version](README.en.md) · Énoncé : [codingdojo.org/kata/mathematical-ast](https://codingdojo.org/kata/mathematical-ast/)

## Le kata

Écrit à l'origine pour pratiquer le pattern **Visiteur** :

1. construire l'arbre syntaxique d'une expression RPN : `3 6 -6 * +` donne `+(3, ×(6, -6))` ;
2. réécrire l'arbre en RPN, en notation infixe, et l'évaluer (−33) ;
3. écrire l'infixe avec le **minimum** de parenthèses ;
4. ajouter la puissance `^` et les flèches de Knuth `↑`.

## Démarche TDD

🔴 rouge · 🟢 vert · 🔵 refactor · 📌 test vert du premier coup

| # | Étape | Ce qui s'est passé |
|---|---|---|
| 1 | 🔴 `parse a lone number` | Compilation impossible. |
| 2 | 🟢 `parse a lone number` | `Operand(token.toLong())`. |
| 3 | 🔴 `parse an addition` | `Operation` et `Operator` n'existent pas. |
| 4 | 🟢 `build operations with a stack` | Une pile, comme pour le kata RPN, mais qui empile des **arbres** au lieu de nombres. |
| 5 | 🔴 `parse the nested example of the kata` | `*` (ou `×`) inconnu. |
| 6 | 🟢 `parse multiplications written with * or ×` | Un opérateur peut avoir des alias d'écriture. |
| 7 | 🔴 `print the tree back in RPN` | `accept` et `RpnPrinter` n'existent pas. |
| 8 | 🟢 `visit the tree to print it in RPN` | Le **Visiteur** : `Expression.accept(visitor)`, et un `Visitor<R>` avec une méthode par type de nœud. L'arbre ne connaît aucun traitement. |
| 9 | 🔴 `evaluate the tree` | `Evaluator` n'existe pas. |
| 10 | 🟢 `evaluate the tree with a visitor` | Un deuxième visiteur, sans toucher à l'arbre. |
| 11 | 🔴 `print the tree in infix notation` | `InfixPrinter` n'existe pas. |
| 12 | 🟢 `print the tree in infix notation` | Toute opération imbriquée entre parenthèses : `3 + (6 × -6)`. |
| 13 | 🔴 `print infix with the minimum of parentheses` | Étape 3 : `MinimalInfixPrinter` n'existe pas. |
| 14 | 🟢 `add parentheses only around lower-precedence operations` | Une priorité par opérateur ; un enfant moins prioritaire que son parent prend des parenthèses. |
| 15 | 🔴 `keep the parentheses subtraction needs on its right` | La soustraction est inconnue, et `3 - (6 - 2)` ne doit pas perdre ses parenthèses. |
| 16 | 🟢 `subtract, parenthesising its right side when needed` | Une opération non associative exige, à droite, une priorité strictement supérieure. |
| 17 | 🔴 `handle the right-associative exponent` | `^` inconnu ; `(2 ^ 3) ^ 2` et `2 ^ 3 ^ 2` diffèrent. |
| 18 | 🟢 `add the right-associative exponent` | Le booléen « associatif » devient un `Grouping` (`ANY`, `LEFT`, `RIGHT`) : chaque sens dit de quel côté une priorité égale exige des parenthèses. |
| 19 | 🔴 `evaluate Knuth's up-arrows` | `↑` et `↑↑` inconnus. |
| 20 | 🟢 `evaluate single and double up-arrows as hyperoperations` | a ↑ b = a^b ; a ↑↑ b = a ↑ (a ↑↑ (b − 1)), d'où 3 ↑↑ 3 = 3^27 = 7 625 597 484 987. |
| 21 | 🔴 `reject malformed expressions` | `+` seul lève une `NoSuchElementException`. |
| 22 | 🟢 `report missing operands and leftover values` | Messages explicites. |

## Solution

```kotlin
interface Visitor<R> {
    fun visit(operand: Operand): R
    fun visit(operation: Operation): R
}

val tree = Mathematical.parse("3 6 -6 * +")
tree.accept(RpnPrinter)          // "3 6 -6 × +"
tree.accept(InfixPrinter)        // "3 + (6 × -6)"
tree.accept(MinimalInfixPrinter) // "3 + 6 × -6"
tree.accept(Evaluator)           // -33
```

En Kotlin, un `sealed interface` et un `when` auraient suffi. J'ai gardé le Visiteur explicite, puisque c'est l'objet du kata : chaque nouveau traitement (étapes 10, 12, 14) a été une nouvelle classe, sans modifier l'arbre.

## Ce que j'en retiens

Les parenthèses minimales se ramènent à deux notions : la priorité, et le **sens de regroupement** à priorité égale. Le passage d'un booléen à trois valeurs (étape 18) a été imposé par la puissance, qui se regroupe à droite.

## Lancer les tests

```bash
./gradlew :katas:mathematical-ast:test
git log --reverse --format='%s%n%b' strict-tdd-start.. -- katas/mathematical-ast   # l'historique TDD
```
