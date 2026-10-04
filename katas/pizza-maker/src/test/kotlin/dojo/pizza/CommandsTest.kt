// Copyright (c) 2026 Riadh MNASRI. Licensed under the MIT License.
package dojo.pizza

import kotlinx.coroutines.test.advanceTimeBy
import kotlinx.coroutines.test.runTest
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

class CommandsTest {

    @Test
    fun `the three commands of the kata`() = runTest {
        val commands = Commands(Oven(backgroundScope) {})

        assertThat(commands.execute("cook a Margherita Pizza")).isEqualTo("Margherita goes in the oven.")
        advanceTimeBy(50_000)
        assertThat(commands.execute("show queue")).isEqualTo("Margherita: cooked")
        assertThat(commands.execute("Get out the pizza")).isEqualTo("Margherita is out, cooked. Points: 1")
        assertThat(commands.execute("dance")).isEqualTo("Unknown command 'dance'")
    }
}
