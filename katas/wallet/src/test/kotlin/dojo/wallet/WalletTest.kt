// Copyright (c) 2026 Riadh MNASRI. Licensed under the MIT License.
package dojo.wallet

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import java.math.BigDecimal

class WalletTest {

    /** Un fournisseur de taux figé : les tests ne dépendent d'aucune API externe. */
    private val rates = RateProvider { from, to ->
        mapOf(
            (StockType.PETROLEUM to Currency.EUR) to BigDecimal("62.50"),
            (StockType.BITCOIN to Currency.EUR) to BigDecimal("55000"),
            (StockType.USD to Currency.EUR) to BigDecimal("0.92"),
        )[from to to] ?: error("No rate from $from to $to")
    }

    @Test
    fun `an empty wallet is worth nothing`() {
        assertThat(Wallet().value(Currency.EUR, rates)).isEqualTo(Value(BigDecimal.ZERO, Currency.EUR))
    }
}
