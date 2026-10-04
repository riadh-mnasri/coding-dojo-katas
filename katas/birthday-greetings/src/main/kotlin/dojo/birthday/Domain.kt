// Copyright (c) 2026 Riadh MNASRI. Licensed under the MIT License.
package dojo.birthday

import java.time.LocalDate

data class Friend(val lastName: String, val firstName: String, val birthDate: LocalDate, val email: String)

data class Message(val to: String, val subject: String, val body: String)

/** Port secondaire : d'où viennent les amis (fichier plat, base SQLite...). */
fun interface FriendRepository {
    fun all(): List<Friend>
}

/** Port secondaire : comment part le message (email, SMS...). */
fun interface MessageSender {
    fun send(message: Message)
}
