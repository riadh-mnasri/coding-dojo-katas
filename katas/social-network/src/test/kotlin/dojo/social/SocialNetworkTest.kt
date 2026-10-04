// Copyright (c) 2026 Riadh MNASRI. Licensed under the MIT License.
package dojo.social

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import java.time.Instant

class SocialNetworkTest {

    /** Une horloge qui avance d'une minute à chaque lecture : chaque message a son propre instant. */
    private var minute = 0L
    private val network = SocialNetwork { Instant.parse("2026-10-05T10:00:00Z").plusSeconds(60 * minute++) }

    @Test
    fun `posting - Thomas publishes a message`() {
        network.post("Thomas", "Hello world")

        assertThat(network.timeline("Thomas").map { it.text }).containsExactly("Hello world")
    }
}
