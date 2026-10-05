// Copyright (c) 2026 Riadh MNASRI. Licensed under the MIT License.
package dojo.orm

import org.assertj.core.api.Assertions.assertThat
import org.jetbrains.exposed.sql.transactions.transaction
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.io.TempDir
import java.nio.file.Path

class MigratorTest {

    @TempDir
    lateinit var directory: Path

    private val database by lazy { DatabaseConfig.forTests(directory) }

    @Test
    fun `a fresh database is migrated to the latest schema version`() {
        val migrator = Migrator(database, Migrations.all)

        migrator.migrateTo(Migrations.LATEST)

        assertThat(migrator.currentVersion()).isEqualTo(Migrations.LATEST)
    }

    @Test
    fun `migrating back to version 1 removes the email and keeps the persons`() {
        val migrator = Migrator(database, Migrations.all)
        migrator.migrateTo(2)
        transaction(database) {
            exec("INSERT INTO persons (name, surname, birth_date, email) VALUES ('Grace', 'Hopper', '1906-12-09', 'grace@navy.mil')")
        }

        migrator.migrateTo(1)

        assertThat(migrator.currentVersion()).isEqualTo(1)
        val columns = transaction(database) {
            exec("PRAGMA table_info(persons)") { rs -> generateSequence { if (rs.next()) rs.getString("name") else null }.toList() }
        }
        assertThat(columns).containsExactly("id", "name", "surname", "birth_date")
        val names = transaction(database) { exec("SELECT name FROM persons") { rs -> rs.next(); rs.getString(1) } }
        assertThat(names).isEqualTo("Grace")
    }
}
