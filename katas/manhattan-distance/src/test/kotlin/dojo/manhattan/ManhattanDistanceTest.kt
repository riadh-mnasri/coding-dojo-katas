// Copyright (c) 2026 Riadh MNASRI. Licensed under the MIT License.
package dojo.manhattan

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

class ManhattanDistanceTest {

    @Test
    fun `distance between a point and itself is zero`() {
        assertThat(manhattanDistance(Point(1, 1), Point(1, 1))).isEqualTo(0)
    }

    @Test
    fun `distance along the horizontal axis`() {
        assertThat(manhattanDistance(Point(1, 1), Point(4, 1))).isEqualTo(3)
    }

    @Test
    fun `distance along the vertical axis`() {
        assertThat(manhattanDistance(Point(1, 1), Point(1, 3))).isEqualTo(2)
    }

    @Test
    fun `examples from the kata`() {
        assertThat(manhattanDistance(Point(5, 4), Point(3, 2))).isEqualTo(4)
        assertThat(manhattanDistance(Point(1, 1), Point(0, 3))).isEqualTo(3)
    }

    @Test
    fun `distance is symmetric, negative coordinates included`() {
        val a = Point(-2, 7)
        val b = Point(3, -1)

        assertThat(manhattanDistance(a, b)).isEqualTo(manhattanDistance(b, a)).isEqualTo(13)
    }
}
