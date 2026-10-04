// Copyright (c) 2026 Riadh MNASRI. Licensed under the MIT License.
package dojo.cqrs

import java.time.LocalDate

data class Room(val name: String)

data class Booking(val clientId: String, val roomName: String, val arrival: LocalDate, val departure: LocalDate)
