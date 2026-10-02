// Copyright (c) 2026 Riadh MNASRI. Licensed under the MIT License.
package dojo.brainfuck

class Machine(size: Int = 30_000) {
    val memory = IntArray(size)
    var pointer = 0
}

class Interpreter {
    fun run(program: String): Machine {
        val machine = Machine()
        program.forEach { command ->
            when (command) {
                '+' -> machine.memory[machine.pointer]++
                '-' -> machine.memory[machine.pointer]--
            }
        }
        return machine
    }
}
