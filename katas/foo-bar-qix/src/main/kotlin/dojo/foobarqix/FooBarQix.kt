// Copyright (c) 2026 Riadh MNASRI. Licensed under the MIT License.
package dojo.foobarqix

class FooBarQix private constructor() {

    fun compute(input: String): String {
        val number = input.toInt()
        val fromDivisors = WORDS.filterKeys { number % it.digitToInt() == 0 }.values.joinToString("")
        val fromDigits = input.mapNotNull { WORDS[it] }.joinToString("")
        return (fromDivisors + fromDigits).ifEmpty { input }
    }

    companion object {
        private val WORDS = linkedMapOf('3' to "Foo", '5' to "Bar", '7' to "Qix")

        val step1 = FooBarQix()
    }
}
