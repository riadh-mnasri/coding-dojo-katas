# Args

🇬🇧 [English version](README.en.md) · Énoncé : [codingdojo.org/kata/Args](https://codingdojo.org/kata/Args/)

## Le kata

Le parseur d'arguments du chapitre 14 de *Clean Code*. Un schéma décrit les drapeaux attendus et leur type ; le parseur vérifie les arguments et rend les valeurs typées :

```kotlin
val args = Args("l,p#,d*", listOf("-l", "-p", "8080", "-d", "/usr/logs"))
args.boolean('l')   // true
args.int('p')       // 8080
args.string('d')    // "/usr/logs"
```

Le format du schéma est laissé libre : j'ai repris celui d'Uncle Bob (`l` booléen, `p#` entier, `d*` chaîne) avec `[*]` et `[#]` pour les listes. Un drapeau absent prend une valeur par défaut, et chaque erreur doit être expliquée précisément.

## Démarche TDD

🔴 rouge · 🟢 vert · 🔵 refactor · 📌 test vert du premier coup

| # | Étape | Ce qui s'est passé |
|---|---|---|
| 1 | 🔴 `read a boolean flag` | Compilation impossible. |
| 2 | 🟢 `say every boolean flag is present` | `return true`. |
| 3 | 🔴 `default an absent boolean to false` | Démasque la valeur en dur. |
| 4 | 🟢 `find a flag among the arguments` | `"-l" in arguments`. |
| 5 | 🔴 `read an integer and a string value` | `int` et `string` n'existent pas. |
| 6 | 🟢 `parse values according to the schema` | Le schéma donne un code de type par drapeau ; on parcourt les arguments, la valeur est l'argument suivant. |
| 7 | 🔴 `give defaults for absent flags` | `NullPointerException` sur un entier absent. |
| 8 | 🟢 `default to 0 and empty string` | Valeurs par défaut. |
| 9 | 📌 `accept negative integers and any argument order` | `-p -3` : comme la valeur est lue par le drapeau qui l'attend, `-3` n'est jamais pris pour un drapeau. |
| 10 | 🔴 `explain exactly what is wrong` | Cinq erreurs attendues, avec leur message exact. |
| 11 | 🟢 `report unknown flags, missing and invalid values` | Une `ArgsException` et un message par cas. |
| 12 | 🔵 `give each value type its own marshaler` | Un `Marshaler` par type (valeur par défaut + lecture), une table code → marshaler. **Écart** : ce refactor a aussi introduit deux nouveaux contrôles (type inconnu dans le schéma, drapeau hors schéma), un comportement nouveau qui n'a rien à faire dans un refactor. |
| 13 | 🔴 `read lists of strings and integers` | `strings` et `ints` n'existent pas. |
| 14 | 🟢 `add list marshalers for strings and integers` | Un `ListMarshaler` qui délègue à un marshaler d'élément : `[*]` et `[#]` sont **deux lignes de la table**. **Écart** : j'y ai écrit aussi un message d'erreur qu'aucun test ne demandait. |
| 15 | 📌 `cover the schema errors added by the marshaler refactoring` | Rattrapage de l'écart de l'étape 12. |
| 16 | 📌 `cover the missing list error written ahead of its test` | Rattrapage de l'écart de l'étape 14. |

## Solution

```kotlin
val MARSHALERS: Map<String, Marshaler> = mapOf(
    "" to BooleanMarshaler,
    "#" to IntMarshaler,
    "*" to StringMarshaler,
    "[*]" to ListMarshaler(StringMarshaler),
    "[#]" to ListMarshaler(IntMarshaler),
)
```

Ajouter un type (un `double`, une date...) revient à écrire un marshaler et à l'ajouter à la table, sans toucher au parcours des arguments : c'est l'extensibilité demandée par l'énoncé.

## Ce que j'en retiens

Les trois tests 📌 de rattrapage (étapes 15 et 16) signalent le même travers : écrire un peu de « bon sens » au passage, sans test rouge préalable. Ce code n'est pas faux, mais il n'a pas été piloté par les tests, et je préfère que l'historique le montre.

## Lancer les tests

```bash
./gradlew :katas:args:test
git log --reverse --format='%s%n%b' strict-tdd-start.. -- katas/args   # l'historique TDD
```
