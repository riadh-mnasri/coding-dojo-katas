// Copyright (c) 2026 Riadh MNASRI. Licensed under the MIT License.
package dojo.brainfuck

class Machine(size: Int = 30_000) {
    val memory = IntArray(size)
    var pointer = 0

    fun move(delta: Int) {
        pointer = Math.floorMod(pointer + delta, memory.size)
    }

    fun add(delta: Int) {
        memory[pointer] = Math.floorMod(memory[pointer] + delta, 256)
    }
}

class Interpreter {
    fun run(program: String): Machine {
        val machine = Machine()
        program.forEach { command ->
            when (command) {
                '+' -> machine.add(1)
                '-' -> machine.add(-1)
                '>' -> machine.move(1)
                '<' -> machine.move(-1)
            }
        }
        return machine
    }
}
