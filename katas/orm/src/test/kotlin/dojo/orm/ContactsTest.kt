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
}
