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
}
