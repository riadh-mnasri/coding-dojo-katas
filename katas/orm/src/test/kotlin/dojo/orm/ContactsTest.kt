// Copyright (c) 2026 Riadh MNASRI. Licensed under the MIT License.
package dojo.orm

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.io.TempDir
import java.nio.file.Path
import java.time.LocalDate

/** Chaque test part d'une base de test neuve, tests.db, dans un dossier temporaire. */
class ContactsTest {

    @TempDir
    lateinit var directory: Path

    private val contacts by lazy { Contacts(DatabaseConfig.forTests(directory)) }

    @Test
    fun `a saved person can be read back`() {
        val id = contacts.save(Person("Margaret", "Hamilton", LocalDate.of(1936, 8, 17)))

        assertThat(contacts.find(id)).isEqualTo(Person("Margaret", "Hamilton", LocalDate.of(1936, 8, 17)))
    }

    @Test
    fun `the production contacts can be seeded`() {
        ProductionContacts.seed(contacts)

        assertThat(contacts.all()).hasSize(9).contains(
            Person("Greta", "Thunberg", LocalDate.of(2003, 1, 3)),
            Person("Elon", "Musk", LocalDate.of(1971, 6, 28)),
        )
    }

    @Test
    fun `a person's email is saved and read back`() {
        val ada = Person("Ada", "Lovelace", LocalDate.of(1815, 12, 10), email = "ada@analytical.engine")

        val id = contacts.save(ada)

        assertThat(contacts.find(id)).isEqualTo(ada)
    }
}
