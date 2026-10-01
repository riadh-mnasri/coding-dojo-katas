# Diamond

🇬🇧 [English version](README.en.md) · Énoncé : [codingdojo.org/kata/Diamond](https://codingdojo.org/kata/Diamond/)

## Le kata

À partir d'une lettre, afficher un losange qui commence par `A` et dont la lettre donnée forme la ligne la plus large :

```
  A
 B B
C   C
 B B
  A
```

## Démarche TDD

🔴 rouge · 🟢 vert · 🔵 refactor · 📌 test vert du premier coup

Ce kata est connu pour son piège : après `A` et `B`, le test `C` demande tout l'algorithme d'un coup. J'ai suivi l'approche des **tests recyclés** de Seb Rose : écrire des propriétés vraies pour toutes les lettres de `A` à `Z`, chacune forçant une seule petite décision, et garder les exemples pour la fin.

| # | Étape | Ce qui s'est passé |
|---|---|---|
| 1 | 🔴 `print A for A` | Compilation impossible : `Diamond` n'existe pas. |
| 2 | 🟢 `fake the A diamond` | `return "A"`. |
| 3 | 🔴 `have 2n+1 rows` | Propriété sur toutes les lettres : pour `B`, attendu 3 lignes, obtenu 1. |
| 4 | 🟢 `one row per letter, there and back` | Les lettres de `A` à la lettre donnée, puis le miroir sans répéter la ligne du milieu (`dropLast(1).reversed()`). Chaque ligne ne contient encore que sa lettre. |
| 5 | 📌 `go from A to the letter and back` | Vert immédiatement : l'étape 4 a déjà fixé l'ordre des lettres. |
| 6 | 🔴 `be as wide as it is high` | Pour `B`, largeur maximale 1 au lieu de 3. |
| 7 | 🟢 `write each letter twice with inner spaces` | `lettre + (2 × rang - 1) espaces + lettre`, sauf pour `A`. |
| 8 | 🔴 `be horizontally symmetric` | Une fois complétées à droite, les lignes ne sont pas symétriques : `"A  "` au lieu de `"  A"`. Il manque la marge extérieure. |
| 9 | 🟢 `pad each row with outer spaces` | `taille - rang` espaces devant chaque ligne. |
| 10 | 📌 `never end a row with a space` | Vert : on n'a jamais ajouté d'espace à droite. |
| 11 | 📌 `match the B and C examples` | Les exemples, écrits en dernier, passent sans changement. Ils servent de documentation lisible. |
| 12 | 🔴 `reject anything but a capital letter` | `'a'`, `'1'`, `'@'` ne lèvent pas d'exception. |
| 13 | 🟢 `accept only capital letters` | Un `require`. |

## Solution

```kotlin
fun of(widest: Char): String {
    val size = widest - 'A'
    val topHalf = ('A'..widest).map { row(it, size) }
    return (topHalf + topHalf.dropLast(1).reversed()).joinToString("\n")
}

private fun row(letter: Char, size: Int): String {
    val index = letter - 'A'
    val outer = " ".repeat(size - index)
    return if (index == 0) "$outer$letter" else "$outer$letter${" ".repeat(2 * index - 1)}$letter"
}
```

## Ce que j'en retiens

Les tests de propriétés transforment le « saut » entre l'exemple `A` et l'exemple `C` en une série de petites décisions : le nombre de lignes, puis la largeur, puis la symétrie. Les exemples deviennent des 📌 qui confirment le design au lieu de le dicter.

## Lancer les tests

```bash
./gradlew :katas:diamond:test
git log --reverse --format='%s%n%b' strict-tdd-start.. -- katas/diamond   # l'historique TDD
```
