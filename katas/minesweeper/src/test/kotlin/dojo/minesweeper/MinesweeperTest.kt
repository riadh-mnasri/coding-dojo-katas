// Copyright (c) 2026 Riadh MNASRI. Licensed under the MIT License.
package dojo.minesweeper

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

class MinesweeperTest {

    @Test
    fun `solves the acceptance test of the kata`() {
        val input = """
            4 4
            *...
            ....
            .*..
            ....
            3 5
            **...
            .....
            .*...
            0 0
        """.trimIndent()

        assertThat(Minesweeper.solve(input)).isEqualTo(
            """
            Field #1:
            *100
            2210
            1*10
            1110

            Field #2:
            **100
            33200
            1*100
            """.trimIndent(),
        )
    }
}
