// Copyright (c) 2026 Riadh MNASRI. Licensed under the MIT License.
package dojo.birthday

import java.time.LocalDate

class BirthdayService(private val friends: FriendRepository, private val sender: MessageSender) {
    fun sendGreetings(today: LocalDate) {
        val everybody = friends.all()
        val celebrated = everybody.filter { it.hasBirthdayOn(today) }
        celebrated.forEach { friend ->
            sender.send(Message(friend.email, "Happy birthday!", "Happy birthday, dear ${friend.firstName}!"))
        }
        celebrated.forEach { friend ->
            (everybody - friend).forEach { other ->
                sender.send(
                    Message(
                        other.email,
                        "Birthday Reminder",
                        "Dear ${other.firstName},\n\nToday is ${friend.firstName} ${friend.lastName}'s birthday.\n" +
                            "Don't forget to send them a message !",
                    ),
                )
            }
        }
    }
}
