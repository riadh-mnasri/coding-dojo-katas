# FooBarQix

🇬🇧 [English version](README.en.md) · Énoncé : [codingdojo.org/kata/FooBarQix](https://codingdojo.org/kata/FooBarQix/)

## Le kata

`compute(String): String` applique, dans cet ordre :

- divisible par 3, 5, 7 → `Foo`, `Bar`, `Qix` ;
- puis, pour chaque chiffre 3, 5, 7 du nombre et dans l'ordre des chiffres → `Foo`, `Bar`, `Qix`.

**Étape 2** : chaque 0 laisse une trace `*` (`101 → 1*1`, `105 → FooBarQix*Bar`).

## Démarche TDD

🔴 rouge · 🟢 vert · 🔵 refactor · 📌 test vert du premier coup

| # | Étape | Ce qui s'est passé |
|---|---|---|
| 1 | 🔴 `keep a number no rule applies to` | Compilation impossible : `FooBarQix` n'existe pas. |
| 2 | 🟢 `return the number as is` | `return input`. |
| 3 | 🔴 `say Foo for 6` | Attendu `"Foo"`, obtenu `"6"`. |
| 4 | 🟢 `say Foo for multiples of 3` | Un `if`. |
| 5 | 🔴 `say Bar for 10` | Attendu `"Bar"`, obtenu `"10"`. |
| 6 | 🟢 `say Bar for multiples of 5` | Concaténation de `foo + bar`, repli sur le nombre si vide (leçon retenue de FizzBuzz). |
| 7 | 🔴 `add Foo for each digit 3` | `13` : attendu `"Foo"`, obtenu `"13"`. |
| 8 | 🟢 `add Foo for each digit 3` | Un filtre sur les chiffres `3`. |
| 9 | 🔴 `translate digits 3 and 5 in their order` | `53` : attendu `"BarFoo"`, obtenu `"Foo"`. |
| 10 | 🟢 `translate digits 3 and 5 in their order` | Une table `chiffre → mot` parcourue dans l'ordre des chiffres. |
| 11 | 🔵 `drive divisors and digits from one table` | La même table ordonnée sert aussi pour les diviseurs : plus aucun `if`. |
| 12 | 🔴 `handle 7 as Qix` | `7` : attendu `"QixQix"`, obtenu `"7"`. |
| 13 | 🟢 `add Qix for 7` | **Une seule ligne** : l'entrée `'7' to "Qix"` dans la table. Le refactoring 11 a payé tout de suite. |
| 14 | 📌 `check every example of step 1` | Les 16 exemples de l'énoncé passent. |
| 15 | 🔵 `name the step 1 behaviour to prepare step 2` | Refactoring préparatoire : `FooBarQix.step1` devient une configuration nommée. Il le faut car l'étape 2 change le résultat de `10` (`Bar` devient `Bar*`) : les deux comportements doivent coexister. |
| 16 | 🔴 `trace zeros of 101 in step 2` | `step2` n'existe pas. |
| 17 | 🟢 `replace zeros with stars when no rule applies` | Le repli remplace les `0` par `*` en étape 2. |
| 18 | 🔴 `interleave zero traces with digit words` | `303` : attendu `"FooFoo*Foo"`, obtenu `"FooFooFoo"`. |
| 19 | 🟢 `keep zero traces among the words` | Le `0` produit `*` dans la partie chiffres. Piège : pour `101`, le résultat serait alors `"*"` ; on ne garde le résultat que s'il contient autre chose que des `*`. |
| 20 | 🔵 `name the zero trace concepts` | `zeroTrace`, `withZeroTraces`, `hasWord` : la règle se lit dans le code. |
| 21 | 📌 `check every example of step 2` | `10 → Bar*`, `101`, `303`, `105`, `10101` passent. |

## Solution

```kotlin
fun compute(input: String): String {
    val number = input.toInt()
    val fromDivisors = WORDS.filterKeys { number % it.digitToInt() == 0 }.values.joinToString("")
    val fromDigits = input.mapNotNull { digit -> WORDS[digit] ?: zeroTrace(digit) }.joinToString("")
    val result = fromDivisors + fromDigits
    return if (result.hasWord()) result else withZeroTraces(input)
}
```

Une seule table ordonnée `3 → Foo, 5 → Bar, 7 → Qix` sert pour les diviseurs et pour les chiffres. Deux configurations, `step1` et `step2`, exposent les deux versions de la règle métier.

## Ce que j'en retiens

- Le refactoring de l'étape 11 a transformé l'arrivée de `Qix` en ajout de donnée.
- L'étape 2 contredit un exemple de l'étape 1 (`10`). Plutôt que de modifier un test vert, j'ai rendu explicites les deux versions : c'est ce qui se passe avec une vraie demande d'évolution.

## Lancer les tests

```bash
./gradlew :katas:foo-bar-qix:test
git log --reverse --format='%s%n%b' strict-tdd-start.. -- katas/foo-bar-qix   # l'historique TDD
```
