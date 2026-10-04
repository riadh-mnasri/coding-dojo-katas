// Copyright (c) 2026 Riadh MNASRI. Licensed under the MIT License.
package dojo.holdem

/**
 * Une ligne par joueur : ses deux cartes puis celles de la table qu'il a vues.
 * Un joueur couché (moins de 7 cartes) n'est pas classé.
 */
object TexasHoldEm {
    private const val FULL_HAND = 7

    fun announce(input: String): String {
        val players = input.lines().map { line -> line.trim().split(" ").map(Card::parse) }
        val hands = players.map { cards -> if (cards.size == FULL_HAND) Hand.best(cards) else null }
        val best = hands.filterNotNull().maxOrNull()
        return input.lines().zip(hands).joinToString("\n") { (line, hand) ->
            when {
                hand == null -> line.trim()
                hand.compareTo(best!!) == 0 -> "${line.trim()} ${hand.category.label} (winner)"
                else -> "${line.trim()} ${hand.category.label}"
            }
        }
    }

    fun rank(cards: String): String = Hand.best(cards.split(" ").map(Card::parse)).category.label
}
