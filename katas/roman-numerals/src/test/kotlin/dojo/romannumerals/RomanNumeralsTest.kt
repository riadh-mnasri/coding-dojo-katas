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
}
