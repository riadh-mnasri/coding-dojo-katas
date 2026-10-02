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
}
