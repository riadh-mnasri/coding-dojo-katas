// Copyright (c) 2026 Riadh MNASRI. Licensed under the MIT License.
package dojo.fizzbuzz

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

class FizzBuzzTest {

    @Test
    fun `says 1 for 1`() {
        assertThat(FizzBuzz.classic.say(1)).isEqualTo("1")
    }

    @Test
    fun `says 2 for 2`() {
        assertThat(FizzBuzz.classic.say(2)).isEqualTo("2")
    }

    @Test
    fun `says Fizz for 3`() {
        assertThat(FizzBuzz.classic.say(3)).isEqualTo("Fizz")
    }

    @Test
    fun `says Buzz for 5`() {
        assertThat(FizzBuzz.classic.say(5)).isEqualTo("Buzz")
    }

    @Test
    fun `says FizzBuzz for 15`() {
        assertThat(FizzBuzz.classic.say(15)).isEqualTo("FizzBuzz")
    }

    @Test
    fun `prints the answers from 1 to 100`() {
        val answers = FizzBuzz.classic.sequence()

        assertThat(answers).hasSize(100)
        assertThat(answers.take(15)).containsExactly(
            "1", "2", "Fizz", "4", "Buzz", "Fizz", "7", "8", "Fizz", "Buzz", "11", "Fizz", "13", "14", "FizzBuzz",
        )
        assertThat(answers.last()).isEqualTo("Buzz")
    }

    @Test
    fun `stage 2 - says Fizz for a number containing a 3`() {
        assertThat(FizzBuzz.stageTwo.say(13)).isEqualTo("Fizz")
    }

    @Test
    fun `stage 2 - says Buzz for a number containing a 5`() {
        assertThat(FizzBuzz.stageTwo.say(52)).isEqualTo("Buzz")
    }
}
