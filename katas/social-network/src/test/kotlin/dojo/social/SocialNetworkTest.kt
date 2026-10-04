// Copyright (c) 2026 Riadh MNASRI. Licensed under the MIT License.
package dojo.social

import org.assertj.core.api.Assertions.assertThat
import org.assertj.core.api.Assertions.assertThatThrownBy
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

    @Test
    fun `mentions - Alice mentions Charlie with an at sign`() {
        network.post("Alice", "Lunch with @Charlie today")
        network.post("Thomas", "Charlie without the at sign does not count")
        network.post("Bob", "Hi @Charlotte")

        assertThat(network.mentionsOf("Charlie").map { it.author }).containsExactly("Alice")
    }

    @Test
    fun `links - Thomas shares a link that leads back to the message`() {
        val post = network.post("Thomas", "Look at this")

        val link = network.linkTo(post)

        assertThat(link).isEqualTo("https://social.example/Thomas/messages/${post.id}")
        assertThat(network.open(link)).isEqualTo(post)
    }

    @Test
    fun `direct messages - Alice writes privately to Thomas`() {
        network.sendDirectMessage(from = "Alice", to = "Thomas", text = "Psst")

        assertThat(network.inbox("Thomas").map { it.from to it.text }).containsExactly("Alice" to "Psst")
        assertThat(network.timeline("Alice")).isEmpty()
        assertThat(network.inbox("Bob")).isEmpty()
    }

    @Test
    fun `links - a link to an unknown message is rejected`() {
        assertThatThrownBy { network.open("https://social.example/Thomas/messages/42") }
            .isInstanceOf(IllegalArgumentException::class.java)
    }
}
