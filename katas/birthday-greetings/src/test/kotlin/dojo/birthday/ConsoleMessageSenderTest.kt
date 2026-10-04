// Copyright (c) 2026 Riadh MNASRI. Licensed under the MIT License.
package dojo.birthday

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import java.io.ByteArrayOutputStream
import java.io.PrintStream

class ConsoleMessageSenderTest {

    @Test
    fun `prints each message, as another sender would send an SMS`() {
        val output = ByteArrayOutputStream()

        ConsoleMessageSender(PrintStream(output)).send(Message("john.doe@foobar.com", "Happy birthday!", "Happy birthday, dear John!"))

        assertThat(output.toString()).isEqualTo("To: john.doe@foobar.com\nSubject: Happy birthday!\n\nHappy birthday, dear John!\n\n")
    }
}
