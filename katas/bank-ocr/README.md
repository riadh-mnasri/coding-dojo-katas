# Bank OCR

🇬🇧 [English version](README.en.md) · Énoncé : [codingdojo.org/kata/BankOCR](https://codingdojo.org/kata/BankOCR/)

## Le kata

Une machine scanne des documents et produit des numéros de compte dessinés avec des `|` et des `_`, 9 chiffres sur 3 lignes (plus une ligne blanche) :

```
    _  _     _  _  _  _  _
  | _| _||_||_ |_   ||_||_|
  ||_  _|  | _||_|  ||_| _|
```

1. lire ces dessins ;
2. valider la clé : `(d1 + 2×d2 + ... + 9×d9) mod 11 = 0`, où d1 est le chiffre **le plus à droite** ;
3. produire un rapport : `ILL` si un chiffre est illisible (remplacé par `?`), `ERR` si la clé est fausse ;
4. quand c'est `ILL` ou `ERR`, essayer de corriger en ajoutant ou retirant **un seul trait** : une seule correction valide → on la prend ; plusieurs → `AMB` avec la liste ; aucune → `ILL`.

## Les données de test

Les grilles de l'énoncé ont des espaces qui comptent (et des lignes entièrement blanches). Plutôt que de les recopier à la main, je les ai extraites de la source de l'énoncé vers trois fichiers de ressources (`use-case-1.txt`, `-3`, `-4`), lus tels quels par les tests paramétrés.

## Démarche TDD

🔴 rouge · 🟢 vert · 🔵 refactor · 📌 test vert du premier coup

| # | Étape | Ce qui s'est passé |
|---|---|---|
| 1 | 🔴 `read an entry of zeros, from the kata fixtures` | Compilation impossible. |
| 2 | 🟢 `cut an entry into 3x3 cells and recognise zero` | Découpage en cellules de 3 × 3, table de reconnaissance limitée au 0. |
| 3 | 🔴 `read every entry of use case 1` | Les dix autres entrées : cellules inconnues. |
| 4 | 🟢 `recognise the ten digits` | La table complète, écrite sur 3 lignes par chiffre comme le conseille l'énoncé, pour qu'on **voie** les chiffres dans le code. |
| 5 | 🔴 `validate account numbers with the checksum` | Story 2 : `isValid` n'existe pas. |
| 6 | 🟢 `compute the checksum with reversed positions` | Le piège signalé par l'énoncé : les positions sont comptées depuis la droite, d'où le `reversed()`. |
| 7 | 🔴 `report illegible and erroneous numbers` | Story 3 : `report` n'existe pas. |
| 8 | 🟢 `mark illegible digits and report ILL or ERR` | Une cellule inconnue devient `?`. |
| 9 | 🔴 `guess numbers from one missing or extra stroke` | Story 4 : les 12 cas de l'énoncé, dont les `AMB`. |
| 10 | 🟢 `try every one-stroke change and keep the valid numbers` | Pour chaque cellule, les chiffres dont le dessin diffère d'un seul caractère (ce ne peut être qu'un trait ajouté ou retiré, puisque `_` et `|` ont des places fixes), puis on garde les numéros valides. Le rapport de la story 3 est conservé à part, car la story 4 change la réponse pour les mêmes entrées. |
| 11 | 🔴 `report a whole file, one account per line` | `reportFile` n'existe pas. |
| 12 | 🟢 `report every entry of a file` | Le fichier est découpé en blocs de 4 lignes. |

## Solution

```kotlin
val guesses = cells.indices
    .flatMap { position -> oneStrokeAway(cells[position]).map { account.replaceRange(position, position + 1, "$it") } }
    .filter { '?' !in it && isValid(it) }
    .distinct().sorted()
```

## Ce que j'en retiens

- Les « gotchas » de l'énoncé (positions inversées de la clé, chiffres corrigeables non listés, `?` à corriger aussi) sont tous couverts par les données d'origine, d'où l'intérêt de les avoir extraites telles quelles plutôt que réécrites.
- Avec une table de dessins, « un trait de différence » devient « un caractère de différence » : la story 4, réputée difficile, tient en une dizaine de lignes.

## Lancer les tests

```bash
./gradlew :katas:bank-ocr:test
git log --reverse --format='%s%n%b' strict-tdd-start.. -- katas/bank-ocr   # l'historique TDD
```
