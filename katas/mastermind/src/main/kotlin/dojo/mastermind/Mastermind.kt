// Copyright (c) 2026 Riadh MNASRI. Licensed under the MIT License.
package dojo.mastermind

enum class Color { BLUE, RED, GREEN, PINK, YELLOW, PURPLE }

data class Answer(val wellPlaced: Int, val misplaced: Int)

object Mastermind {
    fun evaluate(secret: List<Color>, guess: List<Color>): Answer {
        require(secret.size == guess.size) { "Secret and guess must have the same size" }
        val (wellPlaced, unmatched) = secret.zip(guess).partition { (s, g) -> s == g }
        // Une couleur mal placée compte autant de fois qu'elle apparaît des deux côtés, pas plus.
        val secretLeft = unmatched.groupingBy { (s, _) -> s }.eachCount()
        val guessLeft = unmatched.groupingBy { (_, g) -> g }.eachCount()
        val misplaced = guessLeft.entries.sumOf { (color, count) -> minOf(count, secretLeft[color] ?: 0) }
        return Answer(wellPlaced.size, misplaced)
    }
}
