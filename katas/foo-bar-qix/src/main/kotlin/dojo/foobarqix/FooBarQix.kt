// Copyright (c) 2026 Riadh MNASRI. Licensed under the MIT License.
package dojo.foobarqix

object FooBarQix {
    fun compute(input: String): String {
        val number = input.toInt()
        val foo = if (number % 3 == 0) "Foo" else ""
        val bar = if (number % 5 == 0) "Bar" else ""
        return (foo + bar).ifEmpty { input }
    }
}
