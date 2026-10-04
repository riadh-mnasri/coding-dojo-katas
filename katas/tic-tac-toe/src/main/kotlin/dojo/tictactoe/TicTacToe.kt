// Copyright (c) 2026 Riadh MNASRI. Licensed under the MIT License.
package dojo.tictactoe

enum class Player { X, O }

class TicTacToe {
    private val fields = mutableMapOf<Int, Player>()
    private var current = Player.X

    fun play(cell: Int) {
        require(cell !in fields) { "Field $cell is already taken by ${fields[cell]}" }
        fields[cell] = current
        current = if (current == Player.X) Player.O else Player.X
    }

    fun ownerOf(cell: Int): Player? = fields[cell]

    fun board(): String = TODO()

    fun isOver(): Boolean = winner() != null || fields.size == 9

    fun winner(): Player? = LINES.firstNotNullOfOrNull { line ->
        fields[line.first()]?.takeIf { player -> line.all { fields[it] == player } }
    }

    private companion object {
        val LINES = listOf(
            listOf(1, 2, 3), listOf(4, 5, 6), listOf(7, 8, 9),
            listOf(1, 4, 7), listOf(2, 5, 8), listOf(3, 6, 9),
            listOf(1, 5, 9), listOf(3, 5, 7),
        )
    }
}
