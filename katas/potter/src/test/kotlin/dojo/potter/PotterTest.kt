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
}
