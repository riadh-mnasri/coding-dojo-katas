// Copyright (c) 2026 Riadh MNASRI. Licensed under the MIT License.
package dojo.numbersinwords

object NumbersInWords {
    private val belowTwenty = listOf(
        "zero", "one", "two", "three", "four", "five", "six", "seven", "eight", "nine",
        "ten", "eleven", "twelve", "thirteen", "fourteen", "fifteen", "sixteen", "seventeen", "eighteen", "nineteen",
    )

    private val tens = listOf("", "", "twenty", "thirty", "forty", "fifty", "sixty", "seventy", "eighty", "ninety")

    fun toWords(number: Int): String = when {
        number < 20 -> belowTwenty[number]
        number % 10 == 0 -> tens[number / 10]
        else -> "${tens[number / 10]} ${belowTwenty[number % 10]}"
    }
}
