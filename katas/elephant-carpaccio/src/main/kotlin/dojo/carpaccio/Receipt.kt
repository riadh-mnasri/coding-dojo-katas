// Copyright (c) 2026 Riadh MNASRI. Licensed under the MIT License.
package dojo.carpaccio

import java.math.BigDecimal

data class Item(val label: String, val quantity: Int, val unitPrice: BigDecimal) {
    val total: BigDecimal get() = unitPrice * BigDecimal(quantity)
}

class Receipt(private val items: List<Item>, private val state: String) {
    init {
        require(items.isNotEmpty()) { "An order needs at least one item" }
        require(state in TAX_RATES) { "Unknown state '$state', known states are ${TAX_RATES.keys}" }
    }

    val totalWithoutTaxes: BigDecimal get() = items.fold(BigDecimal.ZERO) { sum, item -> sum + item.total }

    /** Le taux du palier le plus haut dépassé par le total hors taxes. */
    val discountRate: BigDecimal get() =
        DISCOUNTS.lastOrNull { (threshold, _) -> totalWithoutTaxes > threshold }?.second ?: BigDecimal.ZERO

    val taxRate: BigDecimal get() = TAX_RATES.getValue(state)

    val discount: BigDecimal get() = percentOf(totalWithoutTaxes, discountRate)

    val tax: BigDecimal get() = percentOf(totalWithoutTaxes - discount, taxRate)

    val totalPrice: BigDecimal get() = totalWithoutTaxes - discount + tax

    fun print(): String {
        val lines = items.map { item ->
            item.label.padEnd(20) + "${item.quantity}".padStart(6) + money(item.unitPrice).padStart(14) + money(item.total).padStart(14)
        }
        val rule = "-".repeat(WIDTH)
        val summary = listOf(
            "Total without taxes" to money(totalWithoutTaxes),
            "Discount ${discountRate.stripTrailingZeros().toPlainString()}%" to "-" + money(discount),
            "Tax ${taxRate.stripTrailingZeros().toPlainString()}%" to "+" + money(tax),
        ).map { (label, value) -> label.padEnd(WIDTH - 14) + value.padStart(14) }
        val total = "Total price".padEnd(WIDTH - 14) + money(totalPrice).padStart(14)
        return (lines + rule + summary + rule + total).joinToString("\n")
    }

    private fun money(amount: BigDecimal) = amount.setScale(2, java.math.RoundingMode.HALF_UP).toPlainString()

    private fun percentOf(amount: BigDecimal, rate: BigDecimal) =
        (amount * rate).divide(BigDecimal(100), 2, java.math.RoundingMode.HALF_UP)

    private companion object {
        const val WIDTH = 54

        val DISCOUNTS = listOf(1_000 to "3", 5_000 to "5", 7_000 to "7", 10_000 to "10", 50_000 to "15")
            .map { (threshold, rate) -> BigDecimal(threshold) to BigDecimal(rate) }

        val TAX_RATES = mapOf("UT" to "6.85", "NV" to "8.00", "TX" to "6.25", "AL" to "4.00", "CA" to "8.25")
            .mapValues { BigDecimal(it.value) }
    }
}
