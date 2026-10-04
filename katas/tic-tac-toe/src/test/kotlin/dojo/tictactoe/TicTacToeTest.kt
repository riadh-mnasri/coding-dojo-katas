// Copyright (c) 2026 Riadh MNASRI. Licensed under the MIT License.
package dojo.tictactoe

import org.assertj.core.api.Assertions.assertThat
import org.assertj.core.api.Assertions.assertThatThrownBy
import org.junit.jupiter.api.Test

/** Boucle interne : une règle par test. */
class TicTacToeTest {

    private val game = TicTacToe()

    @Test
    fun `X plays first`() {
        game.play(5)

        assertThat(game.ownerOf(5)).isEqualTo(Player.X)
    }

    @Test
    fun `players take turns`() {
        game.play(5)
        game.play(1)

        assertThat(game.ownerOf(1)).isEqualTo(Player.O)
    }

    @Test
    fun `a field already taken cannot be taken again`() {
        game.play(5)

        assertThatThrownBy { game.play(5) }.isInstanceOf(IllegalArgumentException::class.java)
        assertThat(game.ownerOf(5)).isEqualTo(Player.X)
    }

    private fun play(vararg cells: Int) = cells.forEach(game::play)

    @Test
    fun `a full row wins and ends the game`() {
        play(1, 4, 2, 5, 3)

        assertThat(game.winner()).isEqualTo(Player.X)
        assertThat(game.isOver()).isTrue()
    }

    @Test
    fun `a full column wins`() {
        play(2, 1, 3, 4, 5, 7)

        assertThat(game.winner()).isEqualTo(Player.O)
    }

    @Test
    fun `a full diagonal wins`() {
        play(3, 1, 5, 2, 7)

        assertThat(game.winner()).isEqualTo(Player.X)
    }

    @Test
    fun `the game is over when every field is taken, even without a winner`() {
        play(1, 2, 3, 5, 4, 6, 8, 7, 9)

        assertThat(game.winner()).isNull()
        assertThat(game.isOver()).isTrue()
    }
}
