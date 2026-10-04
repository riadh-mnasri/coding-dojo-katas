// Copyright (c) 2026 Riadh MNASRI. Licensed under the MIT License.
package dojo.birthday

import java.time.LocalDate

/**
 * Le cœur de l'application : il ne sait ni lire un fichier ni envoyer un mail,
 * il ne parle qu'aux ports [FriendRepository] et [MessageSender].
 */
class BirthdayService(private val friends: FriendRepository, private val sender: MessageSender) {

    fun sendGreetings(today: LocalDate) {
        val everybody = friends.all()
        val celebrated = everybody.filter { it.hasBirthdayOn(today) }
        celebrated.forEach { sender.send(greeting(it)) }
        everybody.forEach { reader ->
            val others = celebrated - reader
            if (others.isNotEmpty()) sender.send(reminder(reader, others))
        }
    }

    private fun greeting(friend: Friend) = Message(friend.email, "Happy birthday!", "Happy birthday, dear ${friend.firstName}!")

    private fun reminder(reader: Friend, celebrated: List<Friend>): Message {
        val names = celebrated.map { "${it.firstName} ${it.lastName}" }
        val listed = if (names.size == 1) names.single() else names.dropLast(1).joinToString(", ") + " and " + names.last()
        return Message(
            reader.email,
            "Birthday Reminder",
            "Dear ${reader.firstName},\n\nToday is $listed's birthday.\nDon't forget to send them a message !",
        )
    }
}
