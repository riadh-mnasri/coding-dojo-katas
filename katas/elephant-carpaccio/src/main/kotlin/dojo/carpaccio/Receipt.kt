// Copyright (c) 2026 Riadh MNASRI. Licensed under the MIT License.
package dojo.carpaccio

import java.math.BigDecimal

data class Item(val label: String, val quantity: Int, val unitPrice: BigDecimal) {
    val total: BigDecimal get() = unitPrice * BigDecimal(quantity)
}

class Receipt(private val items: List<Item>, private val state: String) {
    val totalWithoutTaxes: BigDecimal get() = items.fold(BigDecimal.ZERO) { sum, item -> sum + item.total }

    val taxRate: BigDecimal get() = TAX_RATES.getValue(state)

    val tax: BigDecimal get() = totalWithoutTaxes * taxRate / BigDecimal(100)

    val totalPrice: BigDecimal get() = totalWithoutTaxes + tax

    private companion object {
        val TAX_RATES = mapOf("UT" to "6.85", "NV" to "8.00", "TX" to "6.25", "AL" to "4.00", "CA" to "8.25")
            .mapValues { BigDecimal(it.value) }
    }
}
