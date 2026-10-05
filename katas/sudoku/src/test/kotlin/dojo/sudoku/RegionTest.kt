// Copyright (c) 2026 Riadh MNASRI. Licensed under the MIT License.
package dojo.sudoku

import dojo.sudoku.Direction.EAST
import dojo.sudoku.Direction.NORTH
import dojo.sudoku.Direction.SOUTH
import dojo.sudoku.Direction.WEST
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

class RegionTest {

    private val sent = mutableListOf<Pair<Direction, Message>>()
    private val shown = mutableListOf<Pair<String, Discovery>>()
    private val region = Region("C", send = { output, message -> sent += output to message }, display = { name, d -> shown += name to d })

    @Test
    fun `a discovery goes to the display and to every output`() {
        region.init(row = 1, column = 1, value = 7)

        assertThat(shown).containsExactly("C" to Discovery(1, 1, 7))
        val message = Message(row = 1, column = 1, value = 7, path = listOf("C"))
        assertThat(sent).containsExactlyInAnyOrder(NORTH to message, EAST to message, SOUTH to message, WEST to message)
    }
}
