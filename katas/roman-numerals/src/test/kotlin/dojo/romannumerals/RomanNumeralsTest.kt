// Copyright (c) 2026 Riadh MNASRI. Licensed under the MIT License.
package dojo.romannumerals

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

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
}
