// Copyright (c) 2026 Riadh MNASRI. Licensed under the MIT License.
package dojo.gameoflife

/** Grille finie : aucune vie n'existe au-delà des bords. */
data class Grid(private val cells: List<List<Boolean>>) {

    fun next(): Grid = Grid(
        cells.indices.map { row ->
            cells[row].indices.map { column -> Rules.isAliveNext(isAlive(row, column), liveNeighbours(row, column)) }
        },
    )

    private fun isAlive(row: Int, column: Int) = cells.getOrNull(row)?.getOrNull(column) ?: false

    private fun liveNeighbours(row: Int, column: Int) =
        NEIGHBOUR_OFFSETS.count { (dRow, dColumn) -> isAlive(row + dRow, column + dColumn) }

    companion object {
        private val NEIGHBOUR_OFFSETS = (-1..1).flatMap { dRow -> (-1..1).map { dColumn -> dRow to dColumn } }
            .filterNot { it == 0 to 0 }

        fun parse(text: String) = Grid(text.lines().map { line -> line.map { it == '*' } })
    }
}
