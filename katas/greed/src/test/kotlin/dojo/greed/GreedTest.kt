// Copyright (c) 2026 Riadh MNASRI. Licensed under the MIT License.
package dojo.greed

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.CsvSource

class GreedTest {

    private fun score(vararg dice: Int) = Greed.score(dice.toList())

    @Test
    fun `no dice scores nothing`() {
        assertThat(score()).isEqualTo(0)
    }

    @Test
    fun `a single one scores 100 and a single five scores 50`() {
        assertThat(score(1)).isEqualTo(100)
        assertThat(score(5)).isEqualTo(50)
        assertThat(score(1, 5, 2)).isEqualTo(150)
    }

    @ParameterizedTest(name = "{0} {0} {0} scores {1}")
    @CsvSource("1, 1000", "2, 200", "3, 300", "4, 400", "5, 500", "6, 600")
    fun `triples score by face`(face: Int, expected: Int) {
        assertThat(score(face, face, face)).isEqualTo(expected)
    }

    @ParameterizedTest(name = "{0} dice of {1} score {2}")
    @CsvSource("4, 2, 400", "5, 2, 800", "6, 2, 1600", "4, 1, 2000")
    fun `four, five and six of a kind multiply the triple score by 2, 4 and 8`(count: Int, face: Int, expected: Int) {
        assertThat(Greed.score(List(count) { face })).isEqualTo(expected)
    }

    @Test
    fun `three pairs score 800`() {
        assertThat(score(2, 2, 3, 3, 4, 4)).isEqualTo(800)
    }

    @Test
    fun `a straight scores 1200`() {
        assertThat(score(1, 2, 3, 4, 5, 6)).isEqualTo(1200)
    }
}
