// Copyright (c) 2026 Riadh MNASRI. Licensed under the MIT License.
package dojo.tcg

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

class GameTest {

    @Test
    fun `a turn plays the affordable cards then hands over to the opponent`() {
        val ann = Player("Ann", mutableListOf(1, 2, 3, 1, 0))
        val bob = Player("Bob", mutableListOf(0, 0, 0, 0, 0))
        val game = Game(ann, bob)

        game.playTurn()

        assertThat(bob.health).isEqualTo(29)
        assertThat(game.active).isSameAs(bob)
    }
}
