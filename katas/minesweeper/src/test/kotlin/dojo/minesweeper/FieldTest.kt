// Copyright (c) 2026 Riadh MNASRI. Licensed under the MIT License.
package dojo.minesweeper

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

class FieldTest {

    private fun hints(vararg rows: String) = Field(rows.toList()).hints()

    @Test
    fun `a safe square without mines around shows 0`() {
        assertThat(hints(".")).containsExactly("0")
    }

    @Test
    fun `a mine stays a mine`() {
        assertThat(hints("*")).containsExactly("*")
    }
}
