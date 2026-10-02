// Copyright (c) 2026 Riadh MNASRI. Licensed under the MIT License.
package dojo.minesweeper

/** Le format de l'énoncé : des champs précédés de « lignes colonnes », terminés par « 0 0 ». */
object Minesweeper {

    fun solve(input: String): String {
        val lines = input.lines()
        val outputs = mutableListOf<String>()
        var cursor = 0
        while (true) {
            val (rows, columns) = lines[cursor].trim().split(" ").map(String::toInt)
            if (rows == 0 && columns == 0) break
            val field = Field(lines.subList(cursor + 1, cursor + 1 + rows))
            outputs += (listOf("Field #${outputs.size + 1}:") + field.hints()).joinToString("\n")
            cursor += rows + 1
        }
        return outputs.joinToString("\n\n")
    }
}
