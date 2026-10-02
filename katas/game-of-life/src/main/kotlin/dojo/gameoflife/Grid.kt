// Copyright (c) 2026 Riadh MNASRI. Licensed under the MIT License.
package dojo.gameoflife

data class Grid(private val cells: List<List<Boolean>>) {

    fun next(): Grid = Grid(cells.map { row -> row.map { false } })

    companion object {
        fun parse(text: String) = Grid(text.lines().map { line -> line.map { it == '*' } })
    }
}
