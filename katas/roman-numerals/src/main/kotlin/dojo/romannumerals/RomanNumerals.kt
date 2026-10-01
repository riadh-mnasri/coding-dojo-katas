// Copyright (c) 2026 Riadh MNASRI. Licensed under the MIT License.
package dojo.romannumerals

object RomanNumerals {

    /** Les symboles, soustractions comprises, du plus grand au plus petit. */
    private val symbols = listOf(
        1000 to "M", 900 to "CM", 500 to "D", 400 to "CD",
        100 to "C", 90 to "XC", 50 to "L", 40 to "XL",
        10 to "X", 9 to "IX", 5 to "V", 4 to "IV", 1 to "I",
    )

    fun toRoman(number: Int): String {
        require(number in 1..3999) { "Roman numerals go from 1 to 3999, got $number" }
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

    private val letterValues = mapOf('I' to 1, 'V' to 5, 'X' to 10, 'L' to 50, 'C' to 100, 'D' to 500, 'M' to 1000)

    fun toArabic(roman: String): Int = roman.sumOf { letterValues.getValue(it) }
}
