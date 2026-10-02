// Copyright (c) 2026 Riadh MNASRI. Licensed under the MIT License.
package dojo.mastermind

enum class Color { BLUE, RED, GREEN, PINK, YELLOW, PURPLE }

data class Answer(val wellPlaced: Int, val misplaced: Int)

object Mastermind {
    fun evaluate(secret: List<Color>, guess: List<Color>): Answer {
        val wellPlaced = secret.zip(guess).count { (s, g) -> s == g }
        val misplaced = guess.indices.count { i -> guess[i] != secret[i] && guess[i] in secret }
        return Answer(wellPlaced, misplaced)
    }
}
