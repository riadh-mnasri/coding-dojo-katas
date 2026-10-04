// Copyright (c) 2026 Riadh MNASRI. Licensed under the MIT License.
package dojo.langton

enum class Color { WHITE, BLACK }

/** Coordonnées d'écran : le nord est vers le haut, donc vers les y décroissants. */
enum class Direction(val dx: Int, val dy: Int) {
    NORTH(0, -1), EAST(1, 0), SOUTH(0, 1), WEST(-1, 0);

    fun right() = entries[(ordinal + 1) % entries.size]

    fun left() = entries[(ordinal + entries.size - 1) % entries.size]
}

data class Position(val x: Int, val y: Int) {
    fun moved(direction: Direction) = Position(x + direction.dx, y + direction.dy)
}

data class Ant(val position: Position, val direction: Direction)

/** Ce que fait la fourmi sur une couleur : comment elle tourne et en quelle couleur elle repeint la case. */
data class Rule(val turn: (Direction) -> Direction, val paint: Color)

val LANGTON = mapOf(
    Color.WHITE to Rule(Direction::right, Color.BLACK),
    Color.BLACK to Rule(Direction::left, Color.WHITE),
)

class World(private val rules: Map<Color, Rule> = LANGTON) {
    var ant = Ant(Position(0, 0), Direction.NORTH)
        private set
    private val colors = mutableMapOf<Position, Color>()

    fun colorAt(position: Position): Color = colors[position] ?: Color.WHITE

    fun step() {
        val rule = rules.getValue(colorAt(ant.position))
        val direction = rule.turn(ant.direction)
        colors[ant.position] = rule.paint
        ant = Ant(ant.position.moved(direction), direction)
    }
}
