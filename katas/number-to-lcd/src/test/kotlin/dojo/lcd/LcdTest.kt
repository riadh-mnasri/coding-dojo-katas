// Copyright (c) 2026 Riadh MNASRI. Licensed under the MIT License.
package dojo.lcd

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

class LcdTest {

    private fun lines(vararg rows: String) = rows.joinToString("\n")

    @Test
    fun `draws a 1`() {
        assertThat(Lcd.render(1)).isEqualTo(lines("   ", "  |", "  |"))
    }

    @Test
    fun `draws a 2`() {
        assertThat(Lcd.render(2)).isEqualTo(lines(" _ ", " _|", "|_ "))
    }
}
