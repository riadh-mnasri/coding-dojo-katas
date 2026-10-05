// Copyright (c) 2026 Riadh MNASRI. Licensed under the MIT License.
package dojo.orm

import java.time.LocalDate

/** Les contacts que l'énoncé place dans la base de production. */
object ProductionContacts {
    val all = listOf(
        Person("Elon", "Musk", LocalDate.of(1971, 6, 28)),
        Person("Kamala", "Harris", LocalDate.of(1964, 10, 20)),
        Person("Joe", "Biden", LocalDate.of(1942, 11, 20)),
        Person("Greta", "Thunberg", LocalDate.of(2003, 1, 3)),
        Person("Donald", "Trump", LocalDate.of(1946, 6, 14)),
        Person("Angela", "Merkel", LocalDate.of(1954, 7, 17)),
        Person("Barack", "Obama", LocalDate.of(1961, 8, 4)),
        Person("Mark", "Zuckerberg", LocalDate.of(1984, 5, 14)),
        Person("Jeff", "Bezos", LocalDate.of(1964, 1, 12)),
    )

    fun seed(contacts: Contacts) = all.forEach { contacts.save(it) }
}
