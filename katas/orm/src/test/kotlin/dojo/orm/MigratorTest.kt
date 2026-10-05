// Copyright (c) 2026 Riadh MNASRI. Licensed under the MIT License.
package dojo.orm

import org.assertj.core.api.Assertions.assertThat
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
}
