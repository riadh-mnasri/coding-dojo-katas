// Copyright (c) 2026 Riadh MNASRI. Licensed under the MIT License.
package dojo.potter

import java.math.BigDecimal

object Potter {
    private val BOOK = BigDecimal(8)
    private val DISCOUNTS = mapOf(1 to "0", 2 to "0.05", 3 to "0.10", 4 to "0.20", 5 to "0.25").mapValues { BigDecimal(it.value) }

    fun price(books: List<Int>): BigDecimal {
        val copies = books.groupingBy { it }.eachCount().values.toMutableList()
        var total = BigDecimal.ZERO
        while (copies.any { it > 0 }) {
            val set = copies.count { it > 0 }
            total += setPrice(set)
            copies.replaceAll { if (it > 0) it - 1 else 0 }
        }
        return total
    }

    private fun setPrice(size: Int): BigDecimal =
        if (size == 0) BigDecimal.ZERO else BOOK * BigDecimal(size) * (BigDecimal.ONE - DISCOUNTS.getValue(size))
}
