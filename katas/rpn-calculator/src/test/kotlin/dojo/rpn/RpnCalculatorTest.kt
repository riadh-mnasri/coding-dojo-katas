// Copyright (c) 2026 Riadh MNASRI. Licensed under the MIT License.
package dojo.rpn

import org.assertj.core.api.Assertions.assertThat
import org.assertj.core.api.Assertions.assertThatThrownBy
import org.junit.jupiter.api.Test
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.CsvSource
import org.junit.jupiter.params.provider.ValueSource

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

    @ParameterizedTest(name = "{0} = {1}")
    @CsvSource("'4 2 + 3 -', 3", "'3 5 8 * 7 + *', 141")
    fun `chains expressions`(expression: String, expected: Double) {
        assertThat(calculator.evaluate(expression)).isEqualTo(expected)
    }

    @Test
    fun `SQRT takes a single operand`() {
        assertThat(calculator.evaluate("9 SQRT")).isEqualTo(3.0)
    }

    @ParameterizedTest(name = "{0} = {1}")
    @CsvSource("'5 3 4 2 9 1 MAX', 9", "'4 5 MAX 1 2 MAX *', 10")
    fun `MAX takes every value on the stack`(expression: String, expected: Double) {
        assertThat(calculator.evaluate(expression)).isEqualTo(expected)
    }

    @ParameterizedTest
    @ValueSource(strings = ["+", "1 +", "1 2", "1 0 /", "1 2 ?", "SQRT", "MAX", "1 2 + MAX"])
    fun `rejects invalid expressions`(expression: String) {
        assertThatThrownBy { calculator.evaluate(expression) }.isInstanceOf(IllegalArgumentException::class.java)
    }

    @Test
    fun `new operations can be plugged in`() {
        // Given
        val modulo = Operation { stack ->
            val right = stack.pop()
            stack.pushResult(stack.pop() % right)
        }
        val withModulo = RpnCalculator(RpnCalculator.defaultOperations + ("%" to modulo))

        // When
        val result = withModulo.evaluate("10 4 %")

        // Then
        assertThat(result).isEqualTo(2.0)
    }
}
