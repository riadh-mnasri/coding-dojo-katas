// Copyright (c) 2026 Riadh MNASRI. Licensed under the MIT License.
package dojo.foobarqix

object FooBarQix {
    private val words = linkedMapOf('3' to "Foo", '5' to "Bar")

    fun compute(input: String): String {
        val number = input.toInt()
        val fromDivisors = words.filterKeys { number % it.digitToInt() == 0 }.values.joinToString("")
        val fromDigits = input.mapNotNull { words[it] }.joinToString("")
        return (fromDivisors + fromDigits).ifEmpty { input }
    }
}
