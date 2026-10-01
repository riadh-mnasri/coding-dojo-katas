// Copyright (c) 2026 Riadh MNASRI. Licensed under the MIT License.
package dojo.manhattan

import kotlin.math.abs

/**
 * Point immuable, sans accesseur ni propriété publique : son état ne se lit pas de l'extérieur.
 * C'est donc lui qui calcule sa distance à un autre point (Tell, don't ask).
 */
class Point(private val x: Int, private val y: Int) {
    fun distanceTo(other: Point): Int = abs(x - other.x) + abs(y - other.y)

    // Pas de data class : elle générerait component1()/component2() et copy(), donc un accès à l'état.
    override fun equals(other: Any?) = other is Point && x == other.x && y == other.y

    override fun hashCode() = 31 * x + y

    override fun toString() = "Point($x, $y)"
}

fun manhattanDistance(from: Point, to: Point): Int = from.distanceTo(to)
