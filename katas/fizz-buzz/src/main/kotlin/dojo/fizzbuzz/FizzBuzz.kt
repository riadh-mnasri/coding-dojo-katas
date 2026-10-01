// Copyright (c) 2026 Riadh MNASRI. Licensed under the MIT License.
package dojo.fizzbuzz

/** Une règle qui transforme un nombre en mot, ou rien si elle ne s'applique pas. */
fun interface Rule {
    fun wordFor(number: Int): String?
}

class FizzBuzz(private val rules: List<Rule>) {

    fun say(number: Int): String =
        rules.mapNotNull { it.wordFor(number) }.joinToString("").ifEmpty { number.toString() }

    fun sequence(upTo: Int = 100): List<String> = (1..upTo).map(::say)

    companion object {
        /** Étape 1 : multiples de 3 et de 5. */
        val classic = FizzBuzz(listOf(divisibleBy(3, "Fizz"), divisibleBy(5, "Buzz")))

        /** Étape 2 : un nombre est aussi Fizz (resp. Buzz) s'il contient un 3 (resp. un 5). */
        val stageTwo = FizzBuzz(
            listOf(
                divisibleByOrContains(3, "Fizz"),
                divisibleByOrContains(5, "Buzz"),
            ),
        )

        fun divisibleBy(divisor: Int, word: String) = Rule { if (it % divisor == 0) word else null }

        fun divisibleByOrContains(digit: Int, word: String) = Rule {
            if (it % digit == 0 || it.toString().contains(digit.toString())) word else null
        }
    }
}

fun main() = FizzBuzz.classic.sequence().forEach(::println)
