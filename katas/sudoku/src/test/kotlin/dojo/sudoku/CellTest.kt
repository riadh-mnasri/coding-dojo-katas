// Copyright (c) 2026 Riadh MNASRI. Licensed under the MIT License.
package dojo.sudoku

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

class CellTest {

    @Test
    fun `by default every number is possible and the value is unknown`() {
        val cell = Cell()

        assertThat((1..9).all(cell::isPossible)).isTrue()
        assertThat(cell.value()).isEqualTo(CellValue.Unknown)
    }

    @Test
    fun `excluding every number but one makes the value known`() {
        val cell = Cell()

        (1..9).filter { it != 7 }.forEach(cell::exclude)

        assertThat(cell.isPossible(3)).isFalse()
        assertThat(cell.value()).isEqualTo(CellValue.Known(7))
    }
}
