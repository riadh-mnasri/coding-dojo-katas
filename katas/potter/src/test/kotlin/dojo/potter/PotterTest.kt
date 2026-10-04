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
}
