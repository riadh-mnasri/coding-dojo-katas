// Copyright (c) 2026 Riadh MNASRI. Licensed under the MIT License.
package dojo.nim

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

class NimGameTest {

    private val game = NimGame("Alice", "Bob")

    @Test
    fun `a game starts with ten sticks and the first player to move`() {
        assertThat(game.sticks).isEqualTo(10)
        assertThat(game.currentPlayer).isEqualTo("Alice")
    }

    @Test
    fun `taking sticks removes them and hands over the turn`() {
        game.take(2)

        assertThat(game.sticks).isEqualTo(8)
        assertThat(game.currentPlayer).isEqualTo("Bob")
    }
}
