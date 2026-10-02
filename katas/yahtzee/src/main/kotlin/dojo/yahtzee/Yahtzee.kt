// Copyright (c) 2026 Riadh MNASRI. Licensed under the MIT License.
package dojo.yahtzee

enum class Category(val score: (List<Int>) -> Int) {
    CHANCE({ dice -> dice.sum() }),
    YAHTZEE({ dice -> if (dice.toSet().size == 1) 50 else 0 }),
    ONES(sumOf(1)),
    TWOS(sumOf(2)),
    THREES(sumOf(3)),
    FOURS(sumOf(4)),
    FIVES(sumOf(5)),
    SIXES(sumOf(6)),
    PAIR(ofAKind(2)),
    THREE_OF_A_KIND(ofAKind(3)),
    FOUR_OF_A_KIND(ofAKind(4)),
    TWO_PAIRS({ dice ->
        val pairs = dice.groupingBy { it }.eachCount().filterValues { it >= 2 }.keys
        if (pairs.size == 2) pairs.sum() * 2 else 0
    }),
}

private fun sumOf(face: Int): (List<Int>) -> Int = { dice -> dice.filter { it == face }.sum() }

/** La plus haute face présente au moins [count] fois, multipliée par [count]. */
private fun ofAKind(count: Int): (List<Int>) -> Int = { dice ->
    dice.groupingBy { it }.eachCount().filterValues { it >= count }.keys.maxOrNull()?.times(count) ?: 0
}

object Yahtzee {
    fun score(dice: List<Int>, category: Category): Int = category.score(dice)
}
