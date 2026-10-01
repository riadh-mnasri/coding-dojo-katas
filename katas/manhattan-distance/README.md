# Manhattan Distance

🇬🇧 [English version](README.en.md) · Énoncé : [codingdojo.org/kata/manhattan-distance](https://codingdojo.org/kata/manhattan-distance/)

## Le kata

Écrire `manhattanDistance(Point, Point)` : la distance quand on ne se déplace qu'à l'horizontale et à la verticale, comme dans les rues de Manhattan.

Contraintes : `Point` est immuable, sans getter, sans setter, sans propriété publique.

## Démarche TDD

🔴 rouge · 🟢 vert · 🔵 refactor · 📌 test vert du premier coup

| # | Étape | Ce qui s'est passé |
|---|---|---|
| 1 | 🔴 `measure zero between a point and itself` | Compilation impossible : ni `Point` ni `manhattanDistance`. |
| 2 | 🟢 `return zero for now` | `Point(private val x, private val y)` et `return 0`. |
| 3 | 🔴 `measure a horizontal move` | Attendu 3, obtenu 0. |
| 4 | 🟢 `let the point measure the horizontal gap` | **Le moment clé** : la fonction ne peut pas lire `x`, qui est privé. Le calcul va donc dans `Point.distanceTo(other)`, et la fonction de l'énoncé délègue. En Kotlin, `private` est visible des autres instances de la même classe : `other.x` reste accessible dans `Point`. |
| 5 | 🔴 `measure a vertical move` | Attendu 2, obtenu 0. |
| 6 | 🟢 `add the vertical gap` | `+ abs(y - other.y)`. |
| 7 | 📌 `check the kata examples and symmetry` | Les exemples de l'énoncé et un cas symétrique avec coordonnées négatives passent. |
| 8 | 🔴 `compare points by value` | `Point(2, 3) != Point(2, 3)` : égalité par référence. |
| 9 | 🟢 `give points value semantics without exposing state` | `equals`, `hashCode` et `toString` écrits à la main. Une `data class` aurait été plus courte, mais elle génère `component1()`, `component2()` et `copy()`, ce qui revient à exposer l'état. |

## Solution

```kotlin
class Point(private val x: Int, private val y: Int) {
    fun distanceTo(other: Point): Int = abs(x - other.x) + abs(y - other.y)
    // equals / hashCode / toString écrits à la main
}

fun manhattanDistance(from: Point, to: Point): Int = from.distanceTo(to)
```

## Ce que j'en retiens

Interdire les getters pousse vers *Tell, don't ask* : le comportement va là où sont les données. Le test de l'étape 3 l'a imposé tout seul, sans décision de conception préalable.

## Lancer les tests

```bash
./gradlew :katas:manhattan-distance:test
git log --reverse --format='%s%n%b' strict-tdd-start.. -- katas/manhattan-distance   # l'historique TDD
```
