// Copyright (c) 2026 Riadh MNASRI. Licensed under the MIT License.
package dojo.langton

enum class Color { WHITE, BLACK }

/** Coordonnées d'écran : le nord est vers le haut, donc vers les y décroissants. */
enum class Direction(val dx: Int, val dy: Int) {
    NORTH(0, -1), EAST(1, 0), SOUTH(0, 1), WEST(-1, 0);

    fun right() = entries[(ordinal + 1) % entries.size]
}

data class Position(val x: Int, val y: Int) {
    fun moved(direction: Direction) = Position(x + direction.dx, y + direction.dy)
}

data class Ant(val position: Position, val direction: Direction)

class World {
    var ant = Ant(Position(0, 0), Direction.NORTH)
        private set
    private val colors = mutableMapOf<Position, Color>()

    fun colorAt(position: Position): Color = colors[position] ?: Color.WHITE

    fun step() {
        val direction = ant.direction.right()
        colors[ant.position] = Color.BLACK
        ant = Ant(ant.position.moved(direction), direction)
    }
}
