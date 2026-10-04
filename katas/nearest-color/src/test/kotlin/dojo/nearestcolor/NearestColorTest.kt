// Copyright (c) 2026 Riadh MNASRI. Licensed under the MIT License.
package dojo.nearestcolor

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

class NearestColorTest {

    private val primaries = Palette("F00", "0F0", "00F")

    @Test
    fun `a color of the palette is its own nearest color`() {
        assertThat(primaries.nearest("0F0")).isEqualTo("0F0")
    }

    @Test
    fun `the nearest color of F42 is red`() {
        assertThat(primaries.nearest("F42")).isEqualTo("F00")
    }

    @Test
    fun `yellow is as near to red as to green`() {
        assertThat(primaries.nearestColors("FF0")).containsExactly("F00", "0F0")
    }

    @Test
    fun `six-digit colors work as well`() {
        val palette = Palette("FF0000", "00FF00", "0000FF", "808080")

        assertThat(palette.nearest("F42")).isEqualTo("FF0000")
        assertThat(palette.nearest("7A8090")).isEqualTo("808080")
    }
}
