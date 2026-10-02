// Copyright (c) 2026 Riadh MNASRI. Licensed under the MIT License.
package dojo.stringcalculator

import java.math.BigDecimal

object StringCalculator {
    private val NUMBER = Regex("""-?\d+(\.\d+)?""")
    private val DEFAULT_SEPARATORS = listOf(",", "\n")

    fun add(input: String): String {
        if (input.isEmpty()) return "0"
        val (separators, numbers) = if (input.startsWith("//")) {
            val endOfHeader = input.indexOf('\n')
            listOf(input.substring(2, endOfHeader)) to input.substring(endOfHeader + 1)
        } else {
            DEFAULT_SEPARATORS to input
        }
        val parsed = mutableListOf<BigDecimal>()
        val syntaxErrors = mutableListOf<String>()
        var position = 0
        while (true) {
            if (position == numbers.length) {
                syntaxErrors += "Number expected but EOF found."
                break
            }
            val number = NUMBER.matchAt(numbers, position)
            if (number == null) {
                syntaxErrors += "Number expected but '${escape(numbers[position])}' found at position $position."
                position++
                continue
            }
            parsed += BigDecimal(number.value)
            position = number.range.last + 1
            if (position == numbers.length) break
            val separator = separators.firstOrNull { numbers.startsWith(it, position) }
            if (separator == null) {
                syntaxErrors += "'${separators.first()}' expected but '${escape(numbers[position])}' found at position $position."
                position++
            } else {
                position += separator.length
            }
        }
        val negatives = parsed.filter { it.signum() < 0 }
        val errors = listOfNotNull(
            negatives.takeIf { it.isNotEmpty() }?.let { "Negative not allowed : " + it.joinToString(", ") { n -> n.toPlainString() } },
        ) + syntaxErrors
        if (errors.isNotEmpty()) return errors.joinToString("\n")
        return parsed.fold(BigDecimal.ZERO, BigDecimal::add).stripTrailingZeros().toPlainString()
    }

    private fun escape(char: Char) = if (char == '\n') "\\n" else char.toString()
}
