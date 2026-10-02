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
}
