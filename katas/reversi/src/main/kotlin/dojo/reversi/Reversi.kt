// Copyright (c) 2026 Riadh MNASRI. Licensed under the MIT License.
package dojo.reversi

/**
 * Coups légaux de Reversi : un coup est légal s'il retourne au moins un pion adverse,
 * c'est-à-dire s'il ferme, dans l'une des huit directions, une suite de pions adverses par un pion à soi.
 */
object Reversi {
    private const val SIZE = 8
    private val DIRECTIONS = (-1..1).flatMap { dx -> (-1..1).map { dy -> dx to dy } }.filterNot { it == 0 to 0 }

    /** Les coups sous forme de coordonnées, colonnes A à H, lignes 1 à 8 depuis le haut. */
    fun legalMoves(position: String): List<String> =
        legalSquares(position).map { (column, row) -> "${'A' + column}${row + 1}" }.sorted()

    /** Le plateau avec un `0` sur chaque coup légal. */
    fun showMoves(position: String): String {
        val squares = legalSquares(position)
        val lines = position.lines()
        val board = lines.take(SIZE).mapIndexed { row, line ->
            line.mapIndexed { column, square -> if (column to row in squares) '0' else square }.joinToString("")
        }
        return (board + lines[SIZE]).joinToString("\n")
    }

    private fun legalSquares(position: String): Set<Pair<Int, Int>> {
        val lines = position.lines()
        val board = lines.take(SIZE)
        val player = lines[SIZE].single()
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

        return (0 until SIZE).flatMap { row -> (0 until SIZE).map { column -> column to row } }
            .filter { (column, row) -> at(column, row) == '.' && DIRECTIONS.any { (dx, dy) -> flips(column, row, dx, dy) } }
            .toSet()
    }
}
