// Copyright (c) 2026 Riadh MNASRI. Licensed under the MIT License.
package dojo.poker

class Hand private constructor(private val values: List<Int>) : Comparable<Hand> {

    private val descending = values.sortedDescending()

    override fun compareTo(other: Hand): Int =
        descending.zip(other.descending).map { (mine, theirs) -> mine.compareTo(theirs) }.firstOrNull { it != 0 } ?: 0

    companion object {
        private const val VALUES = "23456789TJQKA"

        fun parse(cards: String) = Hand(cards.split(" ").map { VALUES.indexOf(it[0]) + 2 })
    }
}
