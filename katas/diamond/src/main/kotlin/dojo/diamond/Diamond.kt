// Copyright (c) 2026 Riadh MNASRI. Licensed under the MIT License.
package dojo.diamond

object Diamond {
    fun of(widest: Char): String {
        val topHalf = ('A'..widest).map { it.toString() }
        return (topHalf + topHalf.dropLast(1).reversed()).joinToString("\n")
    }
}
