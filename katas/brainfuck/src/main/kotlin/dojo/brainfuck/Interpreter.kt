// Copyright (c) 2026 Riadh MNASRI. Licensed under the MIT License.
package dojo.brainfuck

class Machine(input: String = "", size: Int = 30_000) {
    val memory = IntArray(size)
    var pointer = 0
    private val pendingInput = ArrayDeque(input.toList())
    private val written = StringBuilder()

    val output: String get() = written.toString()

    fun move(delta: Int) {
        pointer = Math.floorMod(pointer + delta, memory.size)
    }

    fun add(delta: Int) {
        memory[pointer] = Math.floorMod(memory[pointer] + delta, 256)
    }

    fun read() {
        memory[pointer] = pendingInput.removeFirstOrNull()?.code ?: 0
    }

    fun write() {
        written.append(memory[pointer].toChar())
    }
}

class Interpreter {
    fun run(program: String, input: String = ""): Machine {
        val machine = Machine(input)
        val jumps = matchingBrackets(program)
        var position = 0
        while (position < program.length) {
            when (program[position]) {
                '+' -> machine.add(1)
                '-' -> machine.add(-1)
                '>' -> machine.move(1)
                '<' -> machine.move(-1)
                ',' -> machine.read()
                '.' -> machine.write()
                '[' -> if (machine.memory[machine.pointer] == 0) position = jumps.getValue(position)
                ']' -> if (machine.memory[machine.pointer] != 0) position = jumps.getValue(position)
            }
            position++
        }
        return machine
    }

    /** Associe chaque crochet à son crochet correspondant, dans les deux sens. */
    private fun matchingBrackets(program: String): Map<Int, Int> {
        val jumps = mutableMapOf<Int, Int>()
        val opened = ArrayDeque<Int>()
        program.forEachIndexed { index, command ->
            if (command == '[') opened.addLast(index)
            if (command == ']') {
                val open = opened.removeLast()
                jumps[open] = index
                jumps[index] = open
            }
        }
        return jumps
    }
}
