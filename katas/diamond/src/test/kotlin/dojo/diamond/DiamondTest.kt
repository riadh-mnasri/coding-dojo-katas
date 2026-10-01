// Copyright (c) 2026 Riadh MNASRI. Licensed under the MIT License.
package dojo.diamond

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

class DiamondTest {

    private val letters = 'A'..'Z'

    @Test
    fun `diamond A is a single A`() {
        assertThat(Diamond.of('A')).isEqualTo("A")
    }

    @Test
    fun `has two rows per letter before the widest, plus one`() {
        letters.forEach { letter ->
            assertThat(Diamond.of(letter).lines()).hasSize(2 * (letter - 'A') + 1)
        }
    }

    @Test
    fun `goes from A to the letter and back`() {
        letters.forEach { letter ->
            val expected = ('A'..letter).toList()
            val firstLetters = Diamond.of(letter).lines().map { it.trim().first() }
            assertThat(firstLetters).isEqualTo(expected + expected.dropLast(1).reversed())
        }
    }

    @Test
    fun `is as wide as it is high`() {
        letters.forEach { letter ->
            val rows = Diamond.of(letter).lines()
            assertThat(rows.maxOf { it.length }).isEqualTo(rows.size)
        }
    }
}
