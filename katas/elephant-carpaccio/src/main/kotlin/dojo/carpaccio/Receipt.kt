// Copyright (c) 2026 Riadh MNASRI. Licensed under the MIT License.
package dojo.carpaccio

import java.math.BigDecimal

data class Item(val label: String, val quantity: Int, val unitPrice: BigDecimal) {
    val total: BigDecimal get() = unitPrice * BigDecimal(quantity)
}

class Receipt(private val items: List<Item>, private val state: String) {
    val totalWithoutTaxes: BigDecimal get() = items.fold(BigDecimal.ZERO) { sum, item -> sum + item.total }

    /** Le taux du palier le plus haut dépassé par le total hors taxes. */
    val discountRate: BigDecimal get() =
        DISCOUNTS.lastOrNull { (threshold, _) -> totalWithoutTaxes > threshold }?.second ?: BigDecimal.ZERO

    val taxRate: BigDecimal get() = TAX_RATES.getValue(state)

    val discount: BigDecimal get() = percentOf(totalWithoutTaxes, discountRate)

    val tax: BigDecimal get() = percentOf(totalWithoutTaxes - discount, taxRate)

    val totalPrice: BigDecimal get() = totalWithoutTaxes - discount + tax

    private fun percentOf(amount: BigDecimal, rate: BigDecimal) =
        (amount * rate).divide(BigDecimal(100), 2, java.math.RoundingMode.HALF_UP)

    private companion object {
        val DISCOUNTS = listOf(1_000 to "3", 5_000 to "5", 7_000 to "7", 10_000 to "10", 50_000 to "15")
            .map { (threshold, rate) -> BigDecimal(threshold) to BigDecimal(rate) }

        val TAX_RATES = mapOf("UT" to "6.85", "NV" to "8.00", "TX" to "6.25", "AL" to "4.00", "CA" to "8.25")
            .mapValues { BigDecimal(it.value) }
    }
}
