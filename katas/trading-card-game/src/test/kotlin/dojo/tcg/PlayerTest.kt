// Copyright (c) 2026 Riadh MNASRI. Licensed under the MIT License.
package dojo.tcg

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

class PlayerTest {

    /** Une pioche dans un ordre connu : la première carte de la liste est piochée en premier. */
    private fun player(vararg deck: Int) = Player("Ann", deck.toMutableList())

    @Test
    fun `a player starts with 30 health, no mana and three cards in hand`() {
        val ann = player(0, 1, 2, 3, 4)

        assertThat(ann.health).isEqualTo(30)
        assertThat(ann.manaSlots).isZero()
        assertThat(ann.hand).containsExactly(0, 1, 2)
        assertThat(ann.deck).containsExactly(3, 4)
    }

    @Test
    fun `starting a turn adds a mana slot, refills the mana and draws a card`() {
        val ann = player(0, 1, 2, 3, 4)

        ann.startTurn()

        assertThat(ann.manaSlots).isEqualTo(1)
        assertThat(ann.mana).isEqualTo(1)
        assertThat(ann.hand).containsExactly(0, 1, 2, 3)
    }

    @Test
    fun `there are at most ten mana slots`() {
        val ann = player(*IntArray(20))

        repeat(12) { ann.startTurn() }

        assertThat(ann.manaSlots).isEqualTo(10)
    }
}
