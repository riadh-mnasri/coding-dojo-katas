// Copyright (c) 2026 Riadh MNASRI. Licensed under the MIT License.
package dojo.romannumerals

object RomanNumerals {
    fun toRoman(number: Int): String {
        var remaining = number
        var roman = ""
        if (remaining >= 5) {
            roman += "V"
            remaining -= 5
        }
        return roman + "I".repeat(remaining)
    }
}
