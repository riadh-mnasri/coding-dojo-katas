# Brainfuck

🇬🇧 [English version](README.en.md) · Énoncé : [codingdojo.org/kata/Brainfuck](https://codingdojo.org/kata/Brainfuck/)

## Le kata

Écrire un interpréteur Brainfuck qui exécute un programme et renvoie l'état de la mémoire : 30 000 octets, un pointeur, et huit commandes (`+ - > < . , [ ]`).

Contraintes supplémentaires :

- `<` sur la première case ramène à la dernière ;
- une case vaut de 0 à 255 (décrémenter 0 donne 255) ;
- les instructions doivent être faciles à **renommer** (syntaxe « OooWee ») ;
- il doit être facile d'**ajouter** une instruction, par exemple `!` qui saute à la fin de la mémoire.

## Démarche TDD

🔴 rouge · 🟢 vert · 🔵 refactor · 📌 test vert du premier coup

| # | Étape | Ce qui s'est passé |
|---|---|---|
| 1 | 🔴 `leave memory blank for an empty program` | Compilation impossible. |
| 2 | 🟢 `give a blank machine` | Une `Machine` avec 30 000 cases à zéro. |
| 3 | 🔴 `increment and decrement the current cell` | Rien ne change. |
| 4 | 🟢 `increment and decrement the current cell` | Un `when` sur le caractère. |
| 5 | 🔴 `wrap cell values between 0 and 255` | `-` donne -1. |
| 6 | 🟢 `wrap cell values modulo 256` | `Math.floorMod` dans `Machine.add`. |
| 7 | 🔴 `move the pointer and wrap it at the left edge` | Pas de pointeur. |
| 8 | 🟢 `move the pointer around a circular memory` | `floorMod` aussi pour le pointeur : la mémoire est circulaire. |
| 9 | 🔴 `read input and write output as ASCII` | Ni entrée ni sortie. |
| 10 | 🟢 `read and write bytes as ASCII` | Une file d'entrée et un tampon de sortie dans la machine. |
| 11 | 🔴 `loop while the current cell is not zero` | Les crochets sont ignorés. |
| 12 | 🟢 `jump between matching brackets` | `forEach` devient une boucle avec un compteur d'instruction ; les paires de crochets sont précalculées avec une pile. |
| 13 | 📌 `skip a loop on zero, nest loops and print hello world` | Boucle sautée, boucles imbriquées et le classique « Hello World! ». |
| 14 | 🔴 `reject unbalanced brackets` | `NoSuchElementException` au lieu d'une erreur claire. |
| 15 | 🟢 `report unbalanced brackets` | Messages explicites pour un `]` ou un `[` orphelin. |
| 16 | 🔵 `separate the syntax from the instructions` | Le cœur du kata : une `Syntax` associe des **jetons** (caractère ou mot) à des `Instruction`. Le programme est d'abord découpé en instructions, puis exécuté. Les boucles restent des marqueurs gérés par l'interpréteur. |
| 17 | 🔴 `rename every instruction` | `renamed` n'existe pas. |
| 18 | 🟢 `rename tokens while keeping their instructions` | `Syntax.BRAINFUCK.renamed("+" to "Ooo", ...)` : un simple renommage des clés. Le découpage essaie les jetons les plus longs d'abord (`OooWee` avant `Ooo`). |
| 19 | 🔴 `add a jump-to-the-end instruction` | `with` n'existe pas. |
| 20 | 🟢 `extend a syntax with new instructions` | `Syntax.BRAINFUCK.with("!" to Instruction.Action { ... })`. |

## Solution

```kotlin
val oooWee = Syntax.BRAINFUCK.renamed("+" to "Ooo", "-" to "Wee", ">" to "OooWee", /* ... */)
val withJump = Syntax.BRAINFUCK.with("!" to Instruction.Action { it.pointer = it.memory.size - 1 })
Interpreter(withJump).run("!+>+")
```

- `Machine` : mémoire circulaire d'octets, pointeur, entrée et sortie.
- `Instruction` : une `Action` sur la machine, ou un marqueur de boucle.
- `Syntax` : jetons → instructions, avec `renamed` et `with`.
- `Interpreter` : découpe, apparie les boucles, exécute.

## Ce que j'en retiens

Les deux dernières exigences (renommer, étendre) sont des exigences de **conception**. Le refactoring de l'étape 16, fait au vert, les a rendues triviales : chacune n'a demandé qu'une fonction de deux lignes.

## Lancer les tests

```bash
./gradlew :katas:brainfuck:test
git log --reverse --format='%s%n%b' strict-tdd-start.. -- katas/brainfuck   # l'historique TDD
```
