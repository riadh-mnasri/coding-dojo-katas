# FizzBuzz

🇬🇧 [English version](README.en.md) · Énoncé : [codingdojo.org/kata/FizzBuzz](https://codingdojo.org/kata/FizzBuzz/)

## Le kata

Afficher les nombres de 1 à 100, en remplaçant les multiples de 3 par `Fizz`, ceux de 5 par `Buzz` et ceux de 3 et 5 par `FizzBuzz`.

**Étape 2** : un nombre est aussi `Fizz` s'il contient un 3, et `Buzz` s'il contient un 5.

## Démarche TDD

🔴 rouge · 🟢 vert · 🔵 refactor · 📌 test vert du premier coup

| # | Étape | Ce qui s'est passé |
|---|---|---|
| 1 | 🔴 `say 1 for 1` | Compilation impossible : `FizzBuzz` n'existe pas. |
| 2 | 🟢 `fake the answer for 1` | `return "1"` : on fait passer le test le plus simplement possible. |
| 3 | 🔴 `say 2 for 2` | Attendu `"2"`, obtenu `"1"` : la valeur en dur est démasquée. |
| 4 | 🟢 `say the number itself` | `number.toString()`. |
| 5 | 🔴 `say Fizz for 3` | Attendu `"Fizz"`, obtenu `"3"`. |
| 6 | 🟢 `say Fizz for multiples of three` | Implémentation évidente `number % 3 == 0` plutôt qu'un `== 3` artificiel. |
| 7 | 🔴 `say Buzz for 5` | Attendu `"Buzz"`, obtenu `"5"`. |
| 8 | 🟢 `say Buzz for multiples of five` | Une deuxième branche dans un `when`. |
| 9 | 🔴 `say FizzBuzz for 15` | Attendu `"FizzBuzz"`, obtenu `"Fizz"` : la première branche gagne. |
| 10 | 🟢 `concatenate Fizz and Buzz` | Au lieu d'ajouter un cas `% 15`, on concatène les deux mots et on retombe sur le nombre si rien ne s'applique (`ifEmpty`). |
| 11 | 🔵 `turn both conditions into rules` | Les deux conditions ont la même forme : elles deviennent des `Rule` (`wordFor(n): String?`) dans une liste. |
| 12 | 🔴 `list the answers from 1 to 100` | `sequence()` n'existe pas. |
| 13 | 🟢 `list the answers from 1 to 100` | Un `map` sur `1..100`. |
| 14 | 🔵 `inject the rules to prepare stage 2` | Refactoring préparatoire : l'`object` devient une classe qui reçoit ses règles, avec une configuration `classic`. Les tests existants passent par `FizzBuzz.classic`. |
| 15 | 🔴 `say Fizz for 13 in stage 2` | La configuration `stageTwo` n'existe pas. |
| 16 | 🟢 `add a rule for numbers containing a digit` | Nouvelle fabrique `divisibleByOrContains`, appliquée au 3 seulement. |
| 17 | 🔴 `say Buzz for 52 in stage 2` | Attendu `"Buzz"`, obtenu `"52"`. |
| 18 | 🟢 `apply the containing rule to 5 as well` | Même fabrique pour le 5. |
| 19 | 📌 `combine stage 2 rules` | `53` et `35` donnent `FizzBuzz` sans rien changer : la concaténation de l'étape 10 fait le travail. |

## Solution

```kotlin
class FizzBuzz(private val rules: List<Rule>) {
    fun say(number: Int): String =
        rules.mapNotNull { it.wordFor(number) }.joinToString("").ifEmpty { number.toString() }
}

val classic = FizzBuzz(listOf(divisibleBy(3, "Fizz"), divisibleBy(5, "Buzz")))
val stageTwo = FizzBuzz(listOf(divisibleByOrContains(3, "Fizz"), divisibleByOrContains(5, "Buzz")))
```

- L'ordre de la liste fixe l'ordre des mots (`Fizz` avant `Buzz`).
- Ajouter `Whizz` pour 7 ne demande aucune modification de la classe.

## Ce que j'en retiens

Le test 15 est le moment clé : un `if (n % 15 == 0)` aurait marché, mais la concaténation a rendu le cas « 3 et 5 » gratuit, et c'est encore elle qui fait passer le dernier test de l'étape 2 sans code. Le refactoring préparatoire (étape 14) illustre le conseil de Kent Beck : « rendre le changement facile, puis faire le changement facile ».

## Lancer les tests

```bash
./gradlew :katas:fizz-buzz:test
git log --reverse --format='%s%n%b' strict-tdd-start.. -- katas/fizz-buzz   # l'historique TDD
```
