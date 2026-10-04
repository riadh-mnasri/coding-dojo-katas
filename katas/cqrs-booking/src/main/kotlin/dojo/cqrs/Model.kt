// Copyright (c) 2026 Riadh MNASRI. Licensed under the MIT License.
package dojo.cqrs

import java.time.LocalDate

data class Room(val name: String)

data class Booking(val clientId: String, val roomName: String, val arrival: LocalDate, val departure: LocalDate) {
    /** Deux séjours se chevauchent si chacun commence avant la fin de l'autre ; le jour du départ est libre. */
    fun overlaps(other: Booking) = arrival < other.departure && other.arrival < departure
}
