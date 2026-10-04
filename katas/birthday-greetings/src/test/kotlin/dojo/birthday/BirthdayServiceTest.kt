// Copyright (c) 2026 Riadh MNASRI. Licensed under the MIT License.
package dojo.birthday

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import java.time.LocalDate

class BirthdayServiceTest {

    /** Adaptateurs de test : un carnet en mémoire et une boîte d'envoi qui garde les messages. */
    private class InMemoryFriends(private val friends: List<Friend>) : FriendRepository {
        override fun all() = friends
    }

    private class Outbox : MessageSender {
        val sent = mutableListOf<Message>()
        override fun send(message: Message) {
            sent += message
        }
    }

    private val outbox = Outbox()

    private fun service(vararg friends: Friend) = BirthdayService(InMemoryFriends(friends.toList()), outbox)

    @Test
    fun `nobody known, nobody greeted`() {
        service().sendGreetings(LocalDate.of(2026, 10, 8))

        assertThat(outbox.sent).isEmpty()
    }

    @Test
    fun `a friend is greeted on their birthday`() {
        val john = Friend("Doe", "John", LocalDate.of(1982, 10, 8), "john.doe@foobar.com")

        service(john).sendGreetings(LocalDate.of(2026, 10, 8))

        assertThat(outbox.sent).containsExactly(Message("john.doe@foobar.com", "Happy birthday!", "Happy birthday, dear John!"))
    }
}
