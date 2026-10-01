// Copyright (c) 2026 Riadh MNASRI. Licensed under the MIT License.
package dojo.romannumerals

object RomanNumerals {
    fun toRoman(number: Int): String {
        var remaining = number
        var roman = ""
        while (remaining >= 10) {
            roman += "X"
            remaining -= 10
        }
        while (remaining >= 5) {
            roman += "V"
            remaining -= 5
        }
        while (remaining >= 1) {
            roman += "I"
            remaining -= 1
        }
        return roman
    }
}
