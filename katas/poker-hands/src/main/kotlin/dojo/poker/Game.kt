// Copyright (c) 2026 Riadh MNASRI. Licensed under the MIT License.
package dojo.poker

/** Une ligne de partie : « Black: 5 cartes  White: 5 cartes ». */
object Game {
    private val NAMES = mapOf(10 to "10", 11 to "Jack", 12 to "Queen", 13 to "King", 14 to "Ace")

    fun judge(line: String): String {
        val (black, white) = Regex("""Black: (.+?)\s+White: (.+)""").matchEntire(line.trim())?.destructured
            ?: throw IllegalArgumentException("Expected 'Black: <5 cards>  White: <5 cards>', got '$line'")
        val blackHand = Hand.parse(black)
        val whiteHand = Hand.parse(white)
        return when {
            blackHand > whiteHand -> "Black wins. - with ${reason(blackHand, whiteHand)}"
            whiteHand > blackHand -> "White wins. - with ${reason(whiteHand, blackHand)}"
            else -> "Tie."
        }
    }

    /** Pourquoi la main gagne : sa catégorie, et la valeur qui a fait la différence. */
    private fun reason(winner: Hand, loser: Hand): String {
        val label = winner.category.name.lowercase().replace('_', ' ')
        if (winner.category == Category.FULL_HOUSE) {
            return "$label: ${name(winner.ordered[0])} over ${name(winner.ordered[1])}"
        }
        val deciding = if (winner.category != loser.category) {
            winner.ordered.first()
        } else {
            winner.ordered.zip(loser.ordered).first { (mine, theirs) -> mine != theirs }.first
        }
        return "$label: ${name(deciding)}"
    }

    private fun name(value: Int) = NAMES[value] ?: value.toString()
}
