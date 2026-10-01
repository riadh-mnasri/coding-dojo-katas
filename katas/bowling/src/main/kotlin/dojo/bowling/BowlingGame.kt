// Copyright (c) 2026 Riadh MNASRI. Licensed under the MIT License.
package dojo.bowling

class BowlingGame {
    private val rolls = mutableListOf<Int>()

    fun roll(pins: Int) {
        rolls += pins
    }

    fun score(): Int {
        var score = 0
        var frameStart = 0
        repeat(FRAMES) {
            when {
                isStrike(frameStart) -> {
                    score += ALL_PINS + strikeBonus(frameStart)
                    frameStart += 1
                }
                isSpare(frameStart) -> {
                    score += ALL_PINS + spareBonus(frameStart)
                    frameStart += 2
                }
                else -> {
                    score += pinsInFrame(frameStart)
                    frameStart += 2
                }
            }
        }
        return score
    }

    private fun isStrike(frameStart: Int) = rolls[frameStart] == ALL_PINS

    private fun isSpare(frameStart: Int) = pinsInFrame(frameStart) == ALL_PINS

    private fun strikeBonus(frameStart: Int) = rolls[frameStart + 1] + rolls[frameStart + 2]

    private fun spareBonus(frameStart: Int) = rolls[frameStart + 2]

    private fun pinsInFrame(frameStart: Int) = rolls[frameStart] + rolls[frameStart + 1]

    private companion object {
        const val FRAMES = 10
        const val ALL_PINS = 10
    }
}
