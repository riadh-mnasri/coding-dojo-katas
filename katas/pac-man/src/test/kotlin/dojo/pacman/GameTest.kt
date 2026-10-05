// Copyright (c) 2026 Riadh MNASRI. Licensed under the MIT License.
package dojo.pacman

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

/** Pac-Man est dessiné bouche ouverte : V regarde en haut, ^ en bas, > à gauche, < à droite. */
class GameTest {

    private fun board(vararg rows: String) = rows.joinToString("\n")

    @Test
    fun `on each tick pacman moves forward and eats the dot`() {
        val game = Game.parse(board("...", ".V.", "..."))

        game.tick()

        assertThat(game.render()).isEqualTo(board(".V.", ". .", "..."))
        assertThat(game.score).isEqualTo(1)
    }
}
