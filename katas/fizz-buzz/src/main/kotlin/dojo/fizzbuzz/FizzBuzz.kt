// Copyright (c) 2026 Riadh MNASRI. Licensed under the MIT License.
package dojo.fizzbuzz

object FizzBuzz {
    fun say(number: Int): String {
        val fizz = if (number % 3 == 0) "Fizz" else ""
        val buzz = if (number % 5 == 0) "Buzz" else ""
        return (fizz + buzz).ifEmpty { number.toString() }
    }
}
