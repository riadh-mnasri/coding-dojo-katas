// Copyright (c) 2026 Riadh MNASRI. Licensed under the MIT License.
package dojo.minesweeper

class Field(private val rows: List<String>) {

    fun hints(): List<String> = rows.indices.map { row ->
        rows[row].indices.map { column ->
            if (isMine(row, column)) MINE else minesAround(row, column).digitToChar()
        }.joinToString("")
    }

    private fun isMine(row: Int, column: Int) = rows.getOrNull(row)?.getOrNull(column) == MINE

    private fun minesAround(row: Int, column: Int) =
        NEIGHBOURS.count { (dRow, dColumn) -> isMine(row + dRow, column + dColumn) }

    private companion object {
        const val MINE = '*'
        val NEIGHBOURS = (-1..1).flatMap { dRow -> (-1..1).map { dColumn -> dRow to dColumn } }
            .filterNot { it == 0 to 0 }
    }
}
