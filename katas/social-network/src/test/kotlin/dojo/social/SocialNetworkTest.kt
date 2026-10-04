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

    @Test
    fun `reading - Alice sees all of Thomas's messages, newest first`() {
        network.post("Thomas", "First")
        network.post("Alice", "Not Thomas")
        network.post("Thomas", "Second")

        assertThat(network.timeline("Thomas").map { it.text }).containsExactly("Second", "First")
    }

    @Test
    fun `following - Charlie's wall aggregates the users Charlie follows`() {
        network.post("Thomas", "Thomas speaks")
        network.post("Alice", "Alice speaks")
        network.post("Bob", "Bob is not followed")
        network.post("Charlie", "Charlie speaks")

        network.follow("Charlie", "Thomas")
        network.follow("Charlie", "Alice")

        assertThat(network.wall("Charlie").map { it.text }).containsExactly("Charlie speaks", "Alice speaks", "Thomas speaks")
    }
}
