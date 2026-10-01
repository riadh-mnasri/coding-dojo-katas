// Copyright (c) 2026 Riadh MNASRI. Licensed under the MIT License.
package dojo.foobarqix

object FooBarQix {
    fun compute(input: String): String {
        val number = input.toInt()
        val foo = if (number % 3 == 0) "Foo" else ""
        val bar = if (number % 5 == 0) "Bar" else ""
        val digits = input.filter { it == '3' }.map { "Foo" }.joinToString("")
        return (foo + bar + digits).ifEmpty { input }
    }
}
