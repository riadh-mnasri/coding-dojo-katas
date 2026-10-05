// Copyright (c) 2026 Riadh MNASRI. Licensed under the MIT License.
package dojo.sudoku

/** Une valeur découverte dans une grille, en coordonnées locales (1 à 3). */
data class Discovery(val row: Int, val column: Int, val value: Int)

class Grid(val name: String, private val onDiscovery: (Discovery) -> Unit) {
    private val cells = List(3) { List(3) { Cell() } }

    fun cell(row: Int, column: Int): Cell = cells[row - 1][column - 1]

    fun set(row: Int, column: Int, value: Int) {
        (1..9).filter { it != value }.forEach(cell(row, column)::exclude)
        positions().filter { it != row to column }.forEach { (r, c) -> cell(r, c).exclude(value) }
        onDiscovery(Discovery(row, column, value))
    }

    private fun positions() = (1..3).flatMap { r -> (1..3).map { c -> r to c } }
}
