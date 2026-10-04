// Copyright (c) 2026 Riadh MNASRI. Licensed under the MIT License.
package dojo.queens

/** Parcours en profondeur : une dame par ligne, en revenant en arrière dès qu'une ligne n'a plus de case sûre. */
object DepthFirst {
    fun solutions(size: Int): List<List<Int>> = extend(emptyList(), size)

    private fun extend(placed: List<Int>, size: Int): List<List<Int>> =
        if (placed.size == size) {
            listOf(placed)
        } else {
            (0 until size).filter { isSafe(placed, it) }.flatMap { extend(placed + it, size) }
        }
}
