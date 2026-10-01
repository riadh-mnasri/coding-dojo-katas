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
        repeat(10) {
            if (rolls[frameStart] == 10) {
                score += 10 + rolls[frameStart + 1] + rolls[frameStart + 2]
                frameStart += 1
            } else if (rolls[frameStart] + rolls[frameStart + 1] == 10) {
                score += 10 + rolls[frameStart + 2]
                frameStart += 2
            } else {
                score += rolls[frameStart] + rolls[frameStart + 1]
                frameStart += 2
            }
        }
        return score
    }
}
