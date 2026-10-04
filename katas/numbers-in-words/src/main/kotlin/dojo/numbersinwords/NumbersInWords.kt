// Copyright (c) 2026 Riadh MNASRI. Licensed under the MIT License.
package dojo.numbersinwords

/** Nombres en toutes lettres, en anglais britannique (« seven hundred and forty five »). */
object NumbersInWords {
    private val belowTwenty = listOf(
        "zero", "one", "two", "three", "four", "five", "six", "seven", "eight", "nine",
        "ten", "eleven", "twelve", "thirteen", "fourteen", "fifteen", "sixteen", "seventeen", "eighteen", "nineteen",
    )
    private val tens = listOf("", "", "twenty", "thirty", "forty", "fifty", "sixty", "seventy", "eighty", "ninety")
    private val scales = listOf(1_000_000 to "million", 1_000 to "thousand")

    fun toWords(number: Int): String {
        if (number < 1000) return belowThousand(number)
        val words = mutableListOf<String>()
        var rest = number
        for ((scale, name) in scales) {
            if (rest >= scale) {
                words += "${belowThousand(rest / scale)} $name"
                rest %= scale
            }
        }
        if (rest in 1..99) words += "and ${belowThousand(rest)}" else if (rest > 0) words += belowThousand(rest)
        return words.joinToString(" ")
    }

    /** Étape 2 : relit les mots en accumulant le groupe courant, multiplié par chaque échelle rencontrée. */
    fun toNumber(words: String): Int {
        var total = 0
        var group = 0
        words.split(" ").filter { it.isNotBlank() && it != "and" }.forEach { word ->
            val scale = scales.firstOrNull { it.second == word }?.first
            when {
                word in belowTwenty -> group += belowTwenty.indexOf(word)
                word in tens -> group += tens.indexOf(word) * 10
                word == "hundred" -> group *= 100
                scale != null -> {
                    total += group * scale
                    group = 0
                }
                else -> throw IllegalArgumentException("Unknown number word: $word")
            }
        }
        return total + group
    }

    private fun belowThousand(number: Int): String = when {
        number < 20 -> belowTwenty[number]
        number < 100 && number % 10 == 0 -> tens[number / 10]
        number < 100 -> "${tens[number / 10]} ${belowTwenty[number % 10]}"
        number % 100 == 0 -> "${belowTwenty[number / 100]} hundred"
        else -> "${belowTwenty[number / 100]} hundred and ${belowThousand(number % 100)}"
    }
}
