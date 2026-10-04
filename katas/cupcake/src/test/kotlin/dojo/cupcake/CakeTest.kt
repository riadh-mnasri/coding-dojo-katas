// Copyright (c) 2026 Riadh MNASRI. Licensed under the MIT License.
package dojo.cupcake

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

class CakeTest {

    @Test
    fun `a cupcake is a cake named after its emoji`() {
        val cake: Cake = Cupcake()

        assertThat(cake.name()).isEqualTo("🧁")
    }
}
