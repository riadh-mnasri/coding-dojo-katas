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
}
