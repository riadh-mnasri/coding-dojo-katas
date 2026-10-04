// Copyright (c) 2026 Riadh MNASRI. Licensed under the MIT License.
package dojo.args

class ArgsException(message: String) : IllegalArgumentException(message)

/**
 * Sait lire la valeur d'un type de drapeau. Ajouter un type = ajouter un marshaler à [Args.MARSHALERS].
 * [next] fournit l'argument suivant, ou `null` s'il n'y en a plus.
 */
interface Marshaler {
    val default: Any
    fun parse(flag: Char, next: () -> String?): Any
}

private object BooleanMarshaler : Marshaler {
    override val default = false
    override fun parse(flag: Char, next: () -> String?) = true
}

private object IntMarshaler : Marshaler {
    override val default = 0
    override fun parse(flag: Char, next: () -> String?): Any {
        val value = next() ?: throw ArgsException("Flag -$flag expects an integer value")
        return value.toIntOrNull() ?: throw ArgsException("Flag -$flag expects an integer, got '$value'")
    }
}

private object StringMarshaler : Marshaler {
    override val default = ""
    override fun parse(flag: Char, next: () -> String?) = next() ?: throw ArgsException("Flag -$flag expects a string value")
}

/** Schéma : des éléments séparés par des virgules, une lettre suivie du code de son type (`l`, `p#`, `d*`). */
class Args(schema: String, arguments: List<String>) {
    private val marshalers: Map<Char, Marshaler> = schema.split(",").filter { it.isNotBlank() }.associate { element ->
        val flag = element.trim()[0]
        val code = element.trim().drop(1)
        flag to (MARSHALERS[code] ?: throw ArgsException("Unknown type '$code' for flag -$flag in the schema"))
    }
    private val values = mutableMapOf<Char, Any>()

    init {
        val remaining = arguments.iterator()
        while (remaining.hasNext()) {
            val argument = remaining.next()
            if (!argument.startsWith("-") || argument.length != 2) {
                throw ArgsException("Expected a flag like -l, got '$argument'")
            }
            val flag = argument[1]
            val marshaler = marshalers[flag] ?: throw ArgsException("Unknown flag -$flag")
            values[flag] = marshaler.parse(flag) { if (remaining.hasNext()) remaining.next() else null }
        }
    }

    fun boolean(flag: Char): Boolean = valueOf(flag) as Boolean

    fun int(flag: Char): Int = valueOf(flag) as Int

    fun string(flag: Char): String = valueOf(flag) as String

    private fun valueOf(flag: Char): Any =
        values[flag] ?: marshalers[flag]?.default ?: throw ArgsException("Flag -$flag is not in the schema")

    private companion object {
        val MARSHALERS: Map<String, Marshaler> = mapOf("" to BooleanMarshaler, "#" to IntMarshaler, "*" to StringMarshaler)
    }
}
