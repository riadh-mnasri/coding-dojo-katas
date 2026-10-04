// Copyright (c) 2026 Riadh MNASRI. Licensed under the MIT License.
package dojo.marsrover

enum class Direction(val arrow: String) { NORTH("⬆"), EAST("➡"), SOUTH("⬇"), WEST("⬅") }

/** x de gauche à droite, y de bas en haut : la dernière ligne de la carte est y = 0. */
data class Position(val x: Int, val y: Int)

data class Rover(val position: Position, val direction: Direction)

class Mission private constructor(val rover: Rover) {

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
