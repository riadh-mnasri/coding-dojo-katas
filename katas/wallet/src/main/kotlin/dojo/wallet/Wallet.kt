// Copyright (c) 2026 Riadh MNASRI. Licensed under the MIT License.
package dojo.wallet

import java.math.BigDecimal

enum class Currency { EUR, USD }

enum class StockType { PETROLEUM, BITCOIN, EUR, USD }

data class Stock(val quantity: BigDecimal, val type: StockType)

data class Value(val amount: BigDecimal, val currency: Currency)

fun interface RateProvider {
    fun rate(from: StockType, to: Currency): BigDecimal
}

class Wallet(private vararg val stocks: Stock) {
    fun value(currency: Currency, rates: RateProvider): Value = Value(BigDecimal.ZERO, currency)
}
