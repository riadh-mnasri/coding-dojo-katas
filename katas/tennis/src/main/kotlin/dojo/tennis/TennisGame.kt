// Copyright (c) 2026 Riadh MNASRI. Licensed under the MIT License.
package dojo.tennis

class TennisGame(private val player1: String, private val player2: String) {
    private var points1 = 0
    private var points2 = 0

    fun pointWonBy(player: String) {
        if (player == player1) points1++ else points2++
    }

    fun score(): String =
        if (points1 == 0 && points2 == 0) "Love-All" else "${NAMES[points1]}-${NAMES[points2]}"

    private companion object {
        val NAMES = listOf("Love", "Fifteen", "Thirty", "Forty")
    }
}
