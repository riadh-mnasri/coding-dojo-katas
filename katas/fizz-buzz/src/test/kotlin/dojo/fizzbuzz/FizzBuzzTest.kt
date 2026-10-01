// Copyright (c) 2026 Riadh MNASRI. Licensed under the MIT License.
package dojo.fizzbuzz

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.CsvSource

class FizzBuzzTest {

    @Nested
    inner class Stage1 {
        private val fizzBuzz = FizzBuzz.classic

        @Test
        fun `says the number itself when no rule applies`() {
            assertThat(fizzBuzz.say(1)).isEqualTo("1")
            assertThat(fizzBuzz.say(2)).isEqualTo("2")
        }

        @ParameterizedTest
        @CsvSource("3, Fizz", "6, Fizz", "9, Fizz")
        fun `says Fizz for multiples of three`(number: Int, expected: String) {
            assertThat(fizzBuzz.say(number)).isEqualTo(expected)
        }

        @ParameterizedTest
        @CsvSource("5, Buzz", "10, Buzz", "20, Buzz")
        fun `says Buzz for multiples of five`(number: Int, expected: String) {
            assertThat(fizzBuzz.say(number)).isEqualTo(expected)
        }

        @ParameterizedTest
        @CsvSource("15, FizzBuzz", "30, FizzBuzz", "45, FizzBuzz")
        fun `says FizzBuzz for multiples of three and five`(number: Int, expected: String) {
            assertThat(fizzBuzz.say(number)).isEqualTo(expected)
        }

        @Test
        fun `prints the hundred first answers`() {
            val sequence = fizzBuzz.sequence()

            assertThat(sequence).hasSize(100)
            assertThat(sequence.take(15)).containsExactly(
                "1", "2", "Fizz", "4", "Buzz", "Fizz", "7", "8", "Fizz", "Buzz",
                "11", "Fizz", "13", "14", "FizzBuzz",
            )
        }
    }

    @Nested
    inner class Stage2 {
        private val fizzBuzz = FizzBuzz.stageTwo

        @ParameterizedTest
        @CsvSource("13, Fizz", "23, Fizz", "31, Fizz")
        fun `a number containing a 3 is Fizz`(number: Int, expected: String) {
            assertThat(fizzBuzz.say(number)).isEqualTo(expected)
        }

        @ParameterizedTest
        @CsvSource("52, Buzz", "58, Buzz")
        fun `a number containing a 5 is Buzz`(number: Int, expected: String) {
            assertThat(fizzBuzz.say(number)).isEqualTo(expected)
        }

        @ParameterizedTest
        @CsvSource("35, FizzBuzz", "53, FizzBuzz", "51, FizzBuzz")
        fun `rules combine when containing and dividing mix`(number: Int, expected: String) {
            assertThat(fizzBuzz.say(number)).isEqualTo(expected)
        }
    }
}
