// Copyright (c) 2026 Riadh MNASRI. Licensed under the MIT License.
package dojo.mastermind

import dojo.mastermind.Color.BLUE
import dojo.mastermind.Color.GREEN
import dojo.mastermind.Color.PINK
import dojo.mastermind.Color.PURPLE
import dojo.mastermind.Color.RED
import dojo.mastermind.Color.YELLOW
import org.assertj.core.api.Assertions.assertThat
import org.assertj.core.api.Assertions.assertThatThrownBy
import org.junit.jupiter.api.Test

class MastermindTest {

    @Test
    fun `a wrong single peg is neither well placed nor misplaced`() {
        assertThat(Mastermind.evaluate(secret = listOf(BLUE), guess = listOf(RED))).isEqualTo(Answer(0, 0))
    }

    @Test
    fun `the right color at the right place is well placed`() {
        assertThat(Mastermind.evaluate(secret = listOf(BLUE), guess = listOf(BLUE))).isEqualTo(Answer(1, 0))
    }

    @Test
    fun `a right color at the wrong place is misplaced`() {
        assertThat(Mastermind.evaluate(secret = listOf(RED, YELLOW), guess = listOf(BLUE, RED))).isEqualTo(Answer(0, 1))
    }

    @Test
    fun `answers the kata example`() {
        val answer = Mastermind.evaluate(
            secret = listOf(BLUE, RED, GREEN, PINK),
            guess = listOf(YELLOW, RED, BLUE, PURPLE),
        )

        assertThat(answer).isEqualTo(Answer(1, 1))
    }

    @Test
    fun `a color guessed twice but present once is counted once`() {
        val answer = Mastermind.evaluate(secret = listOf(RED, BLUE, GREEN), guess = listOf(GREEN, GREEN, YELLOW))

        assertThat(answer).isEqualTo(Answer(0, 1))
    }

    @Test
    fun `a well placed peg is not counted as misplaced as well`() {
        val answer = Mastermind.evaluate(secret = listOf(RED, BLUE), guess = listOf(RED, RED))

        assertThat(answer).isEqualTo(Answer(1, 0))
    }

    @Test
    fun `secret and guess must have the same size`() {
        assertThatThrownBy { Mastermind.evaluate(secret = listOf(RED, BLUE), guess = listOf(RED)) }
            .isInstanceOf(IllegalArgumentException::class.java)
    }
}
