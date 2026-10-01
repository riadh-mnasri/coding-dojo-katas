// Copyright (c) 2026 Riadh MNASRI. Licensed under the MIT License.
package dojo.dictionaryreplacer

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

class DictionaryReplacerTest {

    @Test
    fun `empty text with an empty dictionary stays empty`() {
        assertThat(DictionaryReplacer.replace("", emptyMap())).isEmpty()
    }

    @Test
    fun `replaces a single placeholder`() {
        assertThat(DictionaryReplacer.replace("\$temp\$", mapOf("temp" to "temporary"))).isEqualTo("temporary")
    }
}
