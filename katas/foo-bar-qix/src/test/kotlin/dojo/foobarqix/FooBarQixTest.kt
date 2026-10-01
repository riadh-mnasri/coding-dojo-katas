// Copyright (c) 2026 Riadh MNASRI. Licensed under the MIT License.
package dojo.foobarqix

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Nested
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.CsvSource

class FooBarQixTest {

    @Nested
    inner class Step1 {
        private val fooBarQix = FooBarQix.step1

        @ParameterizedTest
        @CsvSource("1, 1", "2, 2", "4, 4", "8, 8")
        fun `keeps the number when no rule applies`(input: String, expected: String) {
            assertThat(fooBarQix.compute(input)).isEqualTo(expected)
        }

        @ParameterizedTest
        @CsvSource("6, Foo", "9, Foo", "10, Bar")
        fun `divisible by 3 gives Foo and by 5 gives Bar`(input: String, expected: String) {
            assertThat(fooBarQix.compute(input)).isEqualTo(expected)
        }

        @ParameterizedTest
        @CsvSource("3, FooFoo", "5, BarBar", "7, QixQix")
        fun `divisor rules come before digit rules`(input: String, expected: String) {
            assertThat(fooBarQix.compute(input)).isEqualTo(expected)
        }

        @ParameterizedTest
        @CsvSource("13, Foo", "15, FooBarBar", "21, FooQix", "33, FooFooFoo", "51, FooBar", "53, BarFoo")
        fun `digits are translated in their order`(input: String, expected: String) {
            assertThat(fooBarQix.compute(input)).isEqualTo(expected)
        }
    }

    @Nested
    inner class Step2 {
        private val fooBarQix = FooBarQix.step2

        @ParameterizedTest
        @CsvSource("101, 1*1", "10101, FooQix**")
        fun `zeros are kept as stars`(input: String, expected: String) {
            assertThat(fooBarQix.compute(input)).isEqualTo(expected)
        }

        @ParameterizedTest
        @CsvSource("303, FooFoo*Foo", "105, FooBarQix*Bar", "10, Bar*")
        fun `stars are interleaved with the words`(input: String, expected: String) {
            assertThat(fooBarQix.compute(input)).isEqualTo(expected)
        }

        @ParameterizedTest
        @CsvSource("1, 1", "33, FooFooFoo", "53, BarFoo")
        fun `numbers without zero behave as in step 1`(input: String, expected: String) {
            assertThat(fooBarQix.compute(input)).isEqualTo(expected)
        }
    }
}
