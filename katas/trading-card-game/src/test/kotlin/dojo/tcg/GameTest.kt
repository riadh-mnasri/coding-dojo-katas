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

    @Test
    fun `the active player wins when the opponent's health drops to zero`() {
        val ann = Player("Ann", mutableListOf(8, 8, 8, 8, 8, 8, 8, 8, 8, 8, 8, 8))
        val bob = Player("Bob", mutableListOf(0, 0, 0))
        val game = Game(ann, bob)

        while (game.winner == null) game.playTurn()

        assertThat(game.winner).isSameAs(ann)
        assertThat(bob.health).isLessThanOrEqualTo(0)
    }

    @Test
    fun `a full game between shuffled standard decks always ends with a winner`() {
        repeat(50) { seed ->
            val game = Game.withStandardDecks("Ann", "Bob", kotlin.random.Random(seed))

            var turns = 0
            while (game.winner == null) {
                game.playTurn()
                turns++
            }

            assertThat(turns).isLessThan(100)
        }
    }
}
