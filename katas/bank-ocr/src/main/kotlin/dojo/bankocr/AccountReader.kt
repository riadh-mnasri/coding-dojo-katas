// Copyright (c) 2026 Riadh MNASRI. Licensed under the MIT License.
package dojo.bankocr

object AccountReader {
    /** Chaque chiffre écrit sur 3 lignes de 3 caractères, comme le recommande l'énoncé pour la lisibilité. */
    private val DIGITS = mapOf(
        " _ " +
            "| |" +
            "|_|" to '0',
        "   " +
            "  |" +
            "  |" to '1',
        " _ " +
            " _|" +
            "|_ " to '2',
        " _ " +
            " _|" +
            " _|" to '3',
        "   " +
            "|_|" +
            "  |" to '4',
        " _ " +
            "|_ " +
            " _|" to '5',
        " _ " +
            "|_ " +
            "|_|" to '6',
        " _ " +
            "  |" +
            "  |" to '7',
        " _ " +
            "|_|" +
            "|_|" to '8',
        " _ " +
            "|_|" +
            " _|" to '9',
    )

    fun read(entry: String): String {
        val lines = entry.lines()
        return (0 until 9).map { position ->
            val cell = (0 until 3).joinToString("") { row -> lines[row].substring(position * 3, position * 3 + 3) }
            DIGITS.getValue(cell)
        }.joinToString("")
    }
}
