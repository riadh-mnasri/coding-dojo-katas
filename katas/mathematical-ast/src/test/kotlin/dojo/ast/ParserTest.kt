// Copyright (c) 2026 Riadh MNASRI. Licensed under the MIT License.
package dojo.ast

import org.assertj.core.api.Assertions.assertThat
import org.assertj.core.api.Assertions.assertThatThrownBy
import org.junit.jupiter.api.Test
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.ValueSource

class ParserTest {

    @Test
    fun `a lone number is an operand`() {
        assertThat(Mathematical.parse("3")).isEqualTo(Operand(3))
    }

    @Test
    fun `an operator takes the two previous expressions`() {
        assertThat(Mathematical.parse("3 6 +")).isEqualTo(Operation(Operator.ADD, Operand(3), Operand(6)))
    }

    @Test
    fun `parses the nested example of the kata`() {
        val expected = Operation(Operator.ADD, Operand(3), Operation(Operator.MULTIPLY, Operand(6), Operand(-6)))

        assertThat(Mathematical.parse("3 6 -6 * +")).isEqualTo(expected)
        assertThat(Mathematical.parse("3 6 -6 × +")).isEqualTo(expected)
    }

    @ParameterizedTest
    @ValueSource(strings = ["+", "1 +", "1 2", "1 2 %", ""])
    fun `malformed expressions are rejected`(rpn: String) {
        assertThatThrownBy { Mathematical.parse(rpn) }.isInstanceOf(IllegalArgumentException::class.java)
    }
}
