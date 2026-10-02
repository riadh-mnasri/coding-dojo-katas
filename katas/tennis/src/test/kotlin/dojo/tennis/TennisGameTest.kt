// Copyright (c) 2026 Riadh MNASRI. Licensed under the MIT License.
package dojo.tennis

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

class TennisGameTest {

    private val game = TennisGame("Serena", "Venus")

    private fun points(serena: Int, venus: Int) {
        repeat(serena) { game.pointWonBy("Serena") }
        repeat(venus) { game.pointWonBy("Venus") }
    }

    @Test
    fun `a new game is love all`() {
        assertThat(game.score()).isEqualTo("Love-All")
    }

    @Test
    fun `calls the running score of each player`() {
        points(serena = 1, venus = 0)
        assertThat(game.score()).isEqualTo("Fifteen-Love")

        points(serena = 2, venus = 1)
        assertThat(game.score()).isEqualTo("Forty-Fifteen")
    }

    @Test
    fun `equal scores below forty are called all`() {
        points(serena = 2, venus = 2)

        assertThat(game.score()).isEqualTo("Thirty-All")
    }

    @Test
    fun `three points each is deuce`() {
        points(serena = 3, venus = 3)

        assertThat(game.score()).isEqualTo("Deuce")
    }

    @Test
    fun `one point ahead after deuce is advantage`() {
        points(serena = 4, venus = 3)
        assertThat(game.score()).isEqualTo("Advantage Serena")

        points(serena = 0, venus = 2)
        assertThat(game.score()).isEqualTo("Advantage Venus")
    }

    @Test
    fun `four points with a two-point lead wins the game`() {
        points(serena = 4, venus = 0)
        assertThat(game.score()).isEqualTo("Win for Serena")
    }

    @Test
    fun `two points ahead after deuce wins the game`() {
        points(serena = 3, venus = 5)
        assertThat(game.score()).isEqualTo("Win for Venus")
    }
}
