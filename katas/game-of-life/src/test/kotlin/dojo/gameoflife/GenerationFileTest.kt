// Copyright (c) 2026 Riadh MNASRI. Licensed under the MIT License.
package dojo.gameoflife

import org.assertj.core.api.Assertions.assertThat
import org.assertj.core.api.Assertions.assertThatThrownBy
import org.junit.jupiter.api.Test

class GenerationFileTest {

    @Test
    fun `computes the next generation of the kata example`() {
        val input = """
            Generation 1:
            4 8
            ........
            ....*...
            ...**...
            ........
        """.trimIndent()

        assertThat(GenerationFile.next(input)).isEqualTo(
            """
            Generation 2:
            4 8
            ........
            ...**...
            ...**...
            ........
            """.trimIndent(),
        )
    }

    @Test
    fun `rejects a grid that does not match its declared size`() {
        val input = "Generation 1:\n2 3\n...\n..."

        assertThatThrownBy { GenerationFile.next(input.replace("2 3", "3 3")) }
            .isInstanceOf(IllegalArgumentException::class.java)
        assertThatThrownBy { GenerationFile.next(input.replace("2 3", "2 4")) }
            .isInstanceOf(IllegalArgumentException::class.java)
    }
}
