// Copyright (c) 2026 Riadh MNASRI. Licensed under the MIT License.
package dojo.greed

object Greed {
    fun score(dice: List<Int>): Int = dice.groupingBy { it }.eachCount().map { (face, count) ->
        if (count >= 3) tripleScore(face) + (count - 3) * singleScore(face) else count * singleScore(face)
    }.sum()

    private fun tripleScore(face: Int) = if (face == 1) 1000 else face * 100

    private fun singleScore(face: Int) = when (face) {
        1 -> 100
        5 -> 50
        else -> 0
    }
}
