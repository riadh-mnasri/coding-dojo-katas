// Copyright (c) 2026 Riadh MNASRI. Licensed under the MIT License.
package dojo.carpaccio

import java.math.BigDecimal

data class Item(val label: String, val quantity: Int, val unitPrice: BigDecimal) {
    val total: BigDecimal get() = unitPrice * BigDecimal(quantity)
}

class Receipt(private val items: List<Item>, private val state: String) {
    val totalWithoutTaxes: BigDecimal get() = items.fold(BigDecimal.ZERO) { sum, item -> sum + item.total }
}
