// Copyright (c) 2026 Riadh MNASRI. Licensed under the MIT License.
package dojo.lcd

object Lcd {
    private val glyphs = mapOf(
        '0' to listOf(" _ ", "| |", "|_|"),
        '1' to listOf("   ", "  |", "  |"),
        '2' to listOf(" _ ", " _|", "|_ "),
        '3' to listOf(" _ ", " _|", " _|"),
        '4' to listOf("   ", "|_|", "  |"),
        '5' to listOf(" _ ", "|_ ", " _|"),
        '6' to listOf(" _ ", "|_ ", "|_|"),
        '7' to listOf(" _ ", "  |", "  |"),
        '8' to listOf(" _ ", "|_|", "|_|"),
        '9' to listOf(" _ ", "|_|", " _|"),
    )

    fun render(number: Int): String {
        val digits = number.toString().map(glyphs::getValue)
        return (0 until 3).joinToString("\n") { row -> digits.joinToString("") { it[row] } }
    }

    /** Segments allumés : haut, haut-gauche, haut-droite, milieu, bas-gauche, bas-droite, bas. */
    private val segments = mapOf(
        '0' to "abcefg".toSet(), '1' to "cf".toSet(), '2' to "acdeg".toSet(), '3' to "acdfg".toSet(),
        '4' to "bcdf".toSet(), '5' to "abdfg".toSet(), '6' to "abdefg".toSet(), '7' to "acf".toSet(),
        '8' to "abcdefg".toSet(), '9' to "abcdfg".toSet(),
    )

    fun render(number: Int, width: Int, height: Int): String {
        val digits = number.toString().map(segments::getValue)
        fun horizontal(on: Boolean) = " " + (if (on) "_" else " ").repeat(width) + " "
        fun vertical(left: Boolean, right: Boolean) = (if (left) "|" else " ") + " ".repeat(width) + (if (right) "|" else " ")
        val rows = listOf<(Set<Char>) -> String>({ horizontal('a' in it) }) +
            List(height) { { s: Set<Char> -> vertical('b' in s, 'c' in s) } } +
            listOf({ s: Set<Char> -> horizontal('d' in s) }) +
            List(height) { { s: Set<Char> -> vertical('e' in s, 'f' in s) } } +
            listOf({ s: Set<Char> -> horizontal('g' in s) })
        return rows.joinToString("\n") { row -> digits.joinToString("") { row(it) } }
    }
}
