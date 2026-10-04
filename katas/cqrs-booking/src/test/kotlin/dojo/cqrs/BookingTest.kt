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
}
