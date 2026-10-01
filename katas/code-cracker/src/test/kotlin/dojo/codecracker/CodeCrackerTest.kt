// Copyright (c) 2026 Riadh MNASRI. Licensed under the MIT License.
package dojo.codecracker

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

class CodeCrackerTest {

    private val cracker = CodeCracker.kata

    @Test
    fun `decrypts a single symbol`() {
        assertThat(cracker.decrypt("!")).isEqualTo("a")
    }

    @Test
    fun `decrypts a word`() {
        assertThat(cracker.decrypt("&£aad")).isEqualTo("hello")
    }

    @Test
    fun `keeps characters outside the key`() {
        assertThat(cracker.decrypt("&£aad, ldga(?")).isEqualTo("hello, world?")
    }

    @Test
    fun `encrypts a message`() {
        assertThat(cracker.encrypt("hello world")).isEqualTo("&£aad ldga(")
    }
}
