// Copyright (c) 2026 Riadh MNASRI. Licensed under the MIT License.
package dojo.hello

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

class GreeterTest {

    /** Doublure écrite à la main : elle enregistre ce qu'on lui demande d'afficher. */
    private class RecordingDisplay : Display {
        val shown = mutableListOf<String>()
        override fun show(message: String) {
            shown += message
        }
    }

    @Test
    fun `greets the world on the display`() {
        // Given
        val display = RecordingDisplay()
        val greeter = Greeter(display)

        // When
        greeter.greet()

        // Then
        assertThat(display.shown).containsExactly("Hello, World!")
    }
}
