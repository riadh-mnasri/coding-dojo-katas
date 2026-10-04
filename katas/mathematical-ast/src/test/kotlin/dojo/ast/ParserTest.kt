// Copyright (c) 2026 Riadh MNASRI. Licensed under the MIT License.
package dojo.ast

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

class ParserTest {

    @Test
    fun `a lone number is an operand`() {
        assertThat(Mathematical.parse("3")).isEqualTo(Operand(3))
    }

    @Test
    fun `an operator takes the two previous expressions`() {
        assertThat(Mathematical.parse("3 6 +")).isEqualTo(Operation(Operator.ADD, Operand(3), Operand(6)))
    }
}
