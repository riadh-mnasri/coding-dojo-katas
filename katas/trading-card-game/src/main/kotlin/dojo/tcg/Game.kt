// Copyright (c) 2026 Riadh MNASRI. Licensed under the MIT License.
package dojo.tcg

/** La boucle de jeu, avec une stratégie simple : jouer la carte la plus chère abordable, tant qu'il y en a une. */
class Game(first: Player, second: Player) {
    var active: Player = first
        private set
    private var opponent: Player = second

    var winner: Player? = null
        private set

    fun playTurn() {
        if (winner != null) return
        active.startTurn()
        if (active.health <= 0) {
            winner = opponent
            return
        }
        while (true) {
            val card = active.hand.filter { it <= active.mana }.maxOrNull() ?: break
            active.play(card, against = opponent)
            if (opponent.health <= 0) {
                winner = active
                return
            }
        }
        active = opponent.also { opponent = active }
    }

    companion object {
        /** Le paquet de 20 cartes de l'énoncé. */
        val STANDARD_DECK = listOf(0, 0, 1, 1, 2, 2, 2, 3, 3, 3, 3, 4, 4, 4, 5, 5, 6, 6, 7, 8)

        fun withStandardDecks(first: String, second: String, random: kotlin.random.Random): Game = Game(
            Player(first, STANDARD_DECK.shuffled(random).toMutableList()),
            Player(second, STANDARD_DECK.shuffled(random).toMutableList()),
        )
    }
}
