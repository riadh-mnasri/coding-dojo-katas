// Copyright (c) 2026 Riadh MNASRI. Licensed under the MIT License.
package dojo.romannumerals

object RomanNumerals {

    private val symbols = listOf(10 to "X", 5 to "V", 1 to "I")

    fun toRoman(number: Int): String {
        var remaining = number
        return buildString {
            for ((value, symbol) in symbols) {
                while (remaining >= value) {
                    append(symbol)
                    remaining -= value
                }
            }
        }
    }
}
