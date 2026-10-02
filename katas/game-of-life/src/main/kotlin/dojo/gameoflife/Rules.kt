// Copyright (c) 2026 Riadh MNASRI. Licensed under the MIT License.
package dojo.gameoflife

/** Les quatre règles de Conway, pour une cellule et son nombre de voisins vivants. */
object Rules {
    fun isAliveNext(alive: Boolean, liveNeighbours: Int): Boolean = when {
        alive -> liveNeighbours in 2..3
        else -> liveNeighbours == 3
    }
}
