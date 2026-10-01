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

    @Test
    fun `replaces several placeholders in a sentence`() {
        val dictionary = mapOf("temp" to "temporary", "name" to "John Doe")

        val result = DictionaryReplacer.replace("\$temp\$ here comes the name \$name\$", dictionary)

        assertThat(result).isEqualTo("temporary here comes the name John Doe")
    }

    @Test
    fun `does not replace again inside a replaced value`() {
        val dictionary = mapOf("a" to "\$b\$", "b" to "boom")

        assertThat(DictionaryReplacer.replace("\$a\$", dictionary)).isEqualTo("\$b\$")
    }

    @Test
    fun `keeps unknown placeholders`() {
        assertThat(DictionaryReplacer.replace("\$unknown\$ stays", emptyMap())).isEqualTo("\$unknown\$ stays")
    }

    @Test
    fun `a lonely dollar is not a placeholder`() {
        assertThat(DictionaryReplacer.replace("costs 5\$", mapOf("x" to "y"))).isEqualTo("costs 5\$")
    }

    @Test
    fun `replaces the same placeholder each time it appears`() {
        assertThat(DictionaryReplacer.replace("\$a\$-\$a\$", mapOf("a" to "x"))).isEqualTo("x-x")
    }
}
