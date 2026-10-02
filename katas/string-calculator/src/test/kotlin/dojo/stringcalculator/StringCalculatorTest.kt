// Copyright (c) 2026 Riadh MNASRI. Licensed under the MIT License.
package dojo.stringcalculator

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.CsvSource

class StringCalculatorTest {

    @Test
    fun `an empty input sums to 0`() {
        assertThat(StringCalculator.add("")).isEqualTo("0")
    }

    @ParameterizedTest(name = "\"{0}\" = {1}")
    @CsvSource("1, 1", "'1.1,2.2', 3.3", "'2,3', 5")
    fun `sums one or two numbers`(numbers: String, sum: String) {
        assertThat(StringCalculator.add(numbers)).isEqualTo(sum)
    }

    @Test
    fun `sums any amount of numbers`() {
        assertThat(StringCalculator.add("1,2,3,4,5.5")).isEqualTo("15.5")
    }

    @Test
    fun `newlines are separators too`() {
        assertThat(StringCalculator.add("1\n2,3")).isEqualTo("6")
    }

    @Test
    fun `reports a separator found where a number is expected`() {
        assertThat(StringCalculator.add("175.2,\n35")).isEqualTo("Number expected but '\\n' found at position 6.")
    }
}
