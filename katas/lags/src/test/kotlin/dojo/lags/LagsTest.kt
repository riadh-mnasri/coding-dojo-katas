// Copyright (c) 2026 Riadh MNASRI. Licensed under the MIT License.
package dojo.lags

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

class LagsTest {

    @Test
    fun `no request earns nothing`() {
        assertThat(Lags.bestGain(emptyList())).isZero()
    }
}
