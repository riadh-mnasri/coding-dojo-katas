// Copyright (c) 2026 Riadh MNASRI. Licensed under the MIT License.
package dojo.poker

class Hand private constructor(values: List<Int>) : Comparable<Hand> {

    /** Les groupes de valeurs, des plus nombreux aux plus forts : une paire de 3 passe devant un roi seul. */
    private val groups = values.groupingBy { it }.eachCount().entries
        .sortedWith(compareByDescending<Map.Entry<Int, Int>> { it.value }.thenByDescending { it.key })
    private val shape = groups.map { it.value }
    private val ordered = groups.map { it.key }

    override fun compareTo(other: Hand): Int =
        compareLists(shape, other.shape).takeIf { it != 0 } ?: compareLists(ordered, other.ordered)

    private fun compareLists(mine: List<Int>, theirs: List<Int>) =
        mine.zip(theirs).map { (a, b) -> a.compareTo(b) }.firstOrNull { it != 0 } ?: 0

    companion object {
        private const val VALUES = "23456789TJQKA"

        fun parse(cards: String) = Hand(cards.split(" ").map { VALUES.indexOf(it[0]) + 2 })
    }
}
