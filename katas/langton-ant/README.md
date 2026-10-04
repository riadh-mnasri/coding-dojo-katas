# Langton Ant (la fourmi de Langton)

🇬🇧 [English version](README.en.md) · Énoncé : [codingdojo.org/kata/LangtonAnt](https://codingdojo.org/kata/LangtonAnt/)

## Le kata

Un automate cellulaire : sur un plan de cases blanches ou noires, une fourmi avance pas à pas.

- sur une case **blanche** : elle tourne à droite, la case devient noire, elle avance ;
- sur une case **noire** : elle tourne à gauche, la case devient blanche, elle avance.

**Extension** : une troisième couleur, avec un cycle blanc → noir → rouge → blanc ; sur une case rouge, la fourmi ne tourne pas.

## Démarche TDD

🔴 rouge · 🟢 vert · 🔵 refactor · 📌 test vert du premier coup

| # | Étape | Ce qui s'est passé |
|---|---|---|
| 1 | 🔴 `turn right on a white square` | Compilation impossible : ni `World`, ni `Ant`, ni `Color`. |
| 2 | 🟢 `turn right, flip to black and move on white` | Un plan infini représenté par une `Map` des cases déjà peintes (blanc par défaut). Seule la règle du blanc existe. |
| 3 | 🔴 `turn left on a black square` | Quatre pas ramènent la fourmi sur l'origine noire ; le cinquième doit tourner à gauche, la règle du blanc tourne à droite. |
| 4 | 🟢 `turn left and flip to white on black` | Un `if` sur la couleur. |
| 5 | 🔵 `describe the automaton as a table of rules` | Une `Rule` (comment tourner, quelle couleur peindre) par couleur ; le `World` reçoit sa table. |
| 6 | 🔴 `cycle through white, black and red` | `RED` et `THREE_COLORS` n'existent pas. (Mon premier jet de ce test ne vérifiait pas le passage sur une case rouge, et un commentaire était faux : je l'ai corrigé en amendant le commit rouge avant de le pousser, attendus calculés à la main sur 9 pas.) |
| 7 | 🟢 `add red with its circular rules` | Une couleur de plus et une nouvelle table de règles : **aucune ligne** du moteur ne change. |

## Solution

```kotlin
val THREE_COLORS = mapOf(
    Color.WHITE to Rule(Direction::right, Color.BLACK),
    Color.BLACK to Rule(Direction::left, Color.RED),
    Color.RED to Rule({ it }, Color.WHITE),
)

fun step() {
    val rule = rules.getValue(colorAt(ant.position))
    val direction = rule.turn(ant.direction)
    colors[ant.position] = rule.paint
    ant = Ant(ant.position.moved(direction), direction)
}
```

## Ce que j'en retiens

Le refactoring vers une table de règles a été fait **avant** de connaître l'extension, simplement parce que deux branches symétriques le suggéraient. L'extension a ensuite été une affaire de données.

## Lancer les tests

```bash
./gradlew :katas:langton-ant:test
git log --reverse --format='%s%n%b' strict-tdd-start.. -- katas/langton-ant   # l'historique TDD
```
