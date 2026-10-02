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
}
