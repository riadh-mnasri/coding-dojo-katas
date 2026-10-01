// Copyright (c) 2026 Riadh MNASRI. Licensed under the MIT License.
package dojo.rpn

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.CsvSource

class RpnCalculatorTest {

    private val calculator = RpnCalculator()

    @Test
    fun `a number evaluates to itself`() {
        assertThat(calculator.evaluate("42")).isEqualTo(42.0)
    }

    @Test
    fun `adds the two previous values`() {
        assertThat(calculator.evaluate("1 2 +")).isEqualTo(3.0)
    }

    @ParameterizedTest(name = "{0} = {1}")
    @CsvSource("'5 3 -', 2", "'4 3 *', 12", "'20 5 /', 4")
    fun `applies the operator to the two previous values in order`(expression: String, expected: Double) {
        assertThat(calculator.evaluate(expression)).isEqualTo(expected)
    }
}
