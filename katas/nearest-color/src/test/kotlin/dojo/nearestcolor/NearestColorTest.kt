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
}
