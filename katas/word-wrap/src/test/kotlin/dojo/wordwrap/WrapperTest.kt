// Copyright (c) 2026 Riadh MNASRI. Licensed under the MIT License.
package dojo.wordwrap

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

class WrapperTest {

    @Test
    fun `an empty text stays empty`() {
        assertThat(Wrapper.wrap("", 10)).isEqualTo("")
    }
}
