// Copyright (c) 2026 Riadh MNASRI. Licensed under the MIT License.
package dojo.cqrs

import java.time.LocalDate

/** Côté lecture : un modèle taillé pour répondre aux requêtes, alimenté par les notifications. */
class ReadRegistry(private val rooms: List<Room>) : BookingListener {
    private val stays = mutableMapOf<String, MutableList<Booking>>()

    override fun booked(booking: Booking) {
        stays.getOrPut(booking.roomName) { mutableListOf() } += booking
    }

    fun freeRooms(arrival: LocalDate, departure: LocalDate): List<Room> = rooms.filter { room ->
        stays[room.name].orEmpty().none { it.arrival < departure && arrival < it.departure }
    }
}

/** Une requête renvoie des données et ne change rien. */
class QueryService(private val registry: ReadRegistry) {
    fun freeRooms(arrival: LocalDate, departure: LocalDate): List<Room> = registry.freeRooms(arrival, departure)
}
