// Copyright (c) 2026 Riadh MNASRI. Licensed under the MIT License.
package dojo.romannumerals

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

class RomanNumeralsTest {

    @Test
    fun `1 is I`() {
        assertThat(RomanNumerals.toRoman(1)).isEqualTo("I")
    }
}
