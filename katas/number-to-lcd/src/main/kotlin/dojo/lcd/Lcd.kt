// Copyright (c) 2026 Riadh MNASRI. Licensed under the MIT License.
package dojo.lcd

/** Une ligne de dessin, calculée à partir des segments allumés d'un chiffre. */
private typealias Row = (Set<Char>) -> String

/**
 * Affichage façon LCD.
 *
 * Chaque chiffre est un ensemble de segments allumés :
 * ```
 *  _a_
 * b   c
 *  _d_
 * e   f
 *  _g_
 * ```
 */
object Lcd {
    private val segments = mapOf(
        '0' to "abcefg", '1' to "cf", '2' to "acdeg", '3' to "acdfg", '4' to "bcdf",
        '5' to "abdfg", '6' to "abdefg", '7' to "acf", '8' to "abcdefg", '9' to "abcdfg",
    ).mapValues { (_, lit) -> lit.toSet() }

    /** Partie 1 : la forme compacte sur 3 lignes, où les barres partagent la ligne des verticales. */
    fun render(number: Int): String = draw(
        number,
        listOf(
            { lit -> " " + bar('a' in lit, 1) + " " },
            { lit -> side('b' in lit) + bar('d' in lit, 1) + side('c' in lit) },
            { lit -> side('e' in lit) + bar('g' in lit, 1) + side('f' in lit) },
        ),
    )

    /** Partie 2 : chaque barre a sa propre ligne, soit 2 × hauteur + 3 lignes. */
    fun render(number: Int, width: Int, height: Int): String {
        val horizontal = { segment: Char -> { lit: Set<Char> -> " " + bar(segment in lit, width) + " " } }
        val vertical = { left: Char, right: Char ->
            { lit: Set<Char> -> side(left in lit) + " ".repeat(width) + side(right in lit) }
        }
        return draw(
            number,
            listOf(horizontal('a')) + List(height) { vertical('b', 'c') } +
                listOf(horizontal('d')) + List(height) { vertical('e', 'f') } +
                listOf(horizontal('g')),
        )
    }

    private fun draw(number: Int, rows: List<Row>): String {
        val digits = number.toString().map(segments::getValue)
        return rows.joinToString("\n") { row -> digits.joinToString("") { row(it) } }
    }

    private fun bar(on: Boolean, width: Int) = (if (on) "_" else " ").repeat(width)

    private fun side(on: Boolean) = if (on) "|" else " "
}
