// Copyright (c) 2026 Riadh MNASRI. Licensed under the MIT License.
package dojo.args

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

class ArgsTest {

    @Test
    fun `a boolean flag is true when present`() {
        val args = Args("l", listOf("-l"))

        assertThat(args.boolean('l')).isTrue()
    }

    @Test
    fun `an absent boolean flag is false`() {
        assertThat(Args("l", emptyList()).boolean('l')).isFalse()
    }
}
