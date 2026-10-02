// Copyright (c) 2026 Riadh MNASRI. Licensed under the MIT License.
package dojo.wordwrap

import org.assertj.core.api.Assertions.assertThat
import org.assertj.core.api.Assertions.assertThatThrownBy
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

    @Test
    fun `a space just after the column is a valid break`() {
        assertThat(Wrapper.wrap("word word", 4)).isEqualTo("word\nword")
    }

    @Test
    fun `wraps a sentence on several lines`() {
        assertThat(Wrapper.wrap("word word word", 6)).isEqualTo("word\nword\nword")
        assertThat(Wrapper.wrap("word word word", 11)).isEqualTo("word word\nword")
    }

    @Test
    fun `cuts a long word in the middle of a sentence`() {
        assertThat(Wrapper.wrap("a verylongword b", 5)).isEqualTo("a\nveryl\nongwo\nrd b")
    }

    @Test
    fun `the column must be at least 1`() {
        assertThatThrownBy { Wrapper.wrap("word", 0) }.isInstanceOf(IllegalArgumentException::class.java)
    }
}
