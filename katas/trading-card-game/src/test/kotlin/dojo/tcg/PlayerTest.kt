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
}
