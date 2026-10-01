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
}
