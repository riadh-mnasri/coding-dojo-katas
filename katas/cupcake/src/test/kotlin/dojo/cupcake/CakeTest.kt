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

    @Test
    fun `each topping adds its price`() {
        assertThat(Chocolate(Cupcake()).price()).isEqualByComparingTo("1.1")
        assertThat(Chocolate(Cookie()).price()).isEqualByComparingTo("2.1")
        assertThat(Nuts(Cookie()).price()).isEqualByComparingTo("2.2")
        assertThat(Nuts(Chocolate(Cookie())).price()).isEqualByComparingTo("2.3")
    }

    @Test
    fun `a bundle costs ten percent less than its cakes`() {
        assertThat(Bundle(Cupcake()).price()).isEqualByComparingTo("0.9")
        assertThat(Bundle(Cupcake(), Cookie()).price()).isEqualByComparingTo("2.7")
        assertThat(Bundle(Cupcake(), Cupcake(), Cookie()).price()).isEqualByComparingTo("3.6")
    }

    @Test
    fun `bundles can contain bundles`() {
        val bundle = Bundle(Bundle(Cupcake(), Cookie()), Cupcake())

        assertThat(bundle.price()).isEqualByComparingTo("3.33")
    }
}
