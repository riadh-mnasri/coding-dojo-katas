// Copyright (c) 2026 Riadh MNASRI. Licensed under the MIT License.
package dojo.tennis

class TennisGame(private val player1: String, private val player2: String) {
    private var points1 = 0
    private var points2 = 0

    fun pointWonBy(player: String) {
        if (player == player1) points1++ else points2++
    }

    fun score(): String = when {
        (points1 >= 4 || points2 >= 4) && kotlin.math.abs(points1 - points2) >= 2 ->
            "Win for ${if (points1 > points2) player1 else player2}"
        points1 == points2 && points1 >= 3 -> "Deuce"
        points1 == points2 -> "${NAMES[points1]}-All"
        points1 >= 3 && points2 >= 3 -> "Advantage ${if (points1 > points2) player1 else player2}"
        else -> "${NAMES[points1]}-${NAMES[points2]}"
    }

    private companion object {
        val NAMES = listOf("Love", "Fifteen", "Thirty", "Forty")
    }
}
