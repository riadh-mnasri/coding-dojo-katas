// Copyright (c) 2026 Riadh MNASRI. Licensed under the MIT License.
package dojo.romannumerals

import org.assertj.core.api.Assertions.assertThat
import org.assertj.core.api.Assertions.assertThatThrownBy
import org.junit.jupiter.api.Test
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.CsvSource

class RomanNumeralsTest {

    @Test
    fun `1 is I`() {
        assertThat(RomanNumerals.toRoman(1)).isEqualTo("I")
    }

    @Test
    fun `2 and 3 repeat I`() {
        assertThat(RomanNumerals.toRoman(2)).isEqualTo("II")
        assertThat(RomanNumerals.toRoman(3)).isEqualTo("III")
    }

    @Test
    fun `5 is V and 6 is VI`() {
        assertThat(RomanNumerals.toRoman(5)).isEqualTo("V")
        assertThat(RomanNumerals.toRoman(6)).isEqualTo("VI")
    }

    @Test
    fun `10 is X and 20 is XX`() {
        assertThat(RomanNumerals.toRoman(10)).isEqualTo("X")
        assertThat(RomanNumerals.toRoman(20)).isEqualTo("XX")
    }

    @Test
    fun `4 is IV and 9 is IX`() {
        assertThat(RomanNumerals.toRoman(4)).isEqualTo("IV")
        assertThat(RomanNumerals.toRoman(9)).isEqualTo("IX")
    }

    @ParameterizedTest(name = "{0} is {1}")
    @CsvSource("50, L", "40, XL", "90, XC", "100, C", "400, CD", "500, D", "900, CM", "1000, M")
    fun `bigger letters and their subtractions`(number: Int, roman: String) {
        assertThat(RomanNumerals.toRoman(number)).isEqualTo(roman)
    }

    @ParameterizedTest(name = "{0} is {1}")
    @CsvSource("1990, MCMXC", "2008, MMVIII", "1666, MDCLXVI", "3999, MMMCMXCIX")
    fun `full numbers`(number: Int, roman: String) {
        assertThat(RomanNumerals.toRoman(number)).isEqualTo(roman)
    }

    @Test
    fun `there is no zero in Rome`() {
        assertThatThrownBy { RomanNumerals.toRoman(0) }.isInstanceOf(IllegalArgumentException::class.java)
    }

    @Test
    fun `part 2 - I is 1`() {
        assertThat(RomanNumerals.toArabic("I")).isEqualTo(1)
    }

    @ParameterizedTest(name = "{0} is {1}")
    @CsvSource("III, 3", "VIII, 8", "MDCLXVI, 1666")
    fun `part 2 - adds the letter values`(roman: String, number: Int) {
        assertThat(RomanNumerals.toArabic(roman)).isEqualTo(number)
    }

    @ParameterizedTest(name = "{0} is {1}")
    @CsvSource("IV, 4", "XLII, 42", "MCMXC, 1990", "MMMCMXCIX, 3999")
    fun `part 2 - subtracts a letter smaller than the next one`(roman: String, number: Int) {
        assertThat(RomanNumerals.toArabic(roman)).isEqualTo(number)
    }

    @Test
    fun `part 2 - round-trips every supported number`() {
        (1..3999).forEach { assertThat(RomanNumerals.toArabic(RomanNumerals.toRoman(it))).isEqualTo(it) }
    }
}
