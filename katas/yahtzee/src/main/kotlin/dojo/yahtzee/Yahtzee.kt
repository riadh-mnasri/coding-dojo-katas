// Copyright (c) 2026 Riadh MNASRI. Licensed under the MIT License.
package dojo.yahtzee

enum class Category { CHANCE }

object Yahtzee {
    fun score(dice: List<Int>, category: Category): Int = dice.sum()
}
