// Copyright (c) 2026 Riadh MNASRI. Licensed under the MIT License.
package dojo.foobarqix

object FooBarQix {
    fun compute(input: String): String =
        if (input.toInt() % 3 == 0) "Foo" else input
}
