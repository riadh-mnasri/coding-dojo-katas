// Copyright (c) 2026 Riadh MNASRI. Licensed under the MIT License.
package dojo.wallet

import java.math.BigDecimal

enum class Currency { EUR, USD }

/** Un type d'actif ; une devise détenue en liquide connaît sa monnaie. */
enum class StockType(val currency: Currency? = null) { PETROLEUM, BITCOIN, EUR(Currency.EUR), USD(Currency.USD) }

data class Stock(val quantity: BigDecimal, val type: StockType)

data class Value(val amount: BigDecimal, val currency: Currency)

fun interface RateProvider {
    fun rate(from: StockType, to: Currency): BigDecimal
}

class Wallet(private vararg val stocks: Stock) {
    fun value(currency: Currency, rates: RateProvider): Value =
        Value(stocks.fold(BigDecimal.ZERO) { total, stock -> total + stock.quantity * rateOf(stock.type, currency, rates) }, currency)

    private fun rateOf(type: StockType, currency: Currency, rates: RateProvider) =
        if (type.currency == currency) BigDecimal.ONE else rates.rate(type, currency)
}
