// Copyright (c) 2026 Riadh MNASRI. Licensed under the MIT License.
package dojo.fizzbuzz

object FizzBuzz {
    fun say(number: Int): String = when {
        number % 3 == 0 -> "Fizz"
        number % 5 == 0 -> "Buzz"
        else -> number.toString()
    }
}
