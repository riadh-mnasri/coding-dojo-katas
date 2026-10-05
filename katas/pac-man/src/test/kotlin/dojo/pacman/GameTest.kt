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

    @Test
    fun `pacman can be turned and then moves that way`() {
        val game = Game.parse(board("...", ".V.", "..."))

        game.turn(Direction.LEFT)
        assertThat(game.render()).isEqualTo(board("...", ".>.", "..."))

        game.tick()
        assertThat(game.render()).isEqualTo(board("...", "> .", "..."))
    }

    @Test
    fun `pacman wraps around the edges`() {
        val game = Game.parse(board(".V.", "...", "..."))

        game.tick()

        assertThat(game.render()).isEqualTo(board(". .", "...", ".V."))
    }

    @Test
    fun `pacman stops in front of a wall`() {
        val game = Game.parse(board(".#.", ".V.", "..."))

        game.tick()

        assertThat(game.render()).isEqualTo(board(".#.", ".V.", "..."))
        assertThat(game.score).isZero()
    }

    @Test
    fun `pacman does not turn towards a wall`() {
        val game = Game.parse(board("...", "#V.", "..."))

        game.turn(Direction.LEFT)

        assertThat(game.render()).isEqualTo(board("...", "#V.", "..."))
    }

    @Test
    fun `the level is complete once every dot is eaten`() {
        val game = Game.parse(board("#.#", "#V#", "###"))
        assertThat(game.isLevelComplete).isFalse()

        game.tick()

        assertThat(game.isLevelComplete).isTrue()
    }

    @Test
    fun `running into a monster ends the game`() {
        val game = Game.parse(board(".M.", ".V.", "..."))

        game.tick()

        assertThat(game.isOver).isTrue()
        game.tick()
        assertThat(game.render()).isEqualTo(board(".M.", ".V.", "..."))
    }
}
