# Code Cracker

🇬🇧 [English version](README.en.md) · Énoncé : [codingdojo.org/kata/CodeCracker](https://codingdojo.org/kata/CodeCracker/)

## Le kata

À partir d'une clé de déchiffrement (une correspondance alphabet → symboles), écrire un programme qui déchiffre n'importe quel message, puis un programme qui chiffre.

```
alphabet  a b c d e f g h i j k l m n o p q r s t u v w x y z
clé       ! ) " ( £ * % & > < @ a b c d e f g h i j k l m n o
```

## Démarche TDD

🔴 rouge · 🟢 vert · 🔵 refactor · 📌 test vert du premier coup

| # | Étape | Ce qui s'est passé |
|---|---|---|
| 1 | 🔴 `decrypt a single symbol` | Compilation impossible : `CodeCracker` n'existe pas. |
| 2 | 🟢 `fake the decryption of one symbol` | `return "a"`. |
| 3 | 🔴 `decrypt a word` | `&£aad` : attendu `"hello"`, obtenu `"a"`. |
| 4 | 🟢 `decrypt with the alphabet and key table` | `key.zip(alphabet).toMap()` et `getValue` sur chaque caractère. |
| 5 | 🔴 `keep characters outside the key` | `getValue` lève une exception sur la virgule. |
| 6 | 🟢 `let unknown characters through` | `table[c] ?: c`. Piège : `!` fait partie de la clé (il vaut `a`), il ne peut donc pas servir de ponctuation dans un message chiffré. |
| 7 | 🔴 `encrypt a message` | `encrypt` n'existe pas. |
| 8 | 🟢 `encrypt with the reversed table` | Deuxième table `alphabet.zip(key)`. |
| 9 | 📌 `round-trip a pangram` | `decrypt(encrypt(m)) == m` sur un pangramme. |
| 10 | 🔴 `reject keys with duplicate symbols` | `CodeCracker("ab", "xx")` est accepté, alors que `x` serait indéchiffrable. |
| 11 | 🟢 `require a key of unique symbols` | Deux `require` à la construction. |
| 12 | 🔵 `derive decryption from the encryption table` | Une seule table source, son inverse calculé ; extension `substitute` partagée par les deux sens. |

## Solution

Deux `Map<Char, Char>` construites une fois (chiffrement et son inverse). La classe accepte n'importe quel couple alphabet/clé, la clé du kata est une instance prête à l'emploi (`CodeCracker.kata`).

## Ce que j'en retiens

- Le test d'aller-retour vaut dix exemples, et l'unicité des symboles est l'invariant qui le rend toujours vrai.
- Lors d'une première version écrite sans TDD (retirée depuis), j'avais mal calculé un attendu à la main (`d` se chiffre en `(`, pas en `c`). En voyant chaque test échouer pour la bonne raison avant d'écrire le code, ce genre d'erreur se repère tout de suite.

## Lancer les tests

```bash
./gradlew :katas:code-cracker:test
git log --reverse --format='%s%n%b' strict-tdd-start.. -- katas/code-cracker   # l'historique TDD
```
