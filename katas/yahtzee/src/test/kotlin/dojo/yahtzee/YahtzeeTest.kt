// Copyright (c) 2026 Riadh MNASRI. Licensed under the MIT License.
package dojo.yahtzee

import dojo.yahtzee.Category.CHANCE
import dojo.yahtzee.Category.YAHTZEE
import org.junit.jupiter.params.provider.EnumSource
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

    @ParameterizedTest(name = "{0} on {1} scores {2}")
    @CsvSource("'1,1,2,4,4', ONES, 2", "'1,1,2,4,4', FOURS, 8", "'2,3,2,5,1', TWOS, 4", "'3,3,3,4,5', THREES, 9", "'5,5,5,5,4', FIVES, 20", "'6,1,6,2,3', SIXES, 12", "'1,2,3,4,5', SIXES, 0")
    fun `upper categories sum the dice showing that face`(dice: String, category: Category, expected: Int) {
        assertThat(Yahtzee.score(roll(dice), category)).isEqualTo(expected)
    }

    @ParameterizedTest(name = "{0} on pair scores {1}")
    @CsvSource("'3,3,3,4,4', 8", "'1,2,3,4,6', 0", "'5,5,1,1,2', 10")
    fun `pair scores the two highest matching dice`(dice: String, expected: Int) {
        assertThat(Yahtzee.score(roll(dice), Category.PAIR)).isEqualTo(expected)
    }
}
