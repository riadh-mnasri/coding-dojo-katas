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

/** Ce que fait une instruction. Les deux crochets sont des marqueurs gérés par l'interpréteur. */
sealed interface Instruction {
    fun interface Action : Instruction {
        fun execute(machine: Machine)
    }

    data object LoopStart : Instruction
    data object LoopEnd : Instruction

    companion object {
        val INCREMENT = Action { it.add(1) }
        val DECREMENT = Action { it.add(-1) }
        val RIGHT = Action { it.move(1) }
        val LEFT = Action { it.move(-1) }
        val READ = Action { it.read() }
        val WRITE = Action { it.write() }
    }
}

/** Une syntaxe associe des jetons (un caractère ou un mot) à des instructions. */
class Syntax(private val tokens: Map<String, Instruction>) {
    private val longestFirst = tokens.keys.sortedByDescending { it.length }

    /** Découpe le programme en instructions ; tout ce qui n'est pas un jeton est un commentaire. */
    fun parse(program: String): List<Instruction> {
        val instructions = mutableListOf<Instruction>()
        var position = 0
        while (position < program.length) {
            val token = longestFirst.firstOrNull { program.startsWith(it, position) }
            if (token == null) {
                position++
            } else {
                instructions += tokens.getValue(token)
                position += token.length
            }
        }
        return instructions
    }

    companion object {
        val BRAINFUCK = Syntax(
            mapOf(
                "+" to Instruction.INCREMENT,
                "-" to Instruction.DECREMENT,
                ">" to Instruction.RIGHT,
                "<" to Instruction.LEFT,
                "," to Instruction.READ,
                "." to Instruction.WRITE,
                "[" to Instruction.LoopStart,
                "]" to Instruction.LoopEnd,
            ),
        )
    }
}

class Interpreter(private val syntax: Syntax = Syntax.BRAINFUCK) {

    fun run(program: String, input: String = ""): Machine {
        val machine = Machine(input)
        val instructions = syntax.parse(program)
        val jumps = matchingLoops(instructions)
        var position = 0
        while (position < instructions.size) {
            when (val instruction = instructions[position]) {
                is Instruction.Action -> instruction.execute(machine)
                Instruction.LoopStart -> if (machine.memory[machine.pointer] == 0) position = jumps.getValue(position)
                Instruction.LoopEnd -> if (machine.memory[machine.pointer] != 0) position = jumps.getValue(position)
            }
            position++
        }
        return machine
    }

    /** Associe chaque début de boucle à sa fin, dans les deux sens. */
    private fun matchingLoops(instructions: List<Instruction>): Map<Int, Int> {
        val jumps = mutableMapOf<Int, Int>()
        val opened = ArrayDeque<Int>()
        instructions.forEachIndexed { index, instruction ->
            if (instruction == Instruction.LoopStart) opened.addLast(index)
            if (instruction == Instruction.LoopEnd) {
                val open = opened.removeLastOrNull() ?: throw IllegalArgumentException("Unmatched loop end at instruction $index")
                jumps[open] = index
                jumps[index] = open
            }
        }
        require(opened.isEmpty()) { "Unmatched loop start at instruction ${opened.first()}" }
        return jumps
    }
}
