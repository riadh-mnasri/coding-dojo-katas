// Copyright (c) 2026 Riadh MNASRI. Licensed under the MIT License.
package dojo.stringcalculator

import java.math.BigDecimal

/** Résultat interne d'un calcul : une valeur, ou la liste de tous les problèmes rencontrés. */
sealed interface Outcome {
    data class Value(val number: BigDecimal) : Outcome
    data class Failure(val messages: List<String>) : Outcome
}

object StringCalculator {

    fun add(input: String): String = render(compute(input, BigDecimal.ZERO, BigDecimal::add))

    /** Une entrée vide vaut 1, l'élément neutre de la multiplication, comme 0 pour l'addition. */
    fun multiply(input: String): String = render(compute(input, BigDecimal.ONE, BigDecimal::multiply))

    internal fun compute(input: String, empty: BigDecimal, combine: (BigDecimal, BigDecimal) -> BigDecimal): Outcome {
        if (input.isEmpty()) return Outcome.Value(empty)
        val scan = Scanner.of(input).scan()
        val negatives = scan.numbers.filter { it.signum() < 0 }
        val negativeError = negatives.takeIf { it.isNotEmpty() }
            ?.let { "Negative not allowed : " + it.joinToString(", ") { n -> n.toPlainString() } }
        val errors = listOfNotNull(negativeError) + scan.errors
        return if (errors.isEmpty()) Outcome.Value(scan.numbers.reduce(combine)) else Outcome.Failure(errors)
    }

    private fun render(outcome: Outcome): String = when (outcome) {
        is Outcome.Value -> outcome.number.stripTrailingZeros().toPlainString()
        is Outcome.Failure -> outcome.messages.joinToString("\n")
    }
}

/** Parcourt les nombres et les séparateurs en notant la position de chaque anomalie. */
private class Scanner(private val text: String, private val separators: List<String>) {

    class Scan(val numbers: List<BigDecimal>, val errors: List<String>)

    fun scan(): Scan {
        val numbers = mutableListOf<BigDecimal>()
        val errors = mutableListOf<String>()
        var position = 0
        while (true) {
            if (position == text.length) {
                errors += "Number expected but EOF found."
                break
            }
            val number = NUMBER.matchAt(text, position)
            if (number == null) {
                errors += "Number expected but ${found(position)}."
                position++
                continue
            }
            numbers += BigDecimal(number.value)
            position = number.range.last + 1
            if (position == text.length) break
            val separator = separators.firstOrNull { text.startsWith(it, position) }
            if (separator == null) {
                errors += "'${separators.first()}' expected but ${found(position)}."
                position++
            } else {
                position += separator.length
            }
        }
        return Scan(numbers, errors)
    }

    private fun found(position: Int): String {
        val char = text[position]
        return "'${if (char == '\n') "\\n" else char}' found at position $position"
    }

    companion object {
        private val NUMBER = Regex("""-?\d+(\.\d+)?""")
        private val DEFAULT_SEPARATORS = listOf(",", "\n")

        /** Une première ligne `//sep` remplace les séparateurs par défaut ; les positions sont comptées après elle. */
        fun of(input: String): Scanner {
            if (!input.startsWith("//")) return Scanner(input, DEFAULT_SEPARATORS)
            val endOfHeader = input.indexOf('\n')
            return Scanner(input.substring(endOfHeader + 1), listOf(input.substring(2, endOfHeader)))
        }
    }
}
