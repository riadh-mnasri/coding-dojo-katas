// Copyright (c) 2026 Riadh MNASRI. Licensed under the MIT License.
package dojo.nearestcolor

class Palette(private vararg val colors: String) {

    fun nearest(color: String): String = colors.minBy { distance(it, color) }

    fun nearestColors(color: String): List<String> {
        val best = colors.minOf { distance(it, color) }
        return colors.filter { distance(it, color) == best }
    }

    /** Distance euclidienne au carré dans l'espace RGB : suffisante pour comparer. */
    private fun distance(a: String, b: String) =
        components(a).zip(components(b)).sumOf { (x, y) -> (x - y) * (x - y) }

    /** "F42" → [255, 68, 34] (chaque chiffre est doublé, F → FF) ; "FF4422" se lit par paires. */
    private fun components(color: String) =
        if (color.length == 3) color.map { "$it$it".toInt(16) } else color.chunked(2).map { it.toInt(16) }
}
