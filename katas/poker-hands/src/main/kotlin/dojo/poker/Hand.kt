// Copyright (c) 2026 Riadh MNASRI. Licensed under the MIT License.
package dojo.poker

enum class Category { HIGH_CARD, PAIR, TWO_PAIRS, THREE_OF_A_KIND, STRAIGHT, FLUSH, FULL_HOUSE, FOUR_OF_A_KIND }

class Hand private constructor(values: List<Int>, suits: List<Char>) : Comparable<Hand> {

    /** Les groupes de valeurs, des plus nombreux aux plus forts : une paire de 3 passe devant un roi seul. */
    private val groups = values.groupingBy { it }.eachCount().entries
        .sortedWith(compareByDescending<Map.Entry<Int, Int>> { it.value }.thenByDescending { it.key })
    private val shape = groups.map { it.value }
    private val ordered = groups.map { it.key }

    val category: Category = when {
        shape == listOf(4, 1) -> Category.FOUR_OF_A_KIND
        shape == listOf(3, 2) -> Category.FULL_HOUSE
        suits.toSet().size == 1 -> Category.FLUSH
        shape.size == 5 && ordered.first() - ordered.last() == 4 -> Category.STRAIGHT
        shape == listOf(3, 1, 1) -> Category.THREE_OF_A_KIND
        shape == listOf(2, 2, 1) -> Category.TWO_PAIRS
        shape == listOf(2, 1, 1, 1) -> Category.PAIR
        else -> Category.HIGH_CARD
    }

    override fun compareTo(other: Hand): Int =
        category.compareTo(other.category).takeIf { it != 0 } ?: compareLists(ordered, other.ordered)

    private fun compareLists(mine: List<Int>, theirs: List<Int>) =
        mine.zip(theirs).map { (a, b) -> a.compareTo(b) }.firstOrNull { it != 0 } ?: 0

    companion object {
        private const val VALUES = "23456789TJQKA"

        fun parse(cards: String): Hand {
            val parsed = cards.split(" ")
            return Hand(parsed.map { VALUES.indexOf(it[0]) + 2 }, parsed.map { it[1] })
        }
    }
}
