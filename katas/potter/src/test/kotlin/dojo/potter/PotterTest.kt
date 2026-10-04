// Copyright (c) 2026 Riadh MNASRI. Licensed under the MIT License.
package dojo.potter

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

class PotterTest {

    private fun price(vararg books: Int) = Potter.price(books.toList())

    @Test
    fun `an empty basket costs nothing`() {
        assertThat(price()).isEqualByComparingTo("0")
    }

    @Test
    fun `identical books cost 8 euros each`() {
        assertThat(price(0)).isEqualByComparingTo("8")
        assertThat(price(1, 1, 1)).isEqualByComparingTo("24")
    }

    @Test
    fun `different books form a discounted set`() {
        assertThat(price(0, 1)).isEqualByComparingTo("15.20")
        assertThat(price(0, 2, 4)).isEqualByComparingTo("21.60")
        assertThat(price(0, 1, 2, 4)).isEqualByComparingTo("25.60")
        assertThat(price(0, 1, 2, 3, 4)).isEqualByComparingTo("30.00")
    }

    @Test
    fun `a basket is split into several sets`() {
        assertThat(price(0, 0, 1)).isEqualByComparingTo("23.20")
        assertThat(price(0, 0, 1, 1)).isEqualByComparingTo("30.40")
        assertThat(price(0, 0, 1, 2, 2, 3)).isEqualByComparingTo("40.80")
        assertThat(price(0, 1, 1, 2, 3, 4)).isEqualByComparingTo("38.00")
    }

    @Test
    fun `two sets of four are cheaper than a set of five and a set of three`() {
        assertThat(price(0, 0, 1, 1, 2, 2, 3, 4)).isEqualByComparingTo("51.20")
    }

    @Test
    fun `big baskets are priced quickly`() {
        // 10 lots de 4 (256 €) battent 5 lots de 5 + 5 lots de 3 (258 €). Valeur vérifiée par un oracle Python
        // indépendant, qui essaie toutes les combinaisons de titres.
        val basket = List(10) { 0 } + List(10) { 1 } + List(10) { 2 } + List(5) { 3 } + List(5) { 4 }

        assertThat(Potter.price(basket)).isEqualByComparingTo("256.00")
    }
}
