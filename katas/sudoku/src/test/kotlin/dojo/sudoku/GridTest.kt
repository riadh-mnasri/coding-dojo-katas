// Copyright (c) 2026 Riadh MNASRI. Licensed under the MIT License.
package dojo.sudoku

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

/** Une grille est un carré de 3 × 3 cellules, nommé de A à I ; lignes et colonnes vont de 1 à 3. */
class GridTest {

    private val discoveries = mutableListOf<Discovery>()
    private val grid = Grid("C") { discoveries += it }

    @Test
    fun `a known value cannot appear anywhere else in the grid`() {
        grid.set(row = 1, column = 1, value = 7)

        assertThat(grid.cell(1, 1).value()).isEqualTo(CellValue.Known(7))
        assertThat(grid.cell(2, 3).isPossible(7)).isFalse()
        assertThat(discoveries).containsExactly(Discovery(row = 1, column = 1, value = 7))
    }

    @Test
    fun `a cell left with a single number is discovered in turn`() {
        // Given: la cellule (3, 3) ne peut plus être que 9 ou 8
        (1..7).forEach { grid.cell(3, 3).exclude(it) }

        // When: 8 est trouvé ailleurs dans la grille
        grid.set(row = 1, column = 1, value = 8)

        // Then
        assertThat(grid.cell(3, 3).value()).isEqualTo(CellValue.Known(9))
        assertThat(discoveries).containsExactly(Discovery(1, 1, 8), Discovery(3, 3, 9))
    }
}
