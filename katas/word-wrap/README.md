# Word Wrap

🇬🇧 [English version](README.en.md) · Énoncé : [codingdojo.org/kata/WordWrap](https://codingdojo.org/kata/WordWrap/)

## Le kata

`Wrapper.wrap(text, column)` insère des retours à la ligne pour qu'aucune ligne ne dépasse `column` caractères, en coupant de préférence entre deux mots (le dernier espace de la ligne devient un retour à la ligne). Kata proposé par Robert C. Martin.

## Démarche TDD

🔴 rouge · 🟢 vert · 🔵 refactor · 📌 test vert du premier coup

| # | Étape | Ce qui s'est passé |
|---|---|---|
| 1 | 🔴 `wrap an empty text` | Compilation impossible. |
| 2 | 🟢 `return the text as is` | `return text`. |
| 3 | 🔴 `cut a word longer than the column` | `longword` en 4 : attendu `long\nword`. |
| 4 | 🟢 `cut recursively at the column` | Si le texte dépasse : les `column` premiers caractères, un retour, puis `wrap` du reste. La récursion apparaît dès ce test. |
| 5 | 🔴 `break at a space instead of inside a word` | `word word` en 6 donne `word w\nord`. |
| 6 | 🟢 `break at the last space before the column` | `lastIndexOf(' ', column - 1)` ; l'espace trouvé est remplacé par le retour. |
| 7 | 🔴 `break right after the column when a space sits there` | `word word` en 4 : l'espace est juste **après** la colonne, il n'est pas trouvé, et la sortie contient une ligne vide parasite. Le cas limite d'Uncle Bob. |
| 8 | 🟢 `look for a space up to the column itself` | Chercher jusqu'à l'indice `column` inclus : un espace à cet endroit est une coupure valide. |
| 9 | 🔵 `name the two ways of breaking a line` | Une fonction `breakAt(text, index, skip)` : couper sur un espace (on saute l'espace) ou au milieu d'un mot (on ne saute rien). |
| 10 | 📌 `wrap several lines and long words in sentences` | Phrases sur plusieurs lignes et mot trop long au milieu d'une phrase : verts. |
| 11 | 🔴 `reject a column below 1` | `StackOverflowError` : avec une colonne à 0, la récursion ne progresse jamais. |
| 12 | 🟢 `require a positive column` | Un `require` en entrée. |

## Solution

```kotlin
fun wrap(text: String, column: Int): String {
    require(column >= 1) { "The column must be at least 1, got $column" }
    if (text.length <= column) return text
    val space = text.lastIndexOf(' ', column)
    return if (space >= 0) breakAt(text, space, skip = 1, column) else breakAt(text, column, skip = 0, column)
}
```

## Ce que j'en retiens

Tout le kata tient dans le test 7 : un décalage de un dans la recherche de l'espace. Le test 11 rappelle qu'une fonction récursive doit toujours garantir qu'elle progresse.

## Lancer les tests

```bash
./gradlew :katas:word-wrap:test
git log --reverse --format='%s%n%b' strict-tdd-start.. -- katas/word-wrap   # l'historique TDD
```
