// Copyright (c) 2026 Riadh MNASRI. Licensed under the MIT License.
package dojo.ast

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

class PrintersTest {

    private val example = Mathematical.parse("3 6 -6 * +")

    @Test
    fun `prints the tree back in RPN`() {
        assertThat(example.accept(RpnPrinter)).isEqualTo("3 6 -6 × +")
    }

    @Test
    fun `evaluates the tree`() {
        assertThat(example.accept(Evaluator)).isEqualTo(-33L)
    }

    @Test
    fun `prints the tree in infix notation, every operation in parentheses`() {
        assertThat(example.accept(InfixPrinter)).isEqualTo("3 + (6 × -6)")
    }

    @Test
    fun `step 3 - prints infix with the minimum of parentheses`() {
        assertThat(example.accept(MinimalInfixPrinter)).isEqualTo("3 + 6 × -6")
        assertThat(Mathematical.parse("3 6 + 2 *").accept(MinimalInfixPrinter)).isEqualTo("(3 + 6) × 2")
        assertThat(Mathematical.parse("1 2 + 3 +").accept(MinimalInfixPrinter)).isEqualTo("1 + 2 + 3")
    }

    @Test
    fun `step 3 - subtraction needs parentheses on its right only`() {
        assertThat(Mathematical.parse("3 6 - 2 -").accept(MinimalInfixPrinter)).isEqualTo("3 - 6 - 2")
        assertThat(Mathematical.parse("3 6 2 - -").accept(MinimalInfixPrinter)).isEqualTo("3 - (6 - 2)")
        assertThat(Mathematical.parse("3 6 2 - -").accept(Evaluator)).isEqualTo(-1L)
    }
}
