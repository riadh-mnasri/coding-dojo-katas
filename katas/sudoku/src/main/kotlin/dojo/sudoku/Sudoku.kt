// Copyright (c) 2026 Riadh MNASRI. Licensed under the MIT License.
package dojo.sudoku

import kotlinx.coroutines.CompletableDeferred
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch
import java.util.concurrent.atomic.AtomicInteger
import java.util.concurrent.atomic.AtomicIntegerArray

/**
 * Le résolveur : neuf régions, chacune un acteur (une coroutine et sa boîte aux lettres), disposées en tore.
 * La sortie nord d'une région arrive à l'entrée sud de la région du dessus, etc. ; au-delà du bord, on revient de l'autre côté.
 *
 * Une région ne traite ses messages qu'un par un, dans sa propre coroutine : son état n'est jamais partagé.
 * La résolution est terminée quand plus aucun message n'est en transit.
 */
object Sudoku {
    private const val NAMES = "ABCDEFGHI"

    suspend fun solve(puzzle: String): String = coroutineScope {
        val inboxes = List(9) { Channel<Pair<Direction, Message>>(Channel.UNLIMITED) }
        val inFlight = AtomicInteger(0)
        val finished = CompletableDeferred<Unit>()
        val display = AtomicIntegerArray(81)

        val regions = NAMES.mapIndexed { index, name ->
            Region(
                name.toString(),
                send = { output, message ->
                    inFlight.incrementAndGet()
                    inboxes[neighbour(index, output)].trySend(output.opposite() to message)
                },
                display = { _, d -> display[((index / 3) * 3 + d.row - 1) * 9 + (index % 3) * 3 + d.column - 1] = d.value },
            )
        }

        puzzle.lines().forEachIndexed { row, line ->
            line.forEachIndexed { column, char ->
                if (char != '0') regions[(row / 3) * 3 + column / 3].init(row % 3 + 1, column % 3 + 1, char.digitToInt())
            }
        }

        regions.indices.forEach { index ->
            launch {
                for ((from, message) in inboxes[index]) {
                    regions[index].receive(from, message)
                    if (inFlight.decrementAndGet() == 0) finished.complete(Unit)
                }
            }
        }
        if (inFlight.get() == 0) finished.complete(Unit)

        finished.await()
        inboxes.forEach { it.close() }
        (0 until 9).joinToString("\n") { row -> (0 until 9).joinToString("") { display[row * 9 + it].toString() } }
    }

    /** La région voisine dans cette direction, la grille des régions étant refermée sur elle-même. */
    private fun neighbour(index: Int, output: Direction): Int {
        val row = index / 3
        val column = index % 3
        return when (output) {
            Direction.NORTH -> ((row + 2) % 3) * 3 + column
            Direction.SOUTH -> ((row + 1) % 3) * 3 + column
            Direction.EAST -> row * 3 + (column + 1) % 3
            Direction.WEST -> row * 3 + (column + 2) % 3
        }
    }
}
