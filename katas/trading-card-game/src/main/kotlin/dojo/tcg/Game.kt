// Copyright (c) 2026 Riadh MNASRI. Licensed under the MIT License.
package dojo.tcg

/** La boucle de jeu, avec une stratégie simple : jouer la carte la plus chère abordable, tant qu'il y en a une. */
class Game(first: Player, second: Player) {
    var active: Player = first
        private set
    private var opponent: Player = second

    fun playTurn() {
        active.startTurn()
        while (true) {
            val card = active.hand.filter { it <= active.mana }.maxOrNull() ?: break
            active.play(card, against = opponent)
        }
        active = opponent.also { opponent = active }
    }
}
