// Copyright (c) 2026 Riadh MNASRI. Licensed under the MIT License.
package dojo.bankocr

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.MethodSource

class AccountReaderTest {

    @Test
    fun `user story 1 - reads an entry of zeros`() {
        val zeros = useCases("use-case-1.txt").first()

        assertThat(AccountReader.read(zeros.entry)).isEqualTo("000000000")
    }

    @ParameterizedTest
    @MethodSource("useCase1")
    fun `user story 1 - reads every entry of use case 1`(case: UseCase) {
        assertThat(AccountReader.read(case.entry)).isEqualTo(case.expected)
    }

    companion object {
        @JvmStatic
        fun useCase1() = useCases("use-case-1.txt")

        @JvmStatic
        fun useCase3() = useCases("use-case-3.txt")
    }

    @Test
    fun `user story 2 - a valid account number has a checksum divisible by 11`() {
        assertThat(AccountReader.isValid("345882865")).isTrue()
        assertThat(AccountReader.isValid("457508000")).isTrue()
        assertThat(AccountReader.isValid("664371495")).isFalse()
    }

    @ParameterizedTest
    @MethodSource("useCase3")
    fun `user story 3 - reports illegible numbers, using the kata fixtures`(case: UseCase) {
        assertThat(AccountReader.report(case.entry)).isEqualTo(case.expected)
    }

    @Test
    fun `user story 3 - reports a wrong checksum`() {
        assertThat(AccountReader.reportNumber("664371495")).isEqualTo("664371495 ERR")
        assertThat(AccountReader.reportNumber("457508000")).isEqualTo("457508000")
    }
}
