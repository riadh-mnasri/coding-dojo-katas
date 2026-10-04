// Copyright (c) 2026 Riadh MNASRI. Licensed under the MIT License.
package dojo.args

import org.assertj.core.api.Assertions.assertThat
import org.assertj.core.api.Assertions.assertThatThrownBy
import org.junit.jupiter.api.Test
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.CsvSource

class ArgsTest {

    @Test
    fun `a boolean flag is true when present`() {
        val args = Args("l", listOf("-l"))

        assertThat(args.boolean('l')).isTrue()
    }

    @Test
    fun `an absent boolean flag is false`() {
        assertThat(Args("l", emptyList()).boolean('l')).isFalse()
    }

    @Test
    fun `reads typed values as described by the schema`() {
        val args = Args("l,p#,d*", listOf("-l", "-p", "8080", "-d", "/usr/logs"))

        assertThat(args.boolean('l')).isTrue()
        assertThat(args.int('p')).isEqualTo(8080)
        assertThat(args.string('d')).isEqualTo("/usr/logs")
    }

    @Test
    fun `absent flags take a default value`() {
        val args = Args("l,p#,d*", emptyList())

        assertThat(args.boolean('l')).isFalse()
        assertThat(args.int('p')).isZero()
        assertThat(args.string('d')).isEmpty()
    }

    @Test
    fun `a negative integer is a value, not a flag, and order does not matter`() {
        val args = Args("l,p#,d*", listOf("-d", "/tmp", "-p", "-3", "-l"))

        assertThat(args.int('p')).isEqualTo(-3)
        assertThat(args.string('d')).isEqualTo("/tmp")
        assertThat(args.boolean('l')).isTrue()
    }

    @ParameterizedTest(name = "{0} -> {1}")
    @CsvSource(
        delimiter = '|',
        value = [
            "-x | Unknown flag -x",
            "-p | Flag -p expects an integer value",
            "-p abc | Flag -p expects an integer, got 'abc'",
            "-d | Flag -d expects a string value",
            "oops | Expected a flag like -l, got 'oops'",
        ],
    )
    fun `explains exactly what is wrong`(arguments: String, message: String) {
        assertThatThrownBy { Args("l,p#,d*", arguments.split(" ")) }
            .isInstanceOf(ArgsException::class.java)
            .hasMessage(message)
    }

    @Test
    fun `reads lists of strings and of integers`() {
        val args = Args("g[*],n[#]", listOf("-g", "this,is,a,list", "-n", "1,2,-3,5"))

        assertThat(args.strings('g')).containsExactly("this", "is", "a", "list")
        assertThat(args.ints('n')).containsExactly(1, 2, -3, 5)
        assertThat(Args("g[*],n[#]", emptyList()).ints('n')).isEmpty()
    }
}
