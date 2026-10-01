// Copyright (c) 2026 Riadh MNASRI. Licensed under the MIT License.
package dojo.foobarqix

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.CsvSource

class FooBarQixTest {

    @Test
    fun `keeps a number no rule applies to`() {
        assertThat(FooBarQix.step1.compute("1")).isEqualTo("1")
    }

    @Test
    fun `says Foo for a number divisible by 3`() {
        assertThat(FooBarQix.step1.compute("6")).isEqualTo("Foo")
    }

    @Test
    fun `says Bar for a number divisible by 5`() {
        assertThat(FooBarQix.step1.compute("10")).isEqualTo("Bar")
    }

    @Test
    fun `adds Foo for each digit 3`() {
        assertThat(FooBarQix.step1.compute("13")).isEqualTo("Foo")
        assertThat(FooBarQix.step1.compute("3")).isEqualTo("FooFoo")
    }

    @Test
    fun `translates digits 3 and 5 in their order`() {
        assertThat(FooBarQix.step1.compute("53")).isEqualTo("BarFoo")
        assertThat(FooBarQix.step1.compute("15")).isEqualTo("FooBarBar")
    }

    @Test
    fun `handles 7 as Qix`() {
        assertThat(FooBarQix.step1.compute("7")).isEqualTo("QixQix")
        assertThat(FooBarQix.step1.compute("21")).isEqualTo("FooQix")
    }

    @ParameterizedTest(name = "{0} => {1}")
    @CsvSource(
        "1, 1", "2, 2", "3, FooFoo", "4, 4", "5, BarBar", "6, Foo", "7, QixQix", "8, 8", "9, Foo",
        "10, Bar", "13, Foo", "15, FooBarBar", "21, FooQix", "33, FooFooFoo", "51, FooBar", "53, BarFoo",
    )
    fun `matches every example of step 1`(input: String, expected: String) {
        assertThat(FooBarQix.step1.compute(input)).isEqualTo(expected)
    }

    @Test
    fun `step 2 - keeps a trace of zeros when no rule applies`() {
        assertThat(FooBarQix.step2.compute("101")).isEqualTo("1*1")
    }

    @Test
    fun `step 2 - interleaves zero traces with digit words`() {
        assertThat(FooBarQix.step2.compute("303")).isEqualTo("FooFoo*Foo")
    }

    @Test
    fun `step 2 - traces the zero of 10 after Bar`() {
        assertThat(FooBarQix.step2.compute("10")).isEqualTo("Bar*")
    }

    @ParameterizedTest(name = "{0} => {1}")
    @CsvSource("101, 1*1", "303, FooFoo*Foo", "105, FooBarQix*Bar", "10101, FooQix**")
    fun `step 2 - matches every example`(input: String, expected: String) {
        assertThat(FooBarQix.step2.compute(input)).isEqualTo(expected)
    }
}
