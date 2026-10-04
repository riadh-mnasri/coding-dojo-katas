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

    @Test
    fun `with the same highest card, the next cards decide`() {
        assertThat(hand("2H 3D 5S 9C KD")).isGreaterThan(hand("2C 3H 4S 8C KH"))
        assertThat(hand("2H 3D 5S 9C KD")).isEqualByComparingTo(hand("2D 3H 5C 9S KH"))
    }

    @Test
    fun `a pair beats high cards, and pairs compare by their value then the kickers`() {
        assertThat(hand("2H 2D 5S 9C KD")).isGreaterThan(hand("2C 3H 4S 8C AH"))
        assertThat(hand("3H 3D 5S 9C KD")).isGreaterThan(hand("2C 2H 4S 8C AH"))
        assertThat(hand("3H 3D 5S 9C KD")).isGreaterThan(hand("3C 3S 4S 8C KH"))
    }

    @Test
    fun `groups of equal values rank from two pairs up to four of a kind`() {
        val twoPairs = hand("2H 2D 5S 5C KD")
        val threeOfAKind = hand("2H 2D 2S 5C KD")
        val fullHouse = hand("2H 2D 2S 5C 5D")
        val fourOfAKind = hand("2H 2D 2S 2C KD")

        assertThat(listOf(fourOfAKind, twoPairs, fullHouse, threeOfAKind).sorted())
            .containsExactly(twoPairs, threeOfAKind, fullHouse, fourOfAKind)
        assertThat(hand("3H 3D 2S 2C KD")).isGreaterThan(hand("2H 2D 4S 4C AD").let { hand("3S 3C 2D 2H QD") })
        assertThat(hand("4H 4D 4S 2C 2D")).isGreaterThan(hand("3H 3D 3S AC AD"))
    }
}
