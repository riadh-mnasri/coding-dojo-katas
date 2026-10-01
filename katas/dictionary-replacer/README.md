# Dictionary Replacer

🇬🇧 [English version](README.en.md) · Énoncé : [codingdojo.org/kata/DictionaryReplacer](https://codingdojo.org/kata/DictionaryReplacer/)

## Le kata

Écrire une méthode qui prend une chaîne et un dictionnaire, et remplace chaque clé entourée de `$` par sa valeur : `"$temp$ here comes the name $name$"` devient `"temporary here comes the name John Doe"`.

## Démarche TDD

🔴 rouge · 🟢 vert · 🔵 refactor · 📌 test vert du premier coup

| # | Étape | Ce qui s'est passé |
|---|---|---|
| 1 | 🔴 `keep an empty text empty` | Compilation impossible : `DictionaryReplacer` n'existe pas. |
| 2 | 🟢 `return the text unchanged` | `return text`. |
| 3 | 🔴 `replace a single placeholder` | Attendu `"temporary"`, obtenu `"$temp$"`. |
| 4 | 🟢 `replace each dictionary key wrapped in dollars` | Solution naïve : pour chaque entrée du dictionnaire, `replace("$clé$", valeur)`. |
| 5 | 📌 `replace several placeholders in a sentence` | L'exemple complet de l'énoncé passe avec la boucle. |
| 6 | 🔴 `never replace inside a replaced value` | Avec `a → "$b$"` et `b → "boom"`, la boucle remplace en cascade : `"boom"` au lieu de `"$b$"`. Le résultat dépend de l'ordre du dictionnaire, c'est un bug. |
| 7 | 🟢 `replace placeholders in a single pass over the text` | On parcourt **le texte** et non le dictionnaire : un seul `Regex.replace`. Chaque marqueur est remplacé une fois, quoi que contiennent les valeurs. |
| 8 | 🔴 `keep unknown placeholders` | `getValue` lève `NoSuchElementException` pour une clé absente. |
| 9 | 🟢 `leave unknown placeholders as they are` | `dictionary[clé] ?: match.value` : rien n'est perdu en silence. |
| 10 | 📌 `ignore lonely dollars and repeat known keys` | Un `$` isolé n'est pas un marqueur, une clé répétée est remplacée à chaque fois. |

## Solution

```kotlin
private val placeholder = Regex("""\$(\w+)\$""")

fun replace(text: String, dictionary: Map<String, String>): String =
    placeholder.replace(text) { match -> dictionary[match.groupValues[1]] ?: match.value }
```

## Ce que j'en retiens

La solution naïve passe tous les tests de l'énoncé. C'est le test du remplacement en cascade (étape 6) qui révèle qu'il faut itérer sur le texte et non sur le dictionnaire : un bon prochain test est souvent celui qui attaque une hypothèse implicite de l'implémentation.

## Lancer les tests

```bash
./gradlew :katas:dictionary-replacer:test
git log --reverse --format='%s%n%b' strict-tdd-start.. -- katas/dictionary-replacer   # l'historique TDD
```
