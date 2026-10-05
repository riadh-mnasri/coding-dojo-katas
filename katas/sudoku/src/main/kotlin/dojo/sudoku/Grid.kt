// Copyright (c) 2026 Riadh MNASRI. Licensed under the MIT License.
package dojo.sudoku

/** Une valeur découverte dans une grille, en coordonnées locales (1 à 3). */
data class Discovery(val row: Int, val column: Int, val value: Int)

/**
 * Une grille de 3 × 3 cellules. Règle : une valeur connue dans une cellule est exclue des autres.
 * Chaque cellule qui devient connue est signalée une seule fois à [onDiscovery], puis propagée.
 */
class Grid(val name: String, private val onDiscovery: (Discovery) -> Unit) {
    private val cells = List(3) { List(3) { Cell() } }
    private val reported = mutableSetOf<Pair<Int, Int>>()

    fun cell(row: Int, column: Int): Cell = cells[row - 1][column - 1]

    fun set(row: Int, column: Int, value: Int) {
        (1..9).filter { it != value }.forEach { exclude(row, column, it) }
    }

    fun exclude(row: Int, column: Int, value: Int) {
        val cell = cell(row, column)
        if (!cell.isPossible(value)) return
        cell.exclude(value)
        (cell.value() as? CellValue.Known)?.let { discovered(row, column, it.number) }
    }

    private fun discovered(row: Int, column: Int, value: Int) {
        if (!reported.add(row to column)) return
        onDiscovery(Discovery(row, column, value))
        positions().filter { it != row to column }.forEach { (r, c) -> exclude(r, c, value) }
    }

    private fun positions() = (1..3).flatMap { r -> (1..3).map { c -> r to c } }
}
