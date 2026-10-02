// Copyright (c) 2026 Riadh MNASRI. Licensed under the MIT License.
package dojo.greed

object Greed {
    fun score(dice: List<Int>): Int = dice.map { die ->
        when (die) {
            1 -> 100
            5 -> 50
            else -> 0
        }
    }.sum()
}
