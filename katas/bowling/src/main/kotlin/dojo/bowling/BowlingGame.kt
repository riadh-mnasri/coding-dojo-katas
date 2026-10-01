// Copyright (c) 2026 Riadh MNASRI. Licensed under the MIT License.
package dojo.bowling

class BowlingGame {
    private val rolls = mutableListOf<Int>()
    private var currentFrame = 1
    private var firstRollOfFrame: Int? = null

    fun roll(pins: Int) {
        require(pins in 0..ALL_PINS) { "A roll knocks down 0 to $ALL_PINS pins, got $pins" }
        if (currentFrame < FRAMES) trackFrame(pins)
        rolls += pins
    }

    // Les bonus de la dixième frame ne sont pas des frames : on ne suit que les neuf premières.
    private fun trackFrame(pins: Int) {
        val firstRoll = firstRollOfFrame
        when {
            firstRoll == null && pins == ALL_PINS -> currentFrame++
            firstRoll == null -> firstRollOfFrame = pins
            else -> {
                require(firstRoll + pins <= ALL_PINS) { "A frame cannot knock down more than $ALL_PINS pins" }
                firstRollOfFrame = null
                currentFrame++
            }
        }
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
