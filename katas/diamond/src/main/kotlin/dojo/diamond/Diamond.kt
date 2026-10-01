// Copyright (c) 2026 Riadh MNASRI. Licensed under the MIT License.
package dojo.diamond

object Diamond {
    fun of(widest: Char): String {
        val topHalf = ('A'..widest).map { row(it) }
        return (topHalf + topHalf.dropLast(1).reversed()).joinToString("\n")
    }

    private fun row(letter: Char): String {
        val index = letter - 'A'
        return if (index == 0) "$letter" else "$letter${" ".repeat(2 * index - 1)}$letter"
    }
}
