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

    @ParameterizedTest(name = "page {0} of {1}: {2}")
    @CsvSource("2, 5, 1 (2) 3 4 5", "6, 7, 1 2 3 4 5 (6) 7")
    fun `part 1 - shows every page up to seven`(page: Int, total: Int, expected: String) {
        assertThat(PaginationSeven.render(page, total)).isEqualTo(expected)
    }

    @ParameterizedTest(name = "page {0} of {1}: {2}")
    @CsvSource("42, 100, 1 … 41 (42) 43 … 100", "5, 9, 1 … 4 (5) 6 … 9")
    fun `part 2 - folds far pages into ellipses`(page: Int, total: Int, expected: String) {
        assertThat(PaginationSeven.render(page, total)).isEqualTo(expected)
    }

    @ParameterizedTest(name = "page {0} of {1}: {2}")
    @CsvSource("2, 9, 1 (2) 3 4 5 … 9", "4, 9, 1 2 3 (4) 5 … 9", "1, 9, (1) 2 3 4 5 … 9")
    fun `part 3 - no ellipsis needed at the start`(page: Int, total: Int, expected: String) {
        assertThat(PaginationSeven.render(page, total)).isEqualTo(expected)
    }
}
