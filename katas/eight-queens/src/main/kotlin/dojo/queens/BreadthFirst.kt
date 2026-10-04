// Copyright (c) 2026 Riadh MNASRI. Licensed under the MIT License.
package dojo.queens

/** Parcours en largeur : on garde toutes les positions partielles d'une ligne, puis on les étend toutes d'une ligne. */
object BreadthFirst {
    fun solutions(size: Int): List<List<Int>> =
        (0 until size).fold(listOf(emptyList<Int>())) { partials, _ ->
            partials.flatMap { placed -> (0 until size).filter { isSafe(placed, it) }.map { placed + it } }
        }
}
