// Copyright (c) 2026 Riadh MNASRI. Licensed under the MIT License.
package dojo.range

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.CsvSource

class RangeTest {

    private fun range(notation: String) = Range.parse(notation)

    @Test
    fun `contains the values inside`() {
        assertThat(range("[2,6)").contains(2, 4)).isTrue()
    }

    @Test
    fun `does not contain values outside or on an open end`() {
        assertThat(range("[2,6)").contains(-1, 1, 6, 10)).isFalse()
        assertThat(range("[2,6)").contains(-1)).isFalse()
        assertThat(range("[2,6)").contains(6)).isFalse()
    }

    @Test
    fun `lists all its points`() {
        assertThat(range("[2,6)").allPoints()).containsExactly(2, 3, 4, 5)
    }

    @ParameterizedTest(name = "{0} end points are {1} and {2}")
    @CsvSource("'[2,6)', 2, 5", "'[2,6]', 2, 6", "'(2,6)', 3, 5", "'(2,6]', 3, 6")
    fun `gives its end points`(notation: String, first: Int, last: Int) {
        assertThat(range(notation).endPoints()).isEqualTo(first to last)
    }

    @ParameterizedTest(name = "{0} contains {1}: {2}")
    @CsvSource(
        "'[2,5)', '[7,10)', false", "'[2,5)', '[3,10)', false", "'[3,5)', '[2,10)', false",
        "'[2,10)', '[3,5]', true", "'[3,5]', '[3,5)', true",
    )
    fun `contains another range`(outer: String, inner: String, expected: Boolean) {
        assertThat(range(outer).containsRange(range(inner))).isEqualTo(expected)
    }

    @ParameterizedTest(name = "{0} overlaps {1}: {2}")
    @CsvSource("'[2,5)', '[7,10)', false", "'[2,10)', '[3,5)', true", "'[3,5)', '[3,5)', true", "'[2,5)', '[3,10)', true", "'[3,5)', '[2,10)', true", "'[2,5)', '[5,10)', false")
    fun `overlaps another range`(left: String, right: String, expected: Boolean) {
        assertThat(range(left).overlapsRange(range(right))).isEqualTo(expected)
    }
}
