// Copyright (c) 2026 Riadh MNASRI. Licensed under the MIT License.
package dojo.stringcalculator

import java.math.BigDecimal

object StringCalculator {
    private val NUMBER = Regex("""\d+(\.\d+)?""")
    private val SEPARATORS = listOf(",", "\n")

    fun add(numbers: String): String {
        if (numbers.isEmpty()) return "0"
        val parsed = mutableListOf<BigDecimal>()
        var position = 0
        while (true) {
            if (position == numbers.length) return "Number expected but EOF found."
            val number = NUMBER.matchAt(numbers, position)
                ?: return "Number expected but '${escape(numbers[position])}' found at position $position."
            parsed += BigDecimal(number.value)
            position = number.range.last + 1
            if (position == numbers.length) break
            position += SEPARATORS.first { numbers.startsWith(it, position) }.length
        }
        return parsed.fold(BigDecimal.ZERO, BigDecimal::add).stripTrailingZeros().toPlainString()
    }

    private fun escape(char: Char) = if (char == '\n') "\\n" else char.toString()
}
