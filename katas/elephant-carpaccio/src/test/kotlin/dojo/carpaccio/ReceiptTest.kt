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

    @Test
    fun `slice 3 - Utah adds a 6,85 percent tax`() {
        val receipt = Receipt(listOf(item("Book", 1, "100")), state = "UT")

        assertThat(receipt.tax).isEqualByComparingTo("6.85")
        assertThat(receipt.totalPrice).isEqualByComparingTo("106.85")
    }

    @org.junit.jupiter.params.ParameterizedTest(name = "{0}: {1}")
    @org.junit.jupiter.params.provider.CsvSource("NV, 8.00", "TX, 6.25", "AL, 4.00", "CA, 8.25")
    fun `slice 4 - each state has its tax rate`(state: String, tax: String) {
        assertThat(Receipt(listOf(item("Book", 1, "100")), state).tax).isEqualByComparingTo(tax)
    }

    @org.junit.jupiter.params.ParameterizedTest(name = "{0} -> {1} %")
    @org.junit.jupiter.params.provider.CsvSource("1000, 0", "1000.01, 3", "5000.01, 5", "7000.01, 7", "10000.01, 10", "50000.01, 15")
    fun `slice 5 - orders above a threshold are discounted`(amount: String, rate: String) {
        val receipt = Receipt(listOf(item("Lot", 1, amount)), state = "UT")

        assertThat(receipt.discountRate).isEqualByComparingTo(rate)
    }
}
