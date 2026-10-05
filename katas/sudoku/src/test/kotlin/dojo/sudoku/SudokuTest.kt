// Copyright (c) 2026 Riadh MNASRI. Licensed under the MIT License.
package dojo.sudoku

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.runBlocking
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.RepeatedTest

class SudokuTest {

    private val puzzle = """
        530070000
        600195000
        098000060
        800060003
        400803001
        700020006
        060000280
        000419005
        000080079
    """.trimIndent()

    /** Solution calculée à part, par un solveur Python indépendant appliquant les mêmes règles. */
    private val solution = """
        534678912
        672195348
        198342567
        859761423
        426853791
        713924856
        961537284
        287419635
        345286179
    """.trimIndent()

    /** Répété : avec de vrais threads, l'ordre des messages change d'une exécution à l'autre. */
    @RepeatedTest(20)
    fun `nine regions exchanging messages concurrently solve the puzzle`() {
        val solved = runBlocking(Dispatchers.Default) { Sudoku.solve(puzzle) }

        assertThat(solved).isEqualTo(solution)
    }
}
