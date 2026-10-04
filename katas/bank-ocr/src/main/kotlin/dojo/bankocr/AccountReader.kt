// Copyright (c) 2026 Riadh MNASRI. Licensed under the MIT License.
package dojo.bankocr

object AccountReader {
    private val DIGITS = mapOf(
        " _ " +
            "| |" +
            "|_|" to '0',
    )

    fun read(entry: String): String {
        val lines = entry.lines()
        return (0 until 9).map { position ->
            val cell = (0 until 3).joinToString("") { row -> lines[row].substring(position * 3, position * 3 + 3) }
            DIGITS.getValue(cell)
        }.joinToString("")
    }
}
