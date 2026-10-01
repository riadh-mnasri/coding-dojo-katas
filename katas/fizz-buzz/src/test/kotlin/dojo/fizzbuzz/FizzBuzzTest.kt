// Copyright (c) 2026 Riadh MNASRI. Licensed under the MIT License.
package dojo.fizzbuzz

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

class FizzBuzzTest {

    @Test
    fun `says 1 for 1`() {
        assertThat(FizzBuzz.say(1)).isEqualTo("1")
    }

    @Test
    fun `says 2 for 2`() {
        assertThat(FizzBuzz.say(2)).isEqualTo("2")
    }
}
