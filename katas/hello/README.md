# Hello

🇬🇧 [English version](README.en.md) · Énoncé : [codingdojo.org/kata/Hello](https://codingdojo.org/kata/Hello/)

## Le kata

Afficher « Hello, World! ». Le kata sert à vérifier que l'environnement fonctionne et à introduire les doublures de test : l'affichage doit être bouchonné.

## Démarche TDD

| # | Étape | Ce qui s'est passé |
|---|---|---|
| 1 | 🔴 `expect the greeting to reach the display` | Le test décide *où* va le message : un port `Display` reçu par `Greeter`. Échec de compilation : `Greeter` n'existe pas. |
| 2 | 🟢 `send the greeting to a display port` | `Display` en `fun interface` (le test passe une lambda), `Greeter.greet()` lui envoie le texte. |
| 3 | 🔵 `wire the console as the real display` | Le `main` branche `::println` comme vrai affichage. C'est la seule ligne non testée, et elle ne contient aucune logique. |

## Solution

```kotlin
fun interface Display {
    fun show(message: String)
}

class Greeter(private val display: Display) {
    fun greet() = display.show("Hello, World!")
}
```

Le test n'utilise pas de framework de mock : pour une interface à une méthode, une lambda qui enregistre les messages suffit et se lit mieux.

## Ce que j'en retiens

Même sur un programme d'une ligne, écrire le test en premier oblige à séparer la logique (quoi dire) de l'effet de bord (où l'écrire). C'est l'architecture hexagonale en miniature.

## Lancer les tests

```bash
./gradlew :katas:hello:test
git log --reverse --format='%s%n%b' strict-tdd-start.. -- katas/hello   # l'historique TDD
```
