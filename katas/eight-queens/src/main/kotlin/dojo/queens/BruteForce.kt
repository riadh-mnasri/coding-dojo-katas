// Copyright (c) 2026 Riadh MNASRI. Licensed under the MIT License.
package dojo.queens

/**
 * Force brute, avec l'astuce de l'énoncé.
 *
 * Énumérer les permutations des colonnes règle déjà lignes et colonnes (une dame par ligne et par colonne).
 * Chaque ligne devient un masque d'un seul bit ; décalée d'autant de crans que son numéro de ligne, elle
 * tombe sur sa diagonale. Si les huit masques décalés, combinés par OU, gardent huit bits distincts,
 * aucune diagonale n'est partagée. On fait de même avec le décalage inverse pour les anti-diagonales.
 */
object BruteForce {
    fun solutions(size: Int): List<List<Int>> = permutations((0 until size).toList()).filter { noSharedDiagonal(it) }

    private fun noSharedDiagonal(columns: List<Int>): Boolean {
        val size = columns.size
        val diagonals = columns.foldIndexed(0L) { row, mask, column -> mask or (bit(column, size) shl row) }
        val antiDiagonals = columns.foldIndexed(0L) { row, mask, column -> mask or (bit(column, size) shl (size - 1 - row)) }
        return diagonals.countOneBits() == size && antiDiagonals.countOneBits() == size
    }

    private fun bit(column: Int, size: Int) = 1L shl (size - 1 - column)

    private fun permutations(items: List<Int>): List<List<Int>> =
        if (items.size <= 1) listOf(items) else items.flatMap { item -> permutations(items - item).map { listOf(item) + it } }
}
