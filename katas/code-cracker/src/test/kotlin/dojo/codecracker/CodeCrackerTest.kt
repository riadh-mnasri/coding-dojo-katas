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
}
