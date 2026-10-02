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

    @ParameterizedTest
    @ValueSource(ints = [2, 3])
    fun `a live cell with two or three neighbours lives on`(neighbours: Int) {
        assertThat(Rules.isAliveNext(alive = true, liveNeighbours = neighbours)).isTrue()
    }

    @ParameterizedTest
    @ValueSource(ints = [4, 5, 8])
    fun `a live cell with more than three neighbours dies`(neighbours: Int) {
        assertThat(Rules.isAliveNext(alive = true, liveNeighbours = neighbours)).isFalse()
    }

    @ParameterizedTest
    @ValueSource(ints = [0, 2, 4])
    fun `a dead cell without exactly three neighbours stays dead`(neighbours: Int) {
        assertThat(Rules.isAliveNext(alive = false, liveNeighbours = neighbours)).isFalse()
    }

    @ParameterizedTest
    @ValueSource(booleans = [false])
    fun `a dead cell with exactly three neighbours comes to life`(alive: Boolean) {
        assertThat(Rules.isAliveNext(alive = alive, liveNeighbours = 3)).isTrue()
    }
}
