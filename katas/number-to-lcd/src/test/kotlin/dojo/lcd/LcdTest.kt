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

    @Test
    fun `draws every digit side by side, as in the kata`() {
        assertThat(Lcd.render(1234567890)).isEqualTo(
            lines(
                "    _  _     _  _  _  _  _  _ ",
                "  | _| _||_||_ |_   ||_||_|| |",
                "  ||_  _|  | _||_|  ||_| _||_|",
            ),
        )
    }

    /** L'exemple de la partie 2 : chaque barre horizontale a sa propre ligne, soit 2 × hauteur + 3 lignes. */
    @Test
    fun `stretches a 2 to width 3 and height 2`() {
        assertThat(Lcd.render(2, width = 3, height = 2)).isEqualTo(
            lines(" ___ ", "    |", "    |", " ___ ", "|    ", "|    ", " ___ "),
        )
    }

    @Test
    fun `stretches several digits`() {
        assertThat(Lcd.render(10, width = 2, height = 1)).isEqualTo(
            lines("     __ ", "   ||  |", "        ", "   ||  |", "     __ "),
        )
    }
}
