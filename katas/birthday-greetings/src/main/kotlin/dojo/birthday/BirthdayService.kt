// Copyright (c) 2026 Riadh MNASRI. Licensed under the MIT License.
package dojo.birthday

import java.time.LocalDate

class BirthdayService(private val friends: FriendRepository, private val sender: MessageSender) {
    fun sendGreetings(today: LocalDate) {}
}
