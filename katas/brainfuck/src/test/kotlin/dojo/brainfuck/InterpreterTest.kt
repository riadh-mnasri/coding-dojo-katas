// Copyright (c) 2026 Riadh MNASRI. Licensed under the MIT License.
package dojo.brainfuck

import org.assertj.core.api.Assertions.assertThat
import org.assertj.core.api.Assertions.assertThatThrownBy
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

    @Test
    fun `cells hold bytes from 0 to 255`() {
        assertThat(interpreter.run("-").memory[0]).isEqualTo(255)
        assertThat(interpreter.run("+".repeat(256)).memory[0]).isEqualTo(0)
    }

    @Test
    fun `the pointer moves right and left, wrapping to the last cell`() {
        assertThat(interpreter.run(">++>+").memory.take(3)).containsExactly(0, 2, 1)
        assertThat(interpreter.run(">+<+").memory.take(2)).containsExactly(1, 1)
        assertThat(interpreter.run("<+").memory.last()).isEqualTo(1)
    }

    @Test
    fun `comma reads a byte of input and dot writes the current cell`() {
        val machine = interpreter.run(",+.>,.", input = "AZ")

        assertThat(machine.output).isEqualTo("BZ")
    }

    @Test
    fun `brackets loop while the current cell is not zero`() {
        assertThat(interpreter.run("+++[->++<]").memory.take(2)).containsExactly(0, 6)
    }

    @Test
    fun `a loop on a zero cell is skipped and loops can be nested`() {
        assertThat(interpreter.run("[+]+").memory[0]).isEqualTo(1)
        assertThat(interpreter.run("++[>++[>+++<-]<-]").memory.take(3)).containsExactly(0, 0, 12)
    }

    @Test
    fun `prints hello world`() {
        val program = "++++++++[>++++[>++>+++>+++>+<<<<-]>+>+>->>+[<]<-]>>.>---.+++++++..+++.>>.<-.<.+++.------.--------.>>+.>++."

        assertThat(interpreter.run(program).output).isEqualTo("Hello World!\n")
    }

    @Test
    fun `unbalanced brackets are rejected`() {
        assertThatThrownBy { interpreter.run("[") }.isInstanceOf(IllegalArgumentException::class.java)
        assertThatThrownBy { interpreter.run("+]") }.isInstanceOf(IllegalArgumentException::class.java)
    }

    @Test
    fun `instructions can be renamed, OooWee style`() {
        // Given
        val oooWee = Syntax.BRAINFUCK.renamed(
            "+" to "Ooo", "-" to "Wee", ">" to "OooWee", "<" to "WeeOoo",
            "." to "Ooo!", "," to "Wee?", "[" to "Ooo(", "]" to "Wee)",
        )
        val program = "OooOooOoo Ooo( Wee OooWee OooOoo WeeOoo Wee) OooWee Ooo!"

        // When
        val machine = Interpreter(oooWee).run(program)

        // Then
        assertThat(machine.memory.take(2)).containsExactly(0, 6)
        assertThat(machine.output).isEqualTo(6.toChar().toString())
    }

    @Test
    fun `new instructions can be added, like a jump to the end of memory`() {
        // Given
        val withJump = Syntax.BRAINFUCK.with("!" to Instruction.Action { it.pointer = it.memory.size - 1 })

        // When
        val machine = Interpreter(withJump).run("!+>+")

        // Then
        assertThat(machine.memory.last()).isEqualTo(1)
        assertThat(machine.memory.first()).isEqualTo(1)
    }
}
