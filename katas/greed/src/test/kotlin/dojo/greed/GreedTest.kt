// Copyright (c) 2026 Riadh MNASRI. Licensed under the MIT License.
package dojo.greed

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.CsvSource

class GreedTest {

    private fun score(vararg dice: Int) = Greed.score(dice.toList())

    @Test
    fun `no dice scores nothing`() {
        assertThat(score()).isEqualTo(0)
    }

    @Test
    fun `a single one scores 100 and a single five scores 50`() {
        assertThat(score(1)).isEqualTo(100)
        assertThat(score(5)).isEqualTo(50)
        assertThat(score(1, 5, 2)).isEqualTo(150)
    }
}
