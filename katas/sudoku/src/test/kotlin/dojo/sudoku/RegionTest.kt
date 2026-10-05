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

    @Test
    fun `a message from the north excludes the value from the column and goes on south`() {
        val fromAbove = Message(row = 2, column = 3, value = 5, path = listOf("I"))

        region.receive(from = NORTH, message = fromAbove)

        assertThat(sent).containsExactly(SOUTH to fromAbove.copy(path = listOf("I", "C")))
        assertThat((1..3).none { region.isPossible(row = it, column = 3, value = 5) }).isTrue()
        assertThat(region.isPossible(row = 1, column = 1, value = 5)).isTrue()
    }

    @Test
    fun `a message from the east excludes the value from the row and goes on west`() {
        val fromRight = Message(row = 2, column = 3, value = 5, path = listOf("A"))

        region.receive(from = EAST, message = fromRight)

        assertThat(sent).containsExactly(WEST to fromRight.copy(path = listOf("A", "C")))
        assertThat((1..3).none { region.isPossible(row = 2, column = it, value = 5) }).isTrue()
    }

    @Test
    fun `a message that came back to a region it already went through stops there`() {
        val backHome = Message(row = 2, column = 3, value = 5, path = listOf("C", "F", "I"))

        region.receive(from = SOUTH, message = backHome)

        assertThat(sent).isEmpty()
    }
}
