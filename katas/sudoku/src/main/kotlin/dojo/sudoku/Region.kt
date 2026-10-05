// Copyright (c) 2026 Riadh MNASRI. Licensed under the MIT License.
package dojo.sudoku

enum class Direction {
    NORTH, EAST, SOUTH, WEST;

    fun opposite() = entries[(ordinal + 2) % entries.size]
}

/** Le contenu des messages de l'énoncé : une valeur trouvée, et les régions qui l'ont déjà traitée. */
data class Message(val row: Int, val column: Int, val value: Int, val path: List<String>)

/**
 * Une région : une grille et ses quatre sorties. Elle ne connaît pas ses voisines, seulement la
 * fonction [send] qui achemine un message vers la sortie voulue.
 */
class Region(
    val name: String,
    private val send: (Direction, Message) -> Unit,
    private val display: (String, Discovery) -> Unit,
) {
    private val grid = Grid(name) { discovery ->
        display(name, discovery)
        val message = Message(discovery.row, discovery.column, discovery.value, path = listOf(name))
        Direction.entries.forEach { send(it, message) }
    }

    fun init(row: Int, column: Int, value: Int) = grid.set(row, column, value)

    fun isPossible(row: Int, column: Int, value: Int) = grid.cell(row, column).isPossible(value)

    /** Un message venu du nord ou du sud concerne une colonne, de l'est ou de l'ouest une ligne ; il continue tout droit. */
    fun receive(from: Direction, message: Message) {
        if (name in message.path) return
        when (from) {
            Direction.NORTH, Direction.SOUTH -> grid.excludeFromColumn(message.column, message.value)
            Direction.EAST, Direction.WEST -> grid.excludeFromRow(message.row, message.value)
        }
        send(from.opposite(), message.copy(path = message.path + name))
    }
}
