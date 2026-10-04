// Copyright (c) 2026 Riadh MNASRI. Licensed under the MIT License.
package dojo.tictactoe

enum class Player { X, O }

class TicTacToe {
    private val fields = mutableMapOf<Int, Player>()

    fun play(cell: Int) {
        fields[cell] = Player.X
    }

    fun ownerOf(cell: Int): Player? = fields[cell]

    fun board(): String = TODO()

    fun isOver(): Boolean = TODO()

    fun winner(): Player? = TODO()
}
