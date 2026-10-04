// Copyright (c) 2026 Riadh MNASRI. Licensed under the MIT License.
package dojo.poker

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

class HandTest {

    private fun hand(cards: String) = Hand.parse(cards)

    @Test
    fun `with high cards, the highest card wins`() {
        assertThat(hand("2C 3H 4S 8C AH")).isGreaterThan(hand("2H 3D 5S 9C KD"))
    }
}
