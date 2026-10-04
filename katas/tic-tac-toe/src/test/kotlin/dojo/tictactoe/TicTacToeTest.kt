// Copyright (c) 2026 Riadh MNASRI. Licensed under the MIT License.
package dojo.tictactoe

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

/** Boucle interne : une règle par test. */
class TicTacToeTest {

    private val game = TicTacToe()

    @Test
    fun `X plays first`() {
        game.play(5)

        assertThat(game.ownerOf(5)).isEqualTo(Player.X)
    }
}
