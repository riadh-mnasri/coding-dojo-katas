// Copyright (c) 2026 Riadh MNASRI. Licensed under the MIT License.
package dojo.manhattan

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

class ManhattanDistanceTest {

    @Test
    fun `distance between a point and itself is zero`() {
        assertThat(manhattanDistance(Point(1, 1), Point(1, 1))).isEqualTo(0)
    }
}
