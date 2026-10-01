// Copyright (c) 2026 Riadh MNASRI. Licensed under the MIT License.
package dojo.diamond

object Diamond {
    fun of(widest: Char): String {
        val size = widest - 'A'
        val topHalf = ('A'..widest).map { row(it, size) }
        return (topHalf + topHalf.dropLast(1).reversed()).joinToString("\n")
    }

    private fun row(letter: Char, size: Int): String {
        val index = letter - 'A'
        val outer = " ".repeat(size - index)
        return if (index == 0) "$outer$letter" else "$outer$letter${" ".repeat(2 * index - 1)}$letter"
    }
}
