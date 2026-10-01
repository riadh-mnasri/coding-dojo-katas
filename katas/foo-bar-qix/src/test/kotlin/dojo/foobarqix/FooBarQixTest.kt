// Copyright (c) 2026 Riadh MNASRI. Licensed under the MIT License.
package dojo.foobarqix

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

class FooBarQixTest {

    @Test
    fun `keeps a number no rule applies to`() {
        assertThat(FooBarQix.compute("1")).isEqualTo("1")
    }

    @Test
    fun `says Foo for a number divisible by 3`() {
        assertThat(FooBarQix.compute("6")).isEqualTo("Foo")
    }

    @Test
    fun `says Bar for a number divisible by 5`() {
        assertThat(FooBarQix.compute("10")).isEqualTo("Bar")
    }

    @Test
    fun `adds Foo for each digit 3`() {
        assertThat(FooBarQix.compute("13")).isEqualTo("Foo")
        assertThat(FooBarQix.compute("3")).isEqualTo("FooFoo")
    }
}
