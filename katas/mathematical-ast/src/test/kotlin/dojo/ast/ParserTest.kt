// Copyright (c) 2026 Riadh MNASRI. Licensed under the MIT License.
package dojo.ast

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

class ParserTest {

    @Test
    fun `a lone number is an operand`() {
        assertThat(Mathematical.parse("3")).isEqualTo(Operand(3))
    }
}
