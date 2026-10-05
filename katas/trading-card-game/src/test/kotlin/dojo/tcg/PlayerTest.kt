// Copyright (c) 2026 Riadh MNASRI. Licensed under the MIT License.
package dojo.tcg

import org.assertj.core.api.Assertions.assertThat
import org.assertj.core.api.Assertions.assertThatThrownBy
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

    @Test
    fun `playing a card spends its cost in mana and deals as much damage`() {
        val ann = player(3, 1, 2, 0, 0, 0)
        val bob = player(0, 0, 0, 0, 0)
        repeat(3) { ann.startTurn() }

        ann.play(3, against = bob)

        assertThat(ann.mana).isZero()
        assertThat(ann.hand).doesNotContain(3)
        assertThat(bob.health).isEqualTo(27)
    }

    @Test
    fun `a card must be in hand and affordable`() {
        val ann = player(5, 1, 2, 0)
        val bob = player(0, 0, 0)
        ann.startTurn()

        assertThatThrownBy { ann.play(5, against = bob) }.isInstanceOf(IllegalStateException::class.java)
        assertThatThrownBy { ann.play(7, against = bob) }.isInstanceOf(IllegalArgumentException::class.java)
        assertThat(bob.health).isEqualTo(30)
    }

    @Test
    fun `bleeding out - drawing from an empty deck costs 1 health instead`() {
        val ann = player(0, 0, 0)

        ann.startTurn()

        assertThat(ann.health).isEqualTo(29)
        assertThat(ann.hand).hasSize(3)
    }
}
