// Copyright (c) 2026 Riadh MNASRI. Licensed under the MIT License.
package dojo.manhattan

import kotlin.math.abs

/**
 * Point immuable, sans accesseur ni propriété publique : son état ne se lit pas de l'extérieur.
 * C'est donc lui qui calcule sa distance à un autre point (Tell, don't ask).
 */
class Point(private val x: Int, private val y: Int) {
    fun distanceTo(other: Point): Int = abs(x - other.x) + abs(y - other.y)
}

fun manhattanDistance(from: Point, to: Point): Int = from.distanceTo(to)
