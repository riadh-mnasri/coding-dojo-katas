// Copyright (c) 2026 Riadh MNASRI. Licensed under the MIT License.
package dojo.carpaccio

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

class ReceiptTest {

    private fun item(label: String, quantity: Int, price: String) = Item(label, quantity, price.toBigDecimal())

    @Test
    fun `slice 1 - one line costs its quantity times its price`() {
        val receipt = Receipt(listOf(item("Book", 3, "12.50")), state = "UT")

        assertThat(receipt.totalWithoutTaxes).isEqualByComparingTo("37.50")
    }

    @Test
    fun `slice 2 - several lines add up`() {
        val receipt = Receipt(listOf(item("Book", 3, "12.50"), item("Pen", 2, "1.25")), state = "UT")

        assertThat(receipt.totalWithoutTaxes).isEqualByComparingTo("40.00")
    }
}
