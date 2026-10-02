// Copyright (c) 2026 Riadh MNASRI. Licensed under the MIT License.
package dojo.nim

import org.assertj.core.api.Assertions.assertThat
import org.assertj.core.api.Assertions.assertThatThrownBy
import org.junit.jupiter.api.Test
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.ValueSource

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

    @ParameterizedTest
    @ValueSource(ints = [0, 4, -1])
    fun `a player takes one to three sticks`(count: Int) {
        assertThatThrownBy { game.take(count) }.isInstanceOf(IllegalArgumentException::class.java)
        assertThat(game.sticks).isEqualTo(10)
    }

    @Test
    fun `cannot take more sticks than remain`() {
        repeat(3) { game.take(3) }

        assertThatThrownBy { game.take(2) }.isInstanceOf(IllegalArgumentException::class.java)
    }
}
