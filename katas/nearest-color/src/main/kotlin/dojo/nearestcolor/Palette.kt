// Copyright (c) 2026 Riadh MNASRI. Licensed under the MIT License.
package dojo.nearestcolor

class Palette(private vararg val colors: String) {

    fun nearest(color: String): String = colors.minBy { distance(it, color) }

    /** Distance euclidienne au carré dans l'espace RGB : suffisante pour comparer. */
    private fun distance(a: String, b: String) =
        components(a).zip(components(b)).sumOf { (x, y) -> (x - y) * (x - y) }

    /** "F42" → [255, 68, 34] : chaque chiffre hexadécimal est doublé (F → FF). */
    private fun components(color: String) = color.map { "$it$it".toInt(16) }
}
