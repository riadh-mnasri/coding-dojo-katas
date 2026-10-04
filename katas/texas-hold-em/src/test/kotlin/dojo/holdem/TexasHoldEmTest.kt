// Copyright (c) 2026 Riadh MNASRI. Licensed under the MIT License.
package dojo.holdem

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

class TexasHoldEmTest {

    @Test
    fun `a folded hand is repeated without rank`() {
        assertThat(TexasHoldEm.announce("9h 5s")).isEqualTo("9h 5s")
    }

    @Test
    fun `a full hand is ranked with its best five cards`() {
        assertThat(TexasHoldEm.rank("Kc 9s Ks Kd 9d 3c 6d")).isEqualTo("Full House")
        assertThat(TexasHoldEm.rank("9c Ah Ks Kd 9d 3c 6d")).isEqualTo("Two Pair")
        assertThat(TexasHoldEm.rank("4d 2d Ks Kd 9d 3c 6d")).isEqualTo("Flush")
    }
}
