// Copyright (c) 2026 Riadh MNASRI. Licensed under the MIT License.
package dojo.gameoflife

import org.assertj.core.api.Assertions.assertThat
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
}
