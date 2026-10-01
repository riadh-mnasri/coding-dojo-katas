# FizzBuzz

🇬🇧 [English version](README.en.md) · Énoncé : [codingdojo.org/kata/FizzBuzz](https://codingdojo.org/kata/FizzBuzz/)

## Le kata

Afficher les nombres de 1 à 100, en remplaçant les multiples de 3 par `Fizz`, ceux de 5 par `Buzz` et ceux de 3 et 5 par `FizzBuzz`.

**Étape 2** : un nombre est aussi `Fizz` s'il contient un 3, et `Buzz` s'il contient un 5.

## Démarche TDD

1. `1 → "1"` et `2 → "2"` : la fonction rend le nombre, `toString()` suffit.
2. `3 → Fizz` puis 6 et 9 : premier `if (n % 3 == 0)`.
3. `5 → Buzz` : deuxième `if`, symétrique du premier.
4. `15 → FizzBuzz` : au lieu d'ajouter un troisième `if`, on **concatène** les mots des règles qui s'appliquent. Le cas 15 tombe tout seul.
5. *Refactoring* : les deux `if` ont la même forme, on les extrait en `Rule` (interface fonctionnelle `wordFor(n): String?`). `FizzBuzz` ne connaît plus 3 ni 5, il reçoit une liste de règles.
6. Séquence de 1 à 100 : simple `map` sur la plage.
7. **Étape 2** : grâce au refactoring, la nouvelle exigence est une nouvelle fabrique de règle (`divisibleByOrContains`) et une nouvelle configuration (`stageTwo`). Zéro modification du cœur.

## Solution

```kotlin
fun say(number: Int): String =
    rules.mapNotNull { it.wordFor(number) }.joinToString("").ifEmpty { number.toString() }
```

- L'ordre de la liste de règles fixe l'ordre des mots (`Fizz` avant `Buzz`).
- `ifEmpty` évite le cas particulier « aucune règle ».
- Ajouter `Whizz` pour 7 se fait sans toucher à la classe (principe ouvert/fermé).

## Ce que j'en retiens

Le test 15 est le moment clé : soit on ajoute un `if (n % 15 == 0)` (ça marche, mais ça duplique), soit on change de modèle vers une composition de règles. L'étape 2 valide a posteriori ce choix.

## Lancer les tests

```bash
./gradlew :katas:fizz-buzz:test
```
