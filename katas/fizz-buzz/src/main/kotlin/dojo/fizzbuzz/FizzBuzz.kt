// Copyright (c) 2026 Riadh MNASRI. Licensed under the MIT License.
package dojo.fizzbuzz

/** Une règle donne un mot pour un nombre, ou rien si elle ne s'applique pas. */
fun interface Rule {
    fun wordFor(number: Int): String?
}

fun divisibleBy(divisor: Int, word: String) = Rule { number -> word.takeIf { number % divisor == 0 } }

fun divisibleByOrContains(digit: Int, word: String) = Rule { number ->
    word.takeIf { number % digit == 0 || digit.toString() in number.toString() }
}

class FizzBuzz(private val rules: List<Rule>) {

    fun say(number: Int): String =
        rules.mapNotNull { it.wordFor(number) }.joinToString("").ifEmpty { number.toString() }

    fun sequence(): List<String> = (1..100).map(::say)

    companion object {
        val classic = FizzBuzz(listOf(divisibleBy(3, "Fizz"), divisibleBy(5, "Buzz")))
        val stageTwo = FizzBuzz(listOf(divisibleByOrContains(3, "Fizz"), divisibleByOrContains(5, "Buzz")))
    }
}

fun main() = FizzBuzz.classic.sequence().forEach(::println)
