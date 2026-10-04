// Copyright (c) 2026 Riadh MNASRI. Licensed under the MIT License.
package dojo.wallet

import java.math.BigDecimal
import java.math.RoundingMode

enum class Currency { EUR, USD }

/** Un type d'actif ; une devise détenue en liquide connaît sa monnaie. */
enum class StockType(val currency: Currency? = null) { PETROLEUM, BITCOIN, EUR(Currency.EUR), USD(Currency.USD) }

data class Stock(val quantity: BigDecimal, val type: StockType)

/** Un montant arrondi au centime ; deux valeurs sont égales si leurs montants le sont numériquement (0 = 0.00). */
class Value(amount: BigDecimal, val currency: Currency) {
    val amount: BigDecimal = amount.setScale(2, RoundingMode.HALF_EVEN)

    override fun equals(other: Any?) = other is Value && currency == other.currency && amount.compareTo(other.amount) == 0

    override fun hashCode() = 31 * amount.hashCode() + currency.hashCode()

    override fun toString() = "$amount $currency"
}

fun interface RateProvider {
    fun rate(from: StockType, to: Currency): BigDecimal
}

class Wallet(private vararg val stocks: Stock) {
    fun value(currency: Currency, rates: RateProvider): Value =
        Value(stocks.fold(BigDecimal.ZERO) { total, stock -> total + stock.quantity * rateOf(stock.type, currency, rates) }, currency)

    private fun rateOf(type: StockType, currency: Currency, rates: RateProvider) =
        if (type.currency == currency) BigDecimal.ONE else rates.rate(type, currency)
}
