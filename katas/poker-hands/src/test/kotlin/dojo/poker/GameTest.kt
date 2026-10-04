// Copyright (c) 2026 Riadh MNASRI. Licensed under the MIT License.
package dojo.poker

import org.assertj.core.api.Assertions.assertThat
import org.assertj.core.api.Assertions.assertThatThrownBy
import org.junit.jupiter.api.Test
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.CsvSource

class GameTest {

    @Test
    fun `equal hands are a tie`() {
        assertThat(Game.judge("Black: 2H 3D 5S 9C KD  White: 2D 3H 5C 9S KH")).isEqualTo("Tie.")
    }

    @ParameterizedTest(name = "{0} -> {1}")
    @CsvSource(
        delimiter = '|',
        value = [
            "Black: 2H 3D 5S 9C KD  White: 2C 3H 4S 8C AH | White wins. - with high card: Ace",
            "Black: 2H 4S 4C 2D 4H  White: 2S 8S AS QS 3S | Black wins. - with full house: 4 over 2",
            "Black: 2H 3D 5S 9C KD  White: 2C 3H 4S 8C KH | Black wins. - with high card: 9",
        ],
    )
    fun `announces the winner and why, as in the kata`(line: String, expected: String) {
        assertThat(Game.judge(line)).isEqualTo(expected)
    }

    @ParameterizedTest
    @CsvSource(
        delimiter = '|',
        value = [
            "Black 2H 3D 5S 9C KD White 2C 3H 4S 8C AH",
            "Black: 2H 3D 5S 9C  White: 2C 3H 4S 8C AH",
            "Black: 2H 3D 5S 9C 1D  White: 2C 3H 4S 8C AH",
            "Black: 2H 3D 5S 9C KX  White: 2C 3H 4S 8C AH",
            "Black: 2H 2H 5S 9C KD  White: 2C 3H 4S 8C AH",
        ],
    )
    fun `rejects malformed lines and hands`(line: String) {
        assertThatThrownBy { Game.judge(line) }.isInstanceOf(IllegalArgumentException::class.java)
    }
}
