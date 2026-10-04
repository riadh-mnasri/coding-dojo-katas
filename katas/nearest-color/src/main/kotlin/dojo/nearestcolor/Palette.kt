// Copyright (c) 2026 Riadh MNASRI. Licensed under the MIT License.
package dojo.nearestcolor

class Palette(private vararg val colors: String) {

    init {
        require(colors.isNotEmpty()) { "A palette needs at least one color" }
        colors.forEach(::components)
    }

    fun nearest(color: String): String = nearestColors(color).first()

    /** Partie 2 : toutes les couleurs à égalité de distance minimale, dans l'ordre de la palette. */
    fun nearestColors(color: String): List<String> = closest(color) { distances -> distances.min() }

    /** Bonus : les couleurs les plus éloignées. */
    fun farthestColors(color: String): List<String> = closest(color) { distances -> distances.max() }

    private fun closest(color: String, pick: (List<Int>) -> Int): List<String> {
        val distances = colors.map { distance(it, color) }
        val target = pick(distances)
        return colors.filterIndexed { index, _ -> distances[index] == target }
    }

    /** Distance euclidienne au carré dans l'espace RGB : suffisante pour comparer. */
    private fun distance(a: String, b: String) =
        components(a).zip(components(b)).sumOf { (x, y) -> (x - y) * (x - y) }

    /** "F42" → [255, 68, 34] (chaque chiffre est doublé, F → FF) ; "FF4422" se lit par paires. */
    private fun components(color: String): List<Int> {
        require(color.length in setOf(3, 6) && color.all { it.isDigit() || it.uppercaseChar() in 'A'..'F' }) {
            "A color is 3 or 6 hexadecimal digits, got '$color'"
        }
        return if (color.length == 3) color.map { "$it$it".toInt(16) } else color.chunked(2).map { it.toInt(16) }
    }
}
