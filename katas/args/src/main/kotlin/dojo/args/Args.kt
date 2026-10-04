// Copyright (c) 2026 Riadh MNASRI. Licensed under the MIT License.
package dojo.args

class ArgsException(message: String) : IllegalArgumentException(message)

class Args(schema: String, arguments: List<String>) {
    private val types: Map<Char, String> = schema.split(",").filter { it.isNotBlank() }
        .associate { element -> element.trim()[0] to element.trim().drop(1) }
    private val values = mutableMapOf<Char, Any>()

    init {
        val remaining = arguments.iterator()
        while (remaining.hasNext()) {
            val argument = remaining.next()
            if (!argument.startsWith("-") || argument.length != 2) {
                throw ArgsException("Expected a flag like -l, got '$argument'")
            }
            val flag = argument[1]
            values[flag] = when (types[flag]) {
                null -> throw ArgsException("Unknown flag -$flag")
                "#" -> {
                    if (!remaining.hasNext()) throw ArgsException("Flag -$flag expects an integer value")
                    val value = remaining.next()
                    value.toIntOrNull() ?: throw ArgsException("Flag -$flag expects an integer, got '$value'")
                }
                "*" -> {
                    if (!remaining.hasNext()) throw ArgsException("Flag -$flag expects a string value")
                    remaining.next()
                }
                else -> true
            }
        }
    }

    fun boolean(flag: Char): Boolean = values[flag] == true

    fun int(flag: Char): Int = values[flag] as Int? ?: 0

    fun string(flag: Char): String = values[flag] as String? ?: ""
}
