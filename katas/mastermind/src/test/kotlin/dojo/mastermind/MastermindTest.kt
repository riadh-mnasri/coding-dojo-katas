// Copyright (c) 2026 Riadh MNASRI. Licensed under the MIT License.
package dojo.mastermind

import dojo.mastermind.Color.BLUE
import dojo.mastermind.Color.GREEN
import dojo.mastermind.Color.PINK
import dojo.mastermind.Color.PURPLE
import dojo.mastermind.Color.RED
import dojo.mastermind.Color.YELLOW
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

class MastermindTest {

    @Test
    fun `a wrong single peg is neither well placed nor misplaced`() {
        assertThat(Mastermind.evaluate(secret = listOf(BLUE), guess = listOf(RED))).isEqualTo(Answer(0, 0))
    }
}
