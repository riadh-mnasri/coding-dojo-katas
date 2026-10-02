// Copyright (c) 2026 Riadh MNASRI. Licensed under the MIT License.
package dojo.greed

/** Score d'un lancer de Greed (jusqu'à six dés). */
object Greed {
    private const val STRAIGHT = 1200
    private const val THREE_PAIRS = 800

    fun score(dice: List<Int>): Int {
        require(dice.size <= 6) { "Greed is played with up to 6 dice, got ${dice.size}" }
        require(dice.all { it in 1..6 }) { "A die shows 1 to 6, got $dice" }
        val counts = dice.groupingBy { it }.eachCount()
        return when {
            counts.size == 6 -> STRAIGHT
            counts.size == 3 && counts.values.all { it == 2 } -> THREE_PAIRS
            else -> counts.map { (face, count) -> scoreOf(face, count) }.sum()
        }
    }

    /** Un brelan compte selon la face ; chaque dé identique en plus double le score du brelan. */
    private fun scoreOf(face: Int, count: Int) =
        if (count >= 3) tripleScore(face) shl (count - 3) else count * singleScore(face)

    private fun tripleScore(face: Int) = if (face == 1) 1000 else face * 100

    private fun singleScore(face: Int) = when (face) {
        1 -> 100
        5 -> 50
        else -> 0
    }
}
