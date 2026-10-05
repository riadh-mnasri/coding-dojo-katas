// Copyright (c) 2026 Riadh MNASRI. Licensed under the MIT License.
package dojo.pacman

/** Le dessin de Pac-Man bouche ouverte, selon la direction où il regarde. */
enum class Direction(val symbol: Char, val dx: Int, val dy: Int) {
    UP('V', 0, -1), DOWN('^', 0, 1), LEFT('>', -1, 0), RIGHT('<', 1, 0),
}

class Game private constructor(private val cells: List<CharArray>, private var x: Int, private var y: Int, private var direction: Direction) {

    var score = 0
        private set

    val isLevelComplete: Boolean get() = cells.none { DOT in it }

    fun tick() {
        val (nextX, nextY) = ahead(direction)
        if (cells[nextY][nextX] == WALL) return
        if (cells[nextY][nextX] == DOT) score++
        cells[y][x] = EMPTY
        x = nextX
        y = nextY
        cells[y][x] = direction.symbol
    }

    fun turn(newDirection: Direction) {
        val (aheadX, aheadY) = ahead(newDirection)
        if (cells[aheadY][aheadX] == WALL) return
        direction = newDirection
        cells[y][x] = direction.symbol
    }

    /** La case devant Pac-Man dans cette direction, en passant de l'autre côté du plateau aux bords. */
    private fun ahead(towards: Direction) =
        Math.floorMod(x + towards.dx, cells[y].size) to Math.floorMod(y + towards.dy, cells.size)

    fun render(): String = cells.joinToString("\n") { String(it) }

    companion object {
        private const val DOT = '.'
        private const val WALL = '#'
        private const val EMPTY = ' '

        fun parse(board: String): Game {
            val cells = board.lines().map { it.toCharArray() }
            val (x, y, direction) = cells.withIndex().firstNotNullOf { (row, line) ->
                line.withIndex().firstNotNullOfOrNull { (column, cell) ->
                    Direction.entries.firstOrNull { it.symbol == cell }?.let { Triple(column, row, it) }
                }
            }
            return Game(cells, x, y, direction)
        }
    }
}
