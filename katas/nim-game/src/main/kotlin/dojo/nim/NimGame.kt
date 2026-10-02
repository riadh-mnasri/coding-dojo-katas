// Copyright (c) 2026 Riadh MNASRI. Licensed under the MIT License.
package dojo.nim

/**
 * Jeu de Nim à deux joueurs, variante des allumettes : chacun prend 1 à 3 allumettes,
 * celui qui prend la dernière perd.
 */
class NimGame(private val first: String, private val second: String, sticks: Int = 10) {

    init {
        require(sticks > 0) { "A game needs at least one stick" }
    }

    var sticks = sticks
        private set
    var currentPlayer = first
        private set

    /** Celui qui prend la dernière allumette perd : quand il n'en reste plus, c'est au gagnant de jouer. */
    val winner: String? get() = if (this.sticks == 0) currentPlayer else null

    fun take(count: Int) {
        check(winner == null) { "The game is over, $winner won" }
        require(count in 1..3) { "A player takes 1 to 3 sticks, not $count" }
        require(count <= this.sticks) { "Only ${this.sticks} sticks left" }
        this.sticks -= count
        currentPlayer = if (currentPlayer == first) second else first
    }
}
