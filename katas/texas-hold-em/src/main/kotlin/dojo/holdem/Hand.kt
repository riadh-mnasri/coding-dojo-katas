// Copyright (c) 2026 Riadh MNASRI. Licensed under the MIT License.
package dojo.holdem

enum class Category(val label: String) {
    HIGH_CARD("High Card"), PAIR("Pair"), TWO_PAIR("Two Pair"), THREE_OF_A_KIND("Three of a Kind"),
    STRAIGHT("Straight"), FLUSH("Flush"), FULL_HOUSE("Full House"), FOUR_OF_A_KIND("Four of a Kind"),
    STRAIGHT_FLUSH("Straight Flush"),
}

data class Card(val value: Int, val suit: Char) {
    companion object {
        private const val VALUES = "23456789TJQKA"

        fun parse(text: String) = Card(VALUES.indexOf(text[0]) + 2, text[1])
    }
}

/** Une main de cinq cartes, comparable : d'abord la catégorie, puis les valeurs dans l'ordre de départage. */
class Hand(cards: List<Card>) : Comparable<Hand> {
    private val groups = cards.groupingBy { it.value }.eachCount().entries
        .sortedWith(compareByDescending<Map.Entry<Int, Int>> { it.value }.thenByDescending { it.key })
    private val shape = groups.map { it.value }

    /** La roue A-2-3-4-5 : l'as y vaut 1. */
    private val isWheel = groups.map { it.key }.toSet() == setOf(14, 2, 3, 4, 5)
    private val ordered = if (isWheel) listOf(5, 4, 3, 2, 1) else groups.map { it.key }
    private val isStraight = shape.size == 5 && ordered.first() - ordered.last() == 4
    private val isFlush = cards.map { it.suit }.toSet().size == 1

    val category: Category = when {
        isStraight && isFlush -> Category.STRAIGHT_FLUSH
        shape == listOf(4, 1) -> Category.FOUR_OF_A_KIND
        shape == listOf(3, 2) -> Category.FULL_HOUSE
        isFlush -> Category.FLUSH
        isStraight -> Category.STRAIGHT
        shape == listOf(3, 1, 1) -> Category.THREE_OF_A_KIND
        shape == listOf(2, 2, 1) -> Category.TWO_PAIR
        shape == listOf(2, 1, 1, 1) -> Category.PAIR
        else -> Category.HIGH_CARD
    }

    override fun compareTo(other: Hand): Int =
        category.compareTo(other.category).takeIf { it != 0 }
            ?: ordered.zip(other.ordered).map { (a, b) -> a.compareTo(b) }.firstOrNull { it != 0 } ?: 0

    companion object {
        /** La meilleure main de cinq cartes parmi celles du joueur et de la table. */
        fun best(cards: List<Card>): Hand = combinations(cards, 5).map(::Hand).max()

        private fun <T> combinations(items: List<T>, size: Int): List<List<T>> = when {
            size == 0 -> listOf(emptyList())
            items.size < size -> emptyList()
            else -> combinations(items.drop(1), size - 1).map { listOf(items.first()) + it } +
                combinations(items.drop(1), size)
        }
    }
}
