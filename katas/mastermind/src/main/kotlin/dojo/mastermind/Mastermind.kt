// Copyright (c) 2026 Riadh MNASRI. Licensed under the MIT License.
package dojo.mastermind

enum class Color { BLUE, RED, GREEN, PINK, YELLOW, PURPLE }

data class Answer(val wellPlaced: Int, val misplaced: Int)

object Mastermind {
    fun evaluate(secret: List<Color>, guess: List<Color>): Answer {
        val pairs = secret.zip(guess)
        val (matching, others) = pairs.partition { (s, g) -> s == g }
        val secretLeft = others.groupingBy { it.first }.eachCount()
        val guessLeft = others.groupingBy { it.second }.eachCount()
        val misplaced = guessLeft.entries.sumOf { (color, count) -> minOf(count, secretLeft[color] ?: 0) }
        return Answer(matching.size, misplaced)
    }
}
