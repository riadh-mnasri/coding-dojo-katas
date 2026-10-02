// Copyright (c) 2026 Riadh MNASRI. Licensed under the MIT License.
package dojo.wordwrap

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

class WrapperTest {

    @Test
    fun `an empty text stays empty`() {
        assertThat(Wrapper.wrap("", 10)).isEqualTo("")
    }

    @Test
    fun `a word longer than the column is cut`() {
        assertThat(Wrapper.wrap("longword", 4)).isEqualTo("long\nword")
    }

    @Test
    fun `breaks at the space between two words`() {
        assertThat(Wrapper.wrap("word word", 6)).isEqualTo("word\nword")
    }
}
