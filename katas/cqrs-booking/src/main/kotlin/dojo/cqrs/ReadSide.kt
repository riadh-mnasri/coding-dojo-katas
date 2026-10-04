// Copyright (c) 2026 Riadh MNASRI. Licensed under the MIT License.
package dojo.cqrs

import java.time.LocalDate

/** Côté lecture : un modèle taillé pour répondre aux requêtes, alimenté par les notifications. */
class ReadRegistry(val rooms: List<Room>) : BookingListener {
    override fun booked(booking: Booking) {}
}

/** Une requête renvoie des données et ne change rien. */
class QueryService(private val registry: ReadRegistry) {
    fun freeRooms(arrival: LocalDate, departure: LocalDate): List<Room> = registry.rooms
}
