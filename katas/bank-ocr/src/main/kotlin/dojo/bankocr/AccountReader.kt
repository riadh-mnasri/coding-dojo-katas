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

    fun read(entry: String): String = cellsOf(entry).map { DIGITS[it] ?: '?' }.joinToString("")

    private fun cellsOf(entry: String): List<String> {
        val lines = entry.lines()
        return (0 until 9).map { position ->
            (0 until 3).joinToString("") { row -> lines[row].substring(position * 3, position * 3 + 3) }
        }
    }

    /** (d1 + 2×d2 + ... + 9×d9) mod 11 = 0, où d1 est le chiffre le plus à droite. */
    fun isValid(account: String): Boolean =
        account.reversed().withIndex().sumOf { (index, digit) -> (index + 1) * digit.digitToInt() } % 11 == 0

    fun report(entry: String): String = reportNumber(read(entry))

    /** Un fichier est une suite d'entrées de 4 lignes (la quatrième est blanche). */
    fun reportFile(file: String): String =
        file.lines().chunked(4).filter { it.size >= 3 }.joinToString("\n") { report(it.joinToString("\n")) }

    fun reportNumber(account: String): String = when {
        '?' in account -> "$account ILL"
        !isValid(account) -> "$account ERR"
        else -> account
    }

    /** User story 4 : si le numéro est illisible ou faux, on essaie d'ajouter ou de retirer un seul trait. */
    fun reportWithGuesses(entry: String): String {
        val cells = cellsOf(entry)
        val account = cells.map { DIGITS[it] ?: '?' }.joinToString("")
        if ('?' !in account && isValid(account)) return account
        val guesses = cells.indices
            .flatMap { position -> oneStrokeAway(cells[position]).map { account.replaceRange(position, position + 1, "$it") } }
            .filter { '?' !in it && isValid(it) }
            .distinct()
            .sorted()
        return when (guesses.size) {
            0 -> "$account ILL"
            1 -> guesses.single()
            else -> "$account AMB ${guesses.joinToString(", ", "[", "]") { "'$it'" }}"
        }
    }

    /** Les chiffres dont le dessin diffère de [cell] par un seul trait (un `|` ou un `_` en plus ou en moins). */
    private fun oneStrokeAway(cell: String): List<Char> = DIGITS.filterKeys { pattern ->
        pattern.indices.count { pattern[it] != cell[it] } == 1
    }.values.toList()
}
