// Copyright (c) 2026 Riadh MNASRI. Licensed under the MIT License.
package dojo.yahtzee

import dojo.yahtzee.Category.CHANCE
import dojo.yahtzee.Category.YAHTZEE
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.CsvSource

class YahtzeeTest {

    private fun roll(dice: String) = dice.split(",").map { it.trim().toInt() }

    @Test
    fun `chance scores the sum of all dice`() {
        assertThat(Yahtzee.score(roll("1,1,3,3,6"), CHANCE)).isEqualTo(14)
    }

    @ParameterizedTest(name = "{0} on yahtzee scores {1}")
    @CsvSource("'4,4,4,4,4', 50", "'4,4,4,4,5', 0")
    fun `yahtzee scores 50 when all dice are the same`(dice: String, expected: Int) {
        assertThat(Yahtzee.score(roll(dice), YAHTZEE)).isEqualTo(expected)
    }
}
