// Copyright (c) 2026 Riadh MNASRI. Licensed under the MIT License.
package dojo.hello

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

class GreeterTest {

    @Test
    fun `greets the world on the display`() {
        // Given
        val shown = mutableListOf<String>()
        val greeter = Greeter(display = { message -> shown += message })

        // When
        greeter.greet()

        // Then
        assertThat(shown).containsExactly("Hello, World!")
    }
}
