# Hello

🇬🇧 [English version](README.en.md) · Énoncé : [codingdojo.org/kata/Hello](https://codingdojo.org/kata/Hello/)

## Le kata

Afficher « Hello, World! ». Le kata sert surtout à vérifier que l'environnement fonctionne et à introduire les doublures de test : l'affichage doit être bouchonné.

## Démarche TDD

1. **Le seul test** : « quand je salue, l'affichage reçoit `Hello, World!` ». Écrire ce test oblige à décider *où* va le message. Plutôt que de capturer `System.out`, on introduit un port `Display`.
2. Pour le faire passer : `Greeter` reçoit un `Display` et lui envoie le message.
3. Le `main` branche le vrai affichage (`::println`) : c'est la seule ligne non testée, et elle est triviale.

## Solution

- `Display` est une `fun interface`, ce qui permet de passer `::println` directement.
- Le test utilise une doublure *écrite à la main* (`RecordingDisplay`) plutôt qu'un framework de mock : pour une interface à une méthode, c'est plus lisible.

## Ce que j'en retiens

Même sur un programme d'une ligne, le TDD pousse à séparer la logique (quoi dire) de l'effet de bord (où l'écrire). C'est l'architecture hexagonale en miniature.

## Lancer les tests

```bash
./gradlew :katas:hello:test
```
