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
}
