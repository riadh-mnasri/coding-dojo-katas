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

    @Test
    fun `a trailing separator is refused`() {
        assertThat(StringCalculator.add("1,3,")).isEqualTo("Number expected but EOF found.")
    }

    @ParameterizedTest(name = "\"{0}\" = {1}")
    @CsvSource("'//;\n1;2', 3", "'//|\n1|2|3', 6", "'//sep\n2sep3', 5")
    fun `a first line can define a custom separator`(numbers: String, sum: String) {
        assertThat(StringCalculator.add(numbers)).isEqualTo(sum)
    }

    @Test
    fun `reports a separator other than the custom one`() {
        assertThat(StringCalculator.add("//|\n1|2,3")).isEqualTo("'|' expected but ',' found at position 3.")
    }

    @ParameterizedTest(name = "\"{0}\" -> {1}")
    @CsvSource("'-1,2', 'Negative not allowed : -1'", "'2,-4,-5', 'Negative not allowed : -4, -5'")
    fun `negative numbers are refused and listed`(numbers: String, message: String) {
        assertThat(StringCalculator.add(numbers)).isEqualTo(message)
    }

    @Test
    fun `reports every error, one per line`() {
        assertThat(StringCalculator.add("-1,,2")).isEqualTo("Negative not allowed : -1\nNumber expected but ',' found at position 3.")
    }

    @ParameterizedTest(name = "\"{0}\" -> {1}")
    @CsvSource("'', 1", "'2,3.5', 7", "'//;\n2;3;4', 24", "'2,-3', 'Negative not allowed : -3'", "'1,3,', 'Number expected but EOF found.'")
    fun `multiplies with the same rules`(numbers: String, result: String) {
        assertThat(StringCalculator.multiply(numbers)).isEqualTo(result)
    }

    @Test
    fun `internally, errors are a typed outcome rather than a string`() {
        val outcome = StringCalculator.compute("-1,,2", java.math.BigDecimal.ZERO, java.math.BigDecimal::add)

        assertThat(outcome).isEqualTo(
            Outcome.Failure(listOf("Negative not allowed : -1", "Number expected but ',' found at position 3.")),
        )
    }
}
