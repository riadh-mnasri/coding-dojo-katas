# Potter

🇬🇧 [English version](README.en.md) · Énoncé : [codingdojo.org/kata/Potter](https://codingdojo.org/kata/Potter/)

## Le kata

Un livre Harry Potter coûte 8 €. Des titres **différents** achetés ensemble forment un lot remisé : 5 % pour 2, 10 % pour 3, 20 % pour 4, 25 % pour 5. Calculer le prix de n'importe quel panier avec la meilleure remise possible.

Le piège : pour `2, 2, 2, 1, 1` exemplaires, deux lots de 4 (51,20 €) coûtent moins cher qu'un lot de 5 et un lot de 3 (51,60 €).

## Démarche TDD

🔴 rouge · 🟢 vert · 🔵 refactor · 📌 test vert du premier coup

| # | Étape | Ce qui s'est passé |
|---|---|---|
| 1 | 🔴 `price an empty basket at zero` | Compilation impossible. |
| 2 | 🟢 `price an empty basket` | `ZERO`. |
| 3 | 🔴 `price identical books at 8 euros each` | Attendu 8, obtenu 0. |
| 4 | 🟢 `charge 8 euros per book` | `8 × nombre de livres`. |
| 5 | 🔴 `discount sets of different books` | 2 titres différents : 16 au lieu de 15,20. |
| 6 | 🟢 `discount a set of different books` | Table des remises, appliquée si tous les livres sont différents. |
| 7 | 🔴 `split a basket into several sets` | `0, 0, 1` : 24 au lieu de 23,20. |
| 8 | 🟢 `split the basket greedily into the largest sets` | Algorithme glouton : on forme à chaque fois le plus grand lot possible. |
| 9 | 🔴 `prefer two sets of four over five and three` | L'exemple de l'énoncé : le glouton donne 51,60 au lieu de 51,20. **C'est le cœur du kata.** |
| 10 | 🟢 `search the cheapest split, memoized on the copy counts` | Plutôt qu'un correctif ad hoc (« remplacer 5 + 3 par 4 + 4 »), une recherche : pour chaque taille de lot possible, on prend un exemplaire des titres les plus nombreux, et on garde le découpage le moins cher. Seuls comptent les nombres d'exemplaires triés, ce qui permet de mémoriser les sous-paniers. |
| 11 | 📌 `price big baskets quickly and correctly` | 40 livres (10, 10, 10, 5, 5) : 256 €, calculé instantanément. |
| 12 | 🔵 `state the expected split in the big basket test` | Je n'avais pas vérifié ce 256 avant de l'écrire, et le commentaire du test était approximatif. Un oracle Python indépendant, qui essaie **toutes** les combinaisons de titres, confirme 256 € (10 lots de 4) et 51,20 €. |

## Solution

```kotlin
private fun cheapest(copies: List<Int>, known: MutableMap<List<Int>, BigDecimal>): BigDecimal {
    if (copies.isEmpty()) return BigDecimal.ZERO
    known[copies]?.let { return it }
    val best = (1..copies.size).minOf { size ->
        val rest = copies.mapIndexed { index, count -> if (index < size) count - 1 else count }
            .filter { it > 0 }.sortedDescending()
        setPrice(size) + cheapest(rest, known)
    }
    known[copies] = best
    return best
}
```

Former un lot avec les titres **les plus nombreux** suffit : c'est ce qui laisse le plus de possibilités pour la suite, et l'oracle exhaustif le confirme sur les exemples.

## Ce que j'en retiens

- Le glouton a été écrit et gardé tant qu'il suffisait ; c'est l'exemple de l'énoncé qui a justifié l'optimisation.
- Un test 📌 dont on n'a pas calculé l'attendu soi-même ne prouve rien : il fige ce que le code produit. L'oracle indépendant de l'étape 12 a rétabli la preuve.

## Lancer les tests

```bash
./gradlew :katas:potter:test
git log --reverse --format='%s%n%b' strict-tdd-start.. -- katas/potter   # l'historique TDD
```
