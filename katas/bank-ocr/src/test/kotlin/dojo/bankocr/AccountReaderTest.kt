// Copyright (c) 2026 Riadh MNASRI. Licensed under the MIT License.
package dojo.bankocr

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

class AccountReaderTest {

    @Test
    fun `user story 1 - reads an entry of zeros`() {
        val zeros = useCases("use-case-1.txt").first()

        assertThat(AccountReader.read(zeros.entry)).isEqualTo("000000000")
    }
}
