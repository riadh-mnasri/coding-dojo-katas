// Copyright (c) 2026 Riadh MNASRI. Licensed under the MIT License.
package dojo.lags

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

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
}
