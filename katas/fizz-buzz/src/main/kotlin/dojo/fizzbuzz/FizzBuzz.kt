// Copyright (c) 2026 Riadh MNASRI. Licensed under the MIT License.
package dojo.fizzbuzz

object FizzBuzz {
    fun say(number: Int): String =
        if (number % 3 == 0) "Fizz" else number.toString()
}
