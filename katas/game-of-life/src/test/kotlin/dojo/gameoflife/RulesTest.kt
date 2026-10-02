// Copyright (c) 2026 Riadh MNASRI. Licensed under the MIT License.
package dojo.gameoflife

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.ValueSource

class RulesTest {

    @ParameterizedTest
    @ValueSource(ints = [0, 1])
    fun `a live cell with fewer than two neighbours dies`(neighbours: Int) {
        assertThat(Rules.isAliveNext(alive = true, liveNeighbours = neighbours)).isFalse()
    }
}
