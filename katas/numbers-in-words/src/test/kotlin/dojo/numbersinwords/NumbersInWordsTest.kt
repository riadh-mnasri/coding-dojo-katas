// Copyright (c) 2026 Riadh MNASRI. Licensed under the MIT License.
package dojo.numbersinwords

import org.assertj.core.api.Assertions.assertThat
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
}
