// Copyright (c) 2026 Riadh MNASRI. Licensed under the MIT License.
package dojo.cqrs

/** Ce qui doit être prévenu quand une réservation est enregistrée : le côté lecture, typiquement. */
fun interface BookingListener {
    fun booked(booking: Booking)
}

/** Côté écriture : la source de vérité des réservations. */
class WriteRegistry(private val listeners: List<BookingListener>)

/** Une commande change l'état et ne renvoie rien. */
class CommandService(private val registry: WriteRegistry)
