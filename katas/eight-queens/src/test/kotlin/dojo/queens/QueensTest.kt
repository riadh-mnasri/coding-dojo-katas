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

    @Test
    fun `the eight-queens board has 92 solutions, all valid`() {
        val solutions = DepthFirst.solutions(8)

        // 92 est le nombre de solutions connu du problème des huit dames.
        assertThat(solutions).hasSize(92).doesNotHaveDuplicates()
        assertThat(solutions).allSatisfy { solution -> assertThat(Board(solution).isValid()).isTrue() }
    }

    @Test
    fun `a breadth-first walk finds the same solutions`() {
        assertThat(BreadthFirst.solutions(8)).containsExactlyInAnyOrderElementsOf(DepthFirst.solutions(8))
    }

    @Test
    fun `the validator rejects queens on the same column or diagonal`() {
        assertThat(Board(listOf(0, 0)).isValid()).isFalse()
        assertThat(Board(listOf(0, 1)).isValid()).isFalse()
        assertThat(Board(listOf(1, 3, 0, 2)).isValid()).isTrue()
    }

    @Test
    fun `brute force with bit masks finds the same solutions`() {
        assertThat(BruteForce.solutions(8)).containsExactlyInAnyOrderElementsOf(DepthFirst.solutions(8))
    }
}
