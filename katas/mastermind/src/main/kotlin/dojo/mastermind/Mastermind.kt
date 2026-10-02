// Copyright (c) 2026 Riadh MNASRI. Licensed under the MIT License.
package dojo.mastermind

enum class Color { BLUE, RED, GREEN, PINK, YELLOW, PURPLE }

data class Answer(val wellPlaced: Int, val misplaced: Int)

object Mastermind {
    fun evaluate(secret: List<Color>, guess: List<Color>): Answer = Answer(0, 0)
}
