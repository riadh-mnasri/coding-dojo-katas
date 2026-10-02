// Copyright (c) 2026 Riadh MNASRI. Licensed under the MIT License.
package dojo.brainfuck

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

class InterpreterTest {

    private val interpreter = Interpreter()

    @Test
    fun `an empty program leaves 30000 blank cells`() {
        val machine = interpreter.run("")

        assertThat(machine.memory).hasSize(30_000).containsOnly(0)
    }

    @Test
    fun `plus and minus change the current cell`() {
        assertThat(interpreter.run("+++").memory[0]).isEqualTo(3)
        assertThat(interpreter.run("+++-").memory[0]).isEqualTo(2)
    }
}
