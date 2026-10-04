// Copyright (c) 2026 Riadh MNASRI. Licensed under the MIT License.
package dojo.holdem

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

class TexasHoldEmTest {

    @Test
    fun `a folded hand is repeated without rank`() {
        assertThat(TexasHoldEm.announce("9h 5s")).isEqualTo("9h 5s")
    }
}
