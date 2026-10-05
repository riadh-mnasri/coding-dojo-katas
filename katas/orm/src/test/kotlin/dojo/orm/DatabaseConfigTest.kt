// Copyright (c) 2026 Riadh MNASRI. Licensed under the MIT License.
package dojo.orm

import org.assertj.core.api.Assertions.assertThat
import org.assertj.core.api.Assertions.assertThatThrownBy
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.io.TempDir
import java.nio.file.Path

class DatabaseConfigTest {

    @Test
    fun `the production database comes from the DB_URL variable`(@TempDir directory: Path) {
        val url = "jdbc:sqlite:${directory.resolve("production.db")}"

        val database = DatabaseConfig.forProduction(mapOf("DB_URL" to url))

        assertThat(database.url).isEqualTo(url)
    }

    @Test
    fun `without DB_URL there is no production database`() {
        assertThatThrownBy { DatabaseConfig.forProduction(emptyMap()) }.isInstanceOf(IllegalStateException::class.java)
    }
}
