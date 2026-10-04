// Copyright (c) 2026 Riadh MNASRI. Licensed under the MIT License.
package dojo.queens

/** Une dame en ([row], [column]) est-elle attaquée par l'une des dames déjà placées sur les lignes précédentes ? */
fun isSafe(placed: List<Int>, column: Int): Boolean {
    val row = placed.size
    return placed.withIndex().none { (otherRow, otherColumn) ->
        otherColumn == column || row - otherRow == kotlin.math.abs(column - otherColumn)
    }
}
