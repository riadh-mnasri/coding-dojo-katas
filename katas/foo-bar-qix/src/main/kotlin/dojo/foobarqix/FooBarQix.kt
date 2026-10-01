// Copyright (c) 2026 Riadh MNASRI. Licensed under the MIT License.
package dojo.foobarqix

object FooBarQix {
    private val words = mapOf('3' to "Foo", '5' to "Bar")

    fun compute(input: String): String {
        val number = input.toInt()
        val foo = if (number % 3 == 0) "Foo" else ""
        val bar = if (number % 5 == 0) "Bar" else ""
        val digits = input.mapNotNull { words[it] }.joinToString("")
        return (foo + bar + digits).ifEmpty { input }
    }
}
