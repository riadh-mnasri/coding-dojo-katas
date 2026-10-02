// Copyright (c) 2026 Riadh MNASRI. Licensed under the MIT License.
package dojo.brainfuck

class Machine(size: Int = 30_000) {
    val memory = IntArray(size)
}

class Interpreter {
    fun run(program: String): Machine = Machine()
}
