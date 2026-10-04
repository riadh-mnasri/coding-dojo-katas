// Copyright (c) 2026 Riadh MNASRI. Licensed under the MIT License.
package dojo.queens

import kotlin.math.abs
import kotlin.random.Random

/**
 * Heuristique des conflits minimaux : on part d'un plateau aléatoire (une dame par ligne), puis on choisit
 * au hasard une dame attaquée et on la déplace, sur sa ligne, vers la colonne la moins attaquée.
 * On recommence jusqu'à ne plus avoir de conflit. Elle ne trouve qu'une solution, mais très vite.
 */
object MinConflicts {
    fun solve(size: Int, random: Random, maxSteps: Int = 100_000): List<Int> {
        val columns = MutableList(size) { random.nextInt(size) }
        repeat(maxSteps) {
            val attacked = (0 until size).filter { row -> conflicts(columns, row, columns[row]) > 0 }
            if (attacked.isEmpty()) return columns
            val row = attacked.random(random)
            val counts = (0 until size).map { column -> conflicts(columns, row, column) }
            val fewest = counts.min()
            columns[row] = counts.indices.filter { counts[it] == fewest }.random(random)
        }
        error("No solution found in $maxSteps steps")
    }

    /** Le nombre de dames des autres lignes qui attaqueraient une dame placée en ([row], [column]). */
    private fun conflicts(columns: List<Int>, row: Int, column: Int): Int = columns.indices.count { other ->
        other != row && (columns[other] == column || abs(columns[other] - column) == abs(other - row))
    }
}
