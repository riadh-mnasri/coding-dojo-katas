// Copyright (c) 2026 Riadh MNASRI. Licensed under the MIT License.
package dojo.gameoflife

/** Le format texte de l'énoncé : `Generation N:`, puis `lignes colonnes`, puis la grille. */
object GenerationFile {

    fun next(input: String): String {
        val lines = input.lines()
        val generation = lines[0].removePrefix("Generation ").removeSuffix(":").toInt()
        val size = lines[1]
        val grid = Grid.parse(lines.drop(2).joinToString("\n"))
        return listOf("Generation ${generation + 1}:", size, grid.next().render()).joinToString("\n")
    }
}
