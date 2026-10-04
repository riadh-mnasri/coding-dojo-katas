// Copyright (c) 2026 Riadh MNASRI. Licensed under the MIT License.
package dojo.args

class Args(schema: String, arguments: List<String>) {
    private val types: Map<Char, String> = schema.split(",").filter { it.isNotBlank() }
        .associate { element -> element.trim()[0] to element.trim().drop(1) }
    private val values = mutableMapOf<Char, Any>()

    init {
        val remaining = arguments.iterator()
        while (remaining.hasNext()) {
            val flag = remaining.next().removePrefix("-")[0]
            values[flag] = when (types[flag]) {
                "#" -> remaining.next().toInt()
                "*" -> remaining.next()
                else -> true
            }
        }
    }

    fun boolean(flag: Char): Boolean = values[flag] == true

    fun int(flag: Char): Int = values[flag] as Int

    fun string(flag: Char): String = values[flag] as String
}
