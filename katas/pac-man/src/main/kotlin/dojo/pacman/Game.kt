// Copyright (c) 2026 Riadh MNASRI. Licensed under the MIT License.
package dojo.pacman

/** Le dessin de Pac-Man bouche ouverte, selon la direction où il regarde. */
enum class Direction(val symbol: Char, val dx: Int, val dy: Int) {
    UP('V', 0, -1), DOWN('^', 0, 1), LEFT('>', -1, 0), RIGHT('<', 1, 0),
}

class Game private constructor(private val cells: List<CharArray>, private var x: Int, private var y: Int, private var direction: Direction) {

    var score = 0
        private set

    fun tick() {
        val nextX = Math.floorMod(x + direction.dx, cells[y].size)
        val nextY = Math.floorMod(y + direction.dy, cells.size)
        if (cells[nextY][nextX] == WALL) return
        if (cells[nextY][nextX] == DOT) score++
        cells[y][x] = EMPTY
        x = nextX
        y = nextY
        cells[y][x] = direction.symbol
    }

    fun turn(newDirection: Direction) {
        direction = newDirection
        cells[y][x] = direction.symbol
    }

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
