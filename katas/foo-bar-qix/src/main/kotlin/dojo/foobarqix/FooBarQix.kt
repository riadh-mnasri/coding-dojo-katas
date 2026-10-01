// Copyright (c) 2026 Riadh MNASRI. Licensed under the MIT License.
package dojo.foobarqix

class FooBarQix private constructor(private val traceZeros: Boolean) {

    fun compute(input: String): String {
        val number = input.toInt()
        val fromDivisors = WORDS.filterKeys { number % it.digitToInt() == 0 }.values.joinToString("")
        val fromDigits = input.mapNotNull { WORDS[it] ?: if (traceZeros && it == '0') "*" else null }.joinToString("")
        val result = fromDivisors + fromDigits
        return if (result.any { it != '*' }) result else if (traceZeros) input.replace('0', '*') else input
    }

    companion object {
        private val WORDS = linkedMapOf('3' to "Foo", '5' to "Bar", '7' to "Qix")

        val step1 = FooBarQix(traceZeros = false)
        val step2 = FooBarQix(traceZeros = true)
    }
}
