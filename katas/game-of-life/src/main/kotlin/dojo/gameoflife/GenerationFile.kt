// Copyright (c) 2026 Riadh MNASRI. Licensed under the MIT License.
package dojo.gameoflife

/** Le format texte de l'énoncé : `Generation N:`, puis `lignes colonnes`, puis la grille. */
object GenerationFile {

    fun next(input: String): String {
        val lines = input.lines()
        val generation = lines[0].removePrefix("Generation ").removeSuffix(":").toInt()
        val size = lines[1]
        val (rows, columns) = size.split(" ").map(String::toInt)
        val gridLines = lines.drop(2)
        require(gridLines.size == rows && gridLines.all { it.length == columns }) {
            "The grid does not match its declared size $rows x $columns"
        }
        val grid = Grid.parse(gridLines.joinToString("\n"))
        return listOf("Generation ${generation + 1}:", size, grid.next().render()).joinToString("\n")
    }
}
