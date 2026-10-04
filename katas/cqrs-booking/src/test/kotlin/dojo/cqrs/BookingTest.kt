// Copyright (c) 2026 Riadh MNASRI. Licensed under the MIT License.
package dojo.cqrs

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import java.time.LocalDate

class BookingTest {

    private val rooms = listOf(Room("101"), Room("102"), Room("201"))
    private val readRegistry = ReadRegistry(rooms)
    private val writeRegistry = WriteRegistry(listOf(readRegistry))
    private val commands = CommandService(writeRegistry)
    private val queries = QueryService(readRegistry)

    private fun day(dayOfMonth: Int) = LocalDate.of(2026, 7, dayOfMonth)

    @Test
    fun `every room is free before any booking`() {
        assertThat(queries.freeRooms(arrival = day(10), departure = day(12))).containsExactlyElementsOf(rooms)
    }

    @Test
    fun `a booked room is not free during the stay`() {
        commands.bookARoom(Booking("ann", "101", arrival = day(10), departure = day(12)))

        assertThat(queries.freeRooms(arrival = day(11), departure = day(13))).containsExactly(Room("102"), Room("201"))
    }

    @Test
    fun `a room is free again from the departure day`() {
        commands.bookARoom(Booking("ann", "101", arrival = day(10), departure = day(12)))

        assertThat(queries.freeRooms(arrival = day(12), departure = day(14))).contains(Room("101"))
        assertThat(queries.freeRooms(arrival = day(8), departure = day(10))).contains(Room("101"))
    }
}
