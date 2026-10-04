// Copyright (c) 2026 Riadh MNASRI. Licensed under the MIT License.
package dojo.queens

import kotlin.math.abs

/** Un plateau complet, vérifié indépendamment des algorithmes de recherche : aucune paire de dames ne se prend. */
class Board(private val columns: List<Int>) {
    fun isValid(): Boolean = columns.indices.all { a ->
        (a + 1 until columns.size).all { b ->
            columns[a] != columns[b] && abs(columns[a] - columns[b]) != b - a
        }
    }
}
