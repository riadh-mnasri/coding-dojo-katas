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

    @Test
    fun `counts a mine on the same row`() {
        assertThat(hints(".*.")).containsExactly("1*1")
    }

    @Test
    fun `hints the kata example`() {
        assertThat(hints("*...", "....", ".*..", "....")).containsExactly("*100", "2210", "1*10", "1110")
    }

    @Test
    fun `a square surrounded by mines shows 8`() {
        assertThat(hints("***", "*.*", "***")).containsExactly("***", "*8*", "***")
    }
}
