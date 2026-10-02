// Copyright (c) 2026 Riadh MNASRI. Licensed under the MIT License.
package dojo.range

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

class RangeTest {

    private fun range(notation: String) = Range.parse(notation)

    @Test
    fun `contains the values inside`() {
        assertThat(range("[2,6)").contains(2, 4)).isTrue()
    }

    @Test
    fun `does not contain values outside or on an open end`() {
        assertThat(range("[2,6)").contains(-1, 1, 6, 10)).isFalse()
        assertThat(range("[2,6)").contains(-1)).isFalse()
        assertThat(range("[2,6)").contains(6)).isFalse()
    }

    @Test
    fun `lists all its points`() {
        assertThat(range("[2,6)").allPoints()).containsExactly(2, 3, 4, 5)
    }
}
