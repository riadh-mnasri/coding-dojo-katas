// Copyright (c) 2026 Riadh MNASRI. Licensed under the MIT License.
package dojo.foobarqix

/**
 * @param traceZeros étape 2 : chaque 0 du nombre laisse une trace `*`.
 */
class FooBarQix private constructor(private val traceZeros: Boolean) {

    fun compute(input: String): String {
        val number = input.toInt()
        val fromDivisors = WORDS.filterKeys { number % it.digitToInt() == 0 }.values.joinToString("")
        val fromDigits = input.mapNotNull { digit -> WORDS[digit] ?: zeroTrace(digit) }.joinToString("")
        val result = fromDivisors + fromDigits
        return if (result.hasWord()) result else withZeroTraces(input)
    }

    private fun zeroTrace(digit: Char): String? = if (traceZeros && digit == '0') "$ZERO_TRACE" else null

    private fun withZeroTraces(input: String) = if (traceZeros) input.replace('0', ZERO_TRACE) else input

    private fun String.hasWord() = any { it != ZERO_TRACE }

    companion object {
        private const val ZERO_TRACE = '*'
        private val WORDS = linkedMapOf('3' to "Foo", '5' to "Bar", '7' to "Qix")

        val step1 = FooBarQix(traceZeros = false)
        val step2 = FooBarQix(traceZeros = true)
    }
}
