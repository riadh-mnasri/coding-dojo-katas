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
        manaSlots++
        mana = manaSlots
        hand += deck.removeFirst()
    }
}
