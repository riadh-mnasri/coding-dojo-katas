// Copyright (c) 2026 Riadh MNASRI. Licensed under the MIT License.
package dojo.queens

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

/** Une solution donne, pour chaque ligne, la colonne de sa dame. */
class QueensTest {

    @Test
    fun `a one-square board has a single solution`() {
        assertThat(DepthFirst.solutions(1)).containsExactly(listOf(0))
    }

    @Test
    fun `the four-queens board has exactly two solutions`() {
        assertThat(DepthFirst.solutions(4)).containsExactlyInAnyOrder(listOf(1, 3, 0, 2), listOf(2, 0, 3, 1))
    }
}
