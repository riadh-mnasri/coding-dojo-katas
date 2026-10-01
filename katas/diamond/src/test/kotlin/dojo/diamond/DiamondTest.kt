// Copyright (c) 2026 Riadh MNASRI. Licensed under the MIT License.
package dojo.diamond

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

class DiamondTest {

    private val letters = 'A'..'Z'

    @Test
    fun `diamond A is a single A`() {
        assertThat(Diamond.of('A')).isEqualTo("A")
    }

    @Test
    fun `has two rows per letter before the widest, plus one`() {
        letters.forEach { letter ->
            assertThat(Diamond.of(letter).lines()).hasSize(2 * (letter - 'A') + 1)
        }
    }
}
