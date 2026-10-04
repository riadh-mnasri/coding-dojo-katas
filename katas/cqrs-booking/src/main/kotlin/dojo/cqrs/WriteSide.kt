// Copyright (c) 2026 Riadh MNASRI. Licensed under the MIT License.
package dojo.cqrs

/** Ce qui doit être prévenu quand une réservation est enregistrée : le côté lecture, typiquement. */
fun interface BookingListener {
    fun booked(booking: Booking)
}

/** Côté écriture : la source de vérité des réservations. */
class WriteRegistry(private val listeners: List<BookingListener>) {
    private val bookings = mutableListOf<Booking>()

    /** Le côté écriture vérifie les règles sur ses propres données, jamais sur le modèle de lecture. */
    fun add(booking: Booking) {
        check(bookings.none { it.roomName == booking.roomName && it.overlaps(booking) }) {
            "Room ${booking.roomName} is already booked during that stay"
        }
        bookings += booking
        listeners.forEach { it.booked(booking) }
    }
}

/** Une commande change l'état et ne renvoie rien. */
class CommandService(private val registry: WriteRegistry) {
    fun bookARoom(booking: Booking) {
        registry.add(booking)
    }
}
