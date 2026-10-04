// Copyright (c) 2026 Riadh MNASRI. Licensed under the MIT License.
package dojo.lags

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Assertions.assertTimeoutPreemptively
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.function.ThrowingSupplier
import java.time.Duration

class LagsTest {

    @Test
    fun `no request earns nothing`() {
        assertThat(Lags.bestGain(emptyList())).isZero()
    }

    @Test
    fun `a single request earns its price`() {
        assertThat(Lags.bestGain(listOf(Request("AF514", 0, 5, 10)))).isEqualTo(10)
    }

    @Test
    fun `of two overlapping requests, only the better one is kept`() {
        val requests = listOf(Request("AF514", 0, 5, 10), Request("CO5", 3, 7, 14))

        assertThat(Lags.bestGain(requests)).isEqualTo(14)
    }

    @Test
    fun `solves the sample of the kata`() {
        val requests = listOf(
            Request("AF514", 0, 5, 10),
            Request("CO5", 3, 7, 14),
            Request("AF515", 5, 9, 7),
            Request("BA01", 6, 9, 8),
        )

        assertThat(Lags.bestGain(requests)).isEqualTo(18)
    }

    @Test
    fun `a flight can leave at the very moment the previous one lands`() {
        val requests = listOf(Request("A", 0, 5, 10), Request("B", 5, 5, 10))

        assertThat(Lags.bestGain(requests)).isEqualTo(20)
    }

    @Test
    fun `handles a realistic file of ten thousand requests quickly`() {
        // Optimum connu par construction : 10 000 vols d'une heure à 1 € qui s'enchaînent,
        // et autant de leurres de deux heures à 1 € qui chevauchent leurs voisins.
        val requests = (0 until 10_000).flatMap { hour -> listOf(Request("S$hour", hour, 1, 1), Request("L$hour", hour, 2, 1)) }

        val gain = assertTimeoutPreemptively(Duration.ofSeconds(2), ThrowingSupplier { Lags.bestGain(requests) })

        assertThat(gain).isEqualTo(10_000)
    }
}
