// Copyright (c) 2026 Riadh MNASRI. Licensed under the MIT License.
package dojo.marsrover

enum class Direction(val arrow: String, val dx: Int, val dy: Int) {
    NORTH("⬆", 0, 1), EAST("➡", 1, 0), SOUTH("⬇", 0, -1), WEST("⬅", -1, 0);

    fun right() = entries[(ordinal + 1) % entries.size]

    fun left() = entries[(ordinal + entries.size - 1) % entries.size]
}

/** x de gauche à droite, y de bas en haut : la dernière ligne de la carte est y = 0. */
data class Position(val x: Int, val y: Int) {
    fun next(direction: Direction) = Position(x + direction.dx, y + direction.dy)
}

data class Rover(val position: Position, val direction: Direction) {
    fun turnRight() = copy(direction = direction.right())

    fun turnLeft() = copy(direction = direction.left())

    /** Avance d'une case si le terrain le permet ; sinon ne fait rien. */
    fun forward(terrain: Terrain): Rover {
        val next = position.next(direction)
        return if (terrain.isFree(next)) copy(position = next) else this
    }
}

/** La carte : une case est libre si elle existe et n'est ni un arbre ni un rocher. */
class Terrain(private val rows: List<List<String>>) {
    fun isFree(position: Position): Boolean {
        val tile = rows.getOrNull(position.y)?.getOrNull(position.x)
        return tile != null && tile !in OBSTACLES
    }

    private companion object {
        val OBSTACLES = setOf("🌳", "🪨")
    }
}

class Mission private constructor(val rover: Rover, private val terrain: Terrain) {

    fun execute(commands: String): Rover = tiles(commands).fold(rover) { current, command ->
        when (command) {
            "⬆" -> current.forward(terrain)
            "➡" -> current.turnRight()
            "⬅" -> current.turnLeft()
            else -> throw IllegalArgumentException("Unknown command $command")
        }
    }

    companion object {
        private const val VARIATION_SELECTOR = 0xFE0F

        fun parse(map: String): Mission {
            val rows = map.lines().reversed().map(::tiles)
            val rover = rows.withIndex().firstNotNullOfOrNull { (y, row) ->
                row.withIndex().firstNotNullOfOrNull { (x, tile) ->
                    Direction.entries.firstOrNull { it.arrow == tile }?.let { Rover(Position(x, y), it) }
                }
            } ?: throw IllegalArgumentException("The map shows no rover (⬆️ ➡️ ⬇️ ⬅️)")
            return Mission(rover, Terrain(rows))
        }

        /** Une tuile = un émoji ; certains tiennent sur deux `char`, d'autres portent un sélecteur de variante. */
        private fun tiles(text: String): List<String> =
            text.codePoints().toArray().filter { it != VARIATION_SELECTOR }.map { String(Character.toChars(it)) }
    }
}
