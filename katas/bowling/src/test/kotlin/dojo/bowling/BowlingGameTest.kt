// Copyright (c) 2026 Riadh MNASRI. Licensed under the MIT License.
package dojo.bowling

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

class BowlingGameTest {

    private val game = BowlingGame()

    private fun rollMany(times: Int, pins: Int) = repeat(times) { game.roll(pins) }

    @Test
    fun `gutter game scores zero`() {
        rollMany(20, 0)

        assertThat(game.score()).isEqualTo(0)
    }

    @Test
    fun `all ones scores twenty`() {
        rollMany(20, 1)

        assertThat(game.score()).isEqualTo(20)
    }

    @Test
    fun `a spare earns the next roll as bonus`() {
        game.roll(5)
        game.roll(5)
        game.roll(3)
        rollMany(17, 0)

        assertThat(game.score()).isEqualTo(16)
    }

    @Test
    fun `a strike earns the next two rolls as bonus`() {
        game.roll(10)
        game.roll(3)
        game.roll(4)
        rollMany(16, 0)

        assertThat(game.score()).isEqualTo(24)
    }

    @Test
    fun `perfect game scores 300`() {
        rollMany(12, 10)

        assertThat(game.score()).isEqualTo(300)
    }

    @Test
    fun `a game of spares with a final 5 scores 150`() {
        rollMany(21, 5)

        assertThat(game.score()).isEqualTo(150)
    }
}
