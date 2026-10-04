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

    @Test
    fun `friends born on another day are not greeted`() {
        val mary = Friend("Ann", "Mary", LocalDate.of(1975, 9, 11), "mary.ann@foobar.com")

        service(mary).sendGreetings(LocalDate.of(2026, 10, 8))

        assertThat(outbox.sent).isEmpty()
    }

    @Test
    fun `people born on February 29 are greeted on February 28 in other years`() {
        val leapling = Friend("Leap", "Lea", LocalDate.of(2000, 2, 29), "lea@foobar.com")

        service(leapling).sendGreetings(LocalDate.of(2027, 2, 28))
        assertThat(outbox.sent).hasSize(1)

        outbox.sent.clear()
        service(leapling).sendGreetings(LocalDate.of(2028, 2, 28))
        assertThat(outbox.sent).isEmpty()
    }

    @Test
    fun `the other friends receive a birthday reminder`() {
        val john = Friend("Doe", "John", LocalDate.of(1982, 10, 8), "john.doe@foobar.com")
        val mary = Friend("Ann", "Mary", LocalDate.of(1975, 9, 11), "mary.ann@foobar.com")

        service(john, mary).sendGreetings(LocalDate.of(2026, 10, 8))

        assertThat(outbox.sent).contains(
            Message(
                "mary.ann@foobar.com",
                "Birthday Reminder",
                "Dear Mary,\n\nToday is John Doe's birthday.\nDon't forget to send them a message !",
            ),
        )
        assertThat(outbox.sent.filter { it.to == "john.doe@foobar.com" && it.subject == "Birthday Reminder" }).isEmpty()
    }

    @Test
    fun `a single reminder lists every birthday of the day`() {
        val john = Friend("Doe", "John", LocalDate.of(1982, 10, 8), "john.doe@foobar.com")
        val lea = Friend("Leap", "Lea", LocalDate.of(1990, 10, 8), "lea@foobar.com")
        val max = Friend("Power", "Max", LocalDate.of(1970, 10, 8), "max@foobar.com")
        val mary = Friend("Ann", "Mary", LocalDate.of(1975, 9, 11), "mary.ann@foobar.com")

        service(john, lea, max, mary).sendGreetings(LocalDate.of(2026, 10, 8))

        val remindersToMary = outbox.sent.filter { it.to == "mary.ann@foobar.com" }
        assertThat(remindersToMary).containsExactly(
            Message(
                "mary.ann@foobar.com",
                "Birthday Reminder",
                "Dear Mary,\n\nToday is John Doe, Lea Leap and Max Power's birthday.\nDon't forget to send them a message !",
            ),
        )
        assertThat(outbox.sent.filter { it.to == "john.doe@foobar.com" && it.subject == "Birthday Reminder" }.map { it.body })
            .containsExactly("Dear John,\n\nToday is Lea Leap and Max Power's birthday.\nDon't forget to send them a message !")
    }
}
