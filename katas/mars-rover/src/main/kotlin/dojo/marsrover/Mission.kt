// Copyright (c) 2026 Riadh MNASRI. Licensed under the MIT License.
package dojo.marsrover

enum class Direction(val arrow: String, val dx: Int, val dy: Int) {
    NORTH("⬆", 0, 1), EAST("➡", 1, 0), SOUTH("⬇", 0, -1), WEST("⬅", -1, 0);

    fun right() = entries[(ordinal + 1) % entries.size]

    fun left() = entries[(ordinal + entries.size - 1) % entries.size]
}

/** x de gauche à droite, y de bas en haut : la dernière ligne de la carte est y = 0. */
data class Position(val x: Int, val y: Int)

data class Rover(val position: Position, val direction: Direction)

class Mission private constructor(val rover: Rover) {

    fun execute(commands: String): Rover {
        var current = rover
        tiles(commands).forEach { command ->
            when (command) {
                "⬆" -> {
                    val p = current.position
                    current = current.copy(position = Position(p.x + current.direction.dx, p.y + current.direction.dy))
                }
                "➡" -> current = current.copy(direction = current.direction.right())
                "⬅" -> current = current.copy(direction = current.direction.left())
            }
        }
        return current
    }

    companion object {
        private const val VARIATION_SELECTOR = 0xFE0F

        fun parse(map: String): Mission {
            val rows = map.lines().reversed().map(::tiles)
            val rover = rows.withIndex().firstNotNullOf { (y, row) ->
                row.withIndex().firstNotNullOfOrNull { (x, tile) ->
                    Direction.entries.firstOrNull { it.arrow == tile }?.let { Rover(Position(x, y), it) }
                }
            }
            return Mission(rover)
        }

        /** Une tuile = un émoji ; certains tiennent sur deux `char`, d'autres portent un sélecteur de variante. */
        private fun tiles(row: String): List<String> =
            row.codePoints().toArray().filter { it != VARIATION_SELECTOR }.map { String(Character.toChars(it)) }
    }
}
