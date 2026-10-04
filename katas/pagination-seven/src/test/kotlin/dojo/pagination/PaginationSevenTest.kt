// Copyright (c) 2026 Riadh MNASRI. Licensed under the MIT License.
package dojo.pagination

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.CsvSource

class PaginationSevenTest {

    @Test
    fun `a single page is the current one`() {
        assertThat(PaginationSeven.render(page = 1, total = 1)).isEqualTo("(1)")
    }
}
