// Copyright (c) 2026 Riadh MNASRI. Licensed under the MIT License.
package dojo.numbersinwords

import org.assertj.core.api.Assertions.assertThat
import org.assertj.core.api.Assertions.assertThatThrownBy
import org.junit.jupiter.api.Test
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.CsvSource

class NumbersInWordsTest {

    @Test
    fun `zero is zero`() {
        assertThat(NumbersInWords.toWords(0)).isEqualTo("zero")
    }

    @ParameterizedTest(name = "{0} is {1}")
    @CsvSource("1, one", "7, seven", "10, ten", "13, thirteen", "19, nineteen")
    fun `units and teens have their own word`(number: Int, words: String) {
        assertThat(NumbersInWords.toWords(number)).isEqualTo(words)
    }

    @ParameterizedTest(name = "{0} is {1}")
    @CsvSource("20, twenty", "40, forty", "45, forty five", "99, ninety nine")
    fun `tens are followed by their unit`(number: Int, words: String) {
        assertThat(NumbersInWords.toWords(number)).isEqualTo(words)
    }

    @ParameterizedTest(name = "{0} is {1}")
    @CsvSource("100, one hundred", "745, seven hundred and forty five", "910, nine hundred and ten", "999, nine hundred and ninety nine")
    fun `hundreds link the rest with and`(number: Int, words: String) {
        assertThat(NumbersInWords.toWords(number)).isEqualTo(words)
    }

    @ParameterizedTest(name = "{0} is {1}")
    @CsvSource(
        "1000, one thousand",
        "1001, one thousand and one",
        "1234, one thousand two hundred and thirty four",
        "20000, twenty thousand",
        "745000, seven hundred and forty five thousand",
        "1000000, one million",
        "2001010, two million one thousand and ten",
        "999999999, nine hundred and ninety nine million nine hundred and ninety nine thousand nine hundred and ninety nine",
    )
    fun `thousands and millions are said group by group`(number: Int, words: String) {
        assertThat(NumbersInWords.toWords(number)).isEqualTo(words)
    }

    @ParameterizedTest(name = "{1} is {0}")
    @CsvSource("0, zero", "13, thirteen", "45, forty five", "745, seven hundred and forty five", "2001010, two million one thousand and ten")
    fun `step 2 - reads words back into a number`(number: Int, words: String) {
        assertThat(NumbersInWords.toNumber(words)).isEqualTo(number)
    }

    @org.junit.jupiter.api.Test
    fun `step 2 - every number round-trips through words`() {
        (0..20_000).forEach { assertThat(NumbersInWords.toNumber(NumbersInWords.toWords(it))).isEqualTo(it) }
        listOf(123_456_789, 999_999_999, 1_000_001).forEach {
            assertThat(NumbersInWords.toNumber(NumbersInWords.toWords(it))).isEqualTo(it)
        }
    }

    @Test
    fun `step 2 - unknown words are rejected`() {
        assertThatThrownBy { NumbersInWords.toNumber("seven hundred and fourty five") }
            .isInstanceOf(IllegalArgumentException::class.java)
    }

    @Test
    fun `only numbers from 0 to 999,999,999 can be written`() {
        assertThatThrownBy { NumbersInWords.toWords(-1) }.isInstanceOf(IllegalArgumentException::class.java)
        assertThatThrownBy { NumbersInWords.toWords(1_000_000_000) }.isInstanceOf(IllegalArgumentException::class.java)
    }
}
