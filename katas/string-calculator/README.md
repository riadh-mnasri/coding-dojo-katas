# String Calculator

🇬🇧 [English version](README.en.md) · Énoncé : [codingdojo.org/kata/StringCalculator](https://codingdojo.org/kata/StringCalculator/)

## Le kata

`add(String): String` additionne des nombres décimaux séparés par `,` ou un saut de ligne, et renvoie le résultat **ou un message d'erreur précis** :

| Entrée | Sortie |
|---|---|
| `""` | `0` |
| `"1.1,2.2"` | `3.3` |
| `"175.2,\n35"` | `Number expected but '\n' found at position 6.` |
| `"1,3,"` | `Number expected but EOF found.` |
| `"//sep\n2sep3"` | `5` |
| `"//\|\n1\|2,3"` | `'\|' expected but ',' found at position 3.` |
| `"2,-4,-5"` | `Negative not allowed : -4, -5` |
| `"-1,,2"` | `Negative not allowed : -1` + saut de ligne + `Number expected but ',' found at position 3.` |

Puis : gérer les erreurs autrement qu'avec des chaînes en interne, et écrire `multiply` avec les mêmes règles.

## Démarche TDD

🔴 rouge · 🟢 vert · 🔵 refactor · 📌 test vert du premier coup

| # | Étape | Ce qui s'est passé |
|---|---|---|
| 1 | 🔴 `sum an empty input to 0` | Compilation impossible. |
| 2 | 🟢 `return 0 for an empty input` | `return "0"`. |
| 3 | 🔴 `sum one or two decimal numbers` | `1`, `1.1,2.2`, `2,3` donnent tous `0`. |
| 4 | 🟢 `sum comma separated decimals exactly` | `BigDecimal` plutôt que `Double` : `1.1 + 2.2` doit afficher `3.3`, pas `3.3000000000000003`. `stripTrailingZeros` affiche `5` et non `5.0`. |
| 5 | 📌 `sum any amount of numbers` | `split` gérait déjà n'importe quel nombre de valeurs. |
| 6 | 🔴 `accept newlines as separators` | `NumberFormatException`. |
| 7 | 🟢 `split on newlines too` | Un deuxième séparateur dans `split`. |
| 8 | 🔴 `report a separator where a number is expected` | `split` perd les positions : impossible de dire « position 6 ». |
| 9 | 🟢 `scan the input to locate unexpected separators` | **Changement de modèle** : on abandonne `split` pour un parcours caractère par caractère (`Regex.matchAt` pour lire un nombre à une position donnée). |
| 10 | 🔴 `refuse a trailing separator` | `StringIndexOutOfBoundsException` en fin de chaîne. |
| 11 | 🟢 `report EOF when a number is missing at the end` | Test de fin de texte avant de lire un nombre. |
| 12 | 🔴 `accept a custom separator` | `//` est lu comme un nombre manquant. |
| 13 | 🟢 `read a custom separator from the first line` | L'en-tête fixe les séparateurs ; les positions sont comptées **après** l'en-tête, comme dans l'exemple de l'énoncé. |
| 14 | 🔴 `report a wrong separator` | `NoSuchElementException` sur une virgule non déclarée. |
| 15 | 🟢 `report the expected separator` | Message `'|' expected but ',' found at position 3.` |
| 16 | 🔴 `refuse negative numbers` | Le `-` n'est pas reconnu comme début de nombre. |
| 17 | 🟢 `list the negative numbers refused` | Le motif accepte un signe, les négatifs sont collectés puis refusés ensemble. |
| 18 | 🔴 `report every error at once` | On s'arrête à la première erreur, le négatif n'est jamais signalé. |
| 19 | 🟢 `keep scanning after an error to report them all` | Les erreurs sont accumulées et l'analyse reprend au caractère suivant. |
| 20 | 🔵 `separate scanning, typed outcome and rendering` | Trois responsabilités : `Scanner` (lecture et positions), `compute` qui renvoie un `Outcome` typé (`Value` ou `Failure`), et `render` qui produit la chaîne. C'est l'étape « Errors management » de l'énoncé, version type somme. |
| 21 | 🔴 `multiply with the same rules` | `multiply` n'existe pas. |
| 22 | 🟢 `multiply through the same pipeline` | Une ligne : `compute(input, BigDecimal.ONE, BigDecimal::multiply)`. Choix : une entrée vide vaut 1, l'élément neutre. |
| 23 | 📌 `expose errors as a typed outcome internally` | Le test documente le contrat interne : une `Failure` avec la liste ordonnée des messages. |

## Solution

```kotlin
fun add(input: String): String = render(compute(input, BigDecimal.ZERO, BigDecimal::add))
fun multiply(input: String): String = render(compute(input, BigDecimal.ONE, BigDecimal::multiply))
```

- `Scanner` lit nombre, séparateur, nombre... et note chaque anomalie avec sa position, sans s'arrêter.
- `compute` ajoute la règle des négatifs et renvoie `Outcome.Value` ou `Outcome.Failure`.
- Seul `render` connaît le format texte demandé par l'énoncé.

## Ce que j'en retiens

L'étape 8 est le pivot : un `split` ne peut pas répondre à « où est l'erreur ? ». Changer de modèle tôt a rendu tous les messages suivants simples. Et sortir les erreurs du type `String` (étape 20) a rendu la multiplication triviale.

## Lancer les tests

```bash
./gradlew :katas:string-calculator:test
git log --reverse --format='%s%n%b' strict-tdd-start.. -- katas/string-calculator   # l'historique TDD
```
