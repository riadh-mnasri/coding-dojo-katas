// Copyright (c) 2026 Riadh MNASRI. Licensed under the MIT License.
package dojo.poker

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.CsvSource

class GameTest {

    @Test
    fun `equal hands are a tie`() {
        assertThat(Game.judge("Black: 2H 3D 5S 9C KD  White: 2D 3H 5C 9S KH")).isEqualTo("Tie.")
    }
}
