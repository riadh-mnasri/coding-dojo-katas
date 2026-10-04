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

    @Test
    fun `a cookie is a cake too`() {
        assertThat(Cookie().name()).isEqualTo("🍪")
    }

    @Test
    fun `a topping decorates the name`() {
        assertThat(Chocolate(Cupcake()).name()).isEqualTo("🧁 with 🍫")
        assertThat(Chocolate(Cookie()).name()).isEqualTo("🍪 with 🍫")
    }

    @Test
    fun `toppings are listed in the order they were added`() {
        assertThat(Nuts(Chocolate(Cookie())).name()).isEqualTo("🍪 with 🍫 and 🥜")
        assertThat(Chocolate(Nuts(Cookie())).name()).isEqualTo("🍪 with 🥜 and 🍫")
    }

    @Test
    fun `a cupcake costs 1 dollar and a cookie 2`() {
        assertThat(Cupcake().price()).isEqualByComparingTo("1")
        assertThat(Cookie().price()).isEqualByComparingTo("2")
    }
}
