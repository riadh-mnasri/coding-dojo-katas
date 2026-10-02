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
}
