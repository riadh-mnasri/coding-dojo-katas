// Copyright (c) 2026 Riadh MNASRI. Licensed under the MIT License.
package dojo.romancalculator

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.CsvSource

class RomanCalculatorTest {

    @Test
    fun `I plus I is II`() {
        assertThat(RomanCalculator.add("I", "I")).isEqualTo("II")
    }

    @ParameterizedTest(name = "{0} + {1} = {2}")
    @CsvSource("X, V, XV", "I, X, XI", "V, X, XV")
    fun `letters are sorted from the biggest`(left: String, right: String, sum: String) {
        assertThat(RomanCalculator.add(left, right)).isEqualTo(sum)
    }

    @Test
    fun `five I make a V`() {
        assertThat(RomanCalculator.add("III", "II")).isEqualTo("V")
    }

    @ParameterizedTest(name = "{0} + {1} = {2}")
    @CsvSource("V, V, X", "XXX, XX, L", "L, L, C", "CCC, CC, D", "D, D, M", "VIII, VII, XV")
    fun `groups letters at every level`(left: String, right: String, sum: String) {
        assertThat(RomanCalculator.add(left, right)).isEqualTo(sum)
    }

    @Test
    fun `four I are written IV`() {
        assertThat(RomanCalculator.add("II", "II")).isEqualTo("IV")
    }

    @Test
    fun `V and four I are written IX`() {
        assertThat(RomanCalculator.add("VII", "II")).isEqualTo("IX")
    }

    @ParameterizedTest(name = "{0} + {1} = {2}")
    @CsvSource("XX, XX, XL", "LXX, XX, XC", "CC, CC, CD", "DCC, CC, CM")
    fun `uses subtraction at every level`(left: String, right: String, sum: String) {
        assertThat(RomanCalculator.add(left, right)).isEqualTo(sum)
    }

    @ParameterizedTest(name = "{0} + {1} = {2}")
    @CsvSource("IV, I, V", "IX, I, X", "XIV, LX, LXXIV", "XL, X, L", "IX, IX, XVIII")
    fun `expands subtractive inputs before adding`(left: String, right: String, sum: String) {
        assertThat(RomanCalculator.add(left, right)).isEqualTo(sum)
    }
}
