// Copyright (c) 2026 Riadh MNASRI. Licensed under the MIT License.
package dojo.anagram

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

class AnagramsTest {

    @Test
    fun `an empty dictionary gives no anagram`() {
        assertThat(Anagrams(emptyList()).twoWordAnagramsOf("documenting")).isEmpty()
    }
}
