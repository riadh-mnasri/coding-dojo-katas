// Copyright (c) 2026 Riadh MNASRI. Licensed under the MIT License.
package dojo.yahtzee

enum class Category { CHANCE, YAHTZEE }

object Yahtzee {
    fun score(dice: List<Int>, category: Category): Int = when (category) {
        Category.CHANCE -> dice.sum()
        Category.YAHTZEE -> if (dice.toSet().size == 1) 50 else 0
    }
}
