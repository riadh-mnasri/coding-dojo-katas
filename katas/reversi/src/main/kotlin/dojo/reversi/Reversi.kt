// Copyright (c) 2026 Riadh MNASRI. Licensed under the MIT License.
package dojo.reversi

object Reversi {
    private val DIRECTIONS = listOf(-1 to 0, 1 to 0)

    fun legalMoves(position: String): List<String> {
        val lines = position.lines()
        val board = lines.take(8)
        val player = lines[8].single()
        val opponent = if (player == 'B') 'W' else 'B'
        fun at(column: Int, row: Int) = board.getOrNull(row)?.getOrNull(column)

        fun flips(column: Int, row: Int, dx: Int, dy: Int): Boolean {
            var x = column + dx
            var y = row + dy
            var seen = 0
            while (at(x, y) == opponent) {
                x += dx
                y += dy
                seen++
            }
            return seen > 0 && at(x, y) == player
        }

        return (0 until 8).flatMap { row -> (0 until 8).map { column -> column to row } }
            .filter { (column, row) -> at(column, row) == '.' && DIRECTIONS.any { (dx, dy) -> flips(column, row, dx, dy) } }
            .map { (column, row) -> "${'A' + column}${row + 1}" }
            .sorted()
    }
}
