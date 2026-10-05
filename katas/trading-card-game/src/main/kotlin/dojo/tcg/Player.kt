// Copyright (c) 2026 Riadh MNASRI. Licensed under the MIT License.
package dojo.tcg

/** Une carte vaut son coût en mana, et inflige autant de dégâts. Le paquet est déjà mélangé. */
class Player(val name: String, val deck: MutableList<Int>) {
    var health = 30
        private set
    var manaSlots = 0
        private set
    var mana = 0
        private set
    val hand = mutableListOf<Int>()

    init {
        repeat(3) { hand += deck.removeFirst() }
    }

    fun startTurn() {
        manaSlots = minOf(manaSlots + 1, 10)
        mana = manaSlots
        draw()
    }

    fun play(card: Int, against: Player) {
        require(card in hand) { "$name has no $card card in hand" }
        check(card <= mana) { "$name cannot afford a $card card with $mana mana" }
        mana -= card
        hand.remove(card)
        against.health -= card
    }

    /** Bleeding Out : une pioche vide coûte 1 point de vie. Overload : au-delà de 5 cartes en main, la carte piochée est défaussée. */
    private fun draw() {
        if (deck.isEmpty()) {
            health--
            return
        }
        val card = deck.removeFirst()
        if (hand.size < MAX_HAND) hand += card
    }

    private companion object {
        const val MAX_HAND = 5
    }
}
