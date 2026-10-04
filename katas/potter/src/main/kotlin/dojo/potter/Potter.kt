// Copyright (c) 2026 Riadh MNASRI. Licensed under the MIT License.
package dojo.potter

import java.math.BigDecimal

/**
 * Prix d'un panier de livres Harry Potter, avec la meilleure remise possible.
 *
 * Seul compte le nombre d'exemplaires de chaque titre (trié : quels titres, peu importe).
 * Pour un panier donné, on essaie chaque taille de lot possible, formé avec les titres les plus nombreux,
 * et on garde le moins cher ; les sous-paniers déjà calculés sont mémorisés.
 */
object Potter {
    private val BOOK = BigDecimal(8)
    private val DISCOUNTS = mapOf(1 to "0", 2 to "0.05", 3 to "0.10", 4 to "0.20", 5 to "0.25").mapValues { BigDecimal(it.value) }

    fun price(books: List<Int>): BigDecimal =
        cheapest(books.groupingBy { it }.eachCount().values.sortedDescending(), mutableMapOf())

    private fun cheapest(copies: List<Int>, known: MutableMap<List<Int>, BigDecimal>): BigDecimal {
        if (copies.isEmpty()) return BigDecimal.ZERO
        known[copies]?.let { return it }
        val best = (1..copies.size).minOf { size ->
            val rest = copies.mapIndexed { index, count -> if (index < size) count - 1 else count }
                .filter { it > 0 }
                .sortedDescending()
            setPrice(size) + cheapest(rest, known)
        }
        known[copies] = best
        return best
    }

    private fun setPrice(size: Int): BigDecimal = BOOK * BigDecimal(size) * (BigDecimal.ONE - DISCOUNTS.getValue(size))
}
