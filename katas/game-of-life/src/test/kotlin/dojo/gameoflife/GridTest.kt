// Copyright (c) 2026 Riadh MNASRI. Licensed under the MIT License.
package dojo.gameoflife

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

class GridTest {

    private fun grid(vararg rows: String) = Grid.parse(rows.joinToString("\n"))

    @Test
    fun `a lonely cell dies`() {
        val next = grid("...", ".*.", "...").next()

        assertThat(next).isEqualTo(grid("...", "...", "..."))
    }

    @Test
    fun `a blinker oscillates`() {
        val vertical = grid(".....", "..*..", "..*..", "..*..", ".....")
        val horizontal = grid(".....", ".....", ".***.", ".....", ".....")

        assertThat(vertical.next()).isEqualTo(horizontal)
        assertThat(horizontal.next()).isEqualTo(vertical)
    }

    @Test
    fun `cells on the edges count only the neighbours inside the grid`() {
        val next = grid("**..", "*...", "....").next()

        assertThat(next).isEqualTo(grid("**..", "**..", "...."))
    }

    @Test
    fun `a block is stable`() {
        val block = grid("....", ".**.", ".**.", "....")

        assertThat(block.next()).isEqualTo(block)
    }
}
