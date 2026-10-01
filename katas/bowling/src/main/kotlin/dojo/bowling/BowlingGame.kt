// Copyright (c) 2026 Riadh MNASRI. Licensed under the MIT License.
package dojo.bowling

class BowlingGame {
    private val rolls = mutableListOf<Int>()

    fun roll(pins: Int) {
        rolls += pins
    }

    fun score(): Int = rolls.sum()
}
