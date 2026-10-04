// Copyright (c) 2026 Riadh MNASRI. Licensed under the MIT License.
package dojo.tictactoe

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

/** Boucle externe : une partie complète, décrite comme le ferait l'utilisateur. */
class TicTacToeAcceptanceTest {

    @Test
    fun `X wins a game on the diagonal`() {
        // Given
        val game = TicTacToe()

        // When
        listOf(5, 2, 1, 3, 9).forEach(game::play)

        // Then
        assertThat(game.board()).isEqualTo(
            """
            +---+---+---+
            | X | O | O |
            +---+---+---+
            | 4 | X | 6 |
            +---+---+---+
            | 7 | 8 | X |
            +---+---+---+
            """.trimIndent(),
        )
        assertThat(game.isOver()).isTrue()
        assertThat(game.winner()).isEqualTo(Player.X)
    }
}
