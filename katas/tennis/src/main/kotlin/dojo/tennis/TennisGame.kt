// Copyright (c) 2026 Riadh MNASRI. Licensed under the MIT License.
package dojo.tennis

import kotlin.math.abs

/**
 * Un jeu de tennis (un seul jeu, comme au tennis de la Wii).
 */
class TennisGame(private val player1: String, private val player2: String) {
    private var points1 = 0
    private var points2 = 0

    fun pointWonBy(player: String) {
        require(player == player1 || player == player2) { "$player is not playing this game" }
        check(!hasWinner()) { "The game is over: ${score()}" }
        if (player == player1) points1++ else points2++
    }

    fun score(): String = when {
        hasWinner() -> "Win for ${leader()}"
        isDeuce() -> "Deuce"
        points1 == points2 -> "${CALLS[points1]}-All"
        bothReachedForty() -> "Advantage ${leader()}"
        else -> "${CALLS[points1]}-${CALLS[points2]}"
    }

    private fun hasWinner() = maxOf(points1, points2) >= 4 && abs(points1 - points2) >= 2

    private fun isDeuce() = points1 == points2 && bothReachedForty()

    private fun bothReachedForty() = points1 >= 3 && points2 >= 3

    private fun leader() = if (points1 > points2) player1 else player2

    private companion object {
        val CALLS = listOf("Love", "Fifteen", "Thirty", "Forty")
    }
}
