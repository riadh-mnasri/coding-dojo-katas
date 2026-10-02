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

    @Test
    fun `the player taking the last stick loses`() {
        // Given: 10 sticks, Alice and Bob alternate until one stick is left for Bob
        listOf(3, 3, 3).forEach(game::take)
        assertThat(game.winner).isNull()

        // When
        game.take(1)

        // Then
        assertThat(game.sticks).isZero()
        assertThat(game.winner).isEqualTo("Alice")
    }

    @Test
    fun `no move is allowed once the game is over`() {
        listOf(3, 3, 3, 1).forEach(game::take)

        assertThatThrownBy { game.take(1) }.isInstanceOf(IllegalStateException::class.java)
    }

    @Test
    fun `the number of sticks can be chosen`() {
        val longGame = NimGame("Rick", "Morty", sticks = 21)

        assertThat(longGame.sticks).isEqualTo(21)
    }
}
