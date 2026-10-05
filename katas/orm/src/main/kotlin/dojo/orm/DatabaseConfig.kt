// Copyright (c) 2026 Riadh MNASRI. Licensed under the MIT License.
package dojo.orm

import org.jetbrains.exposed.sql.Database
import java.nio.file.Path

object DatabaseConfig {
    /** La base de test, tests.db, dans le dossier donné. */
    fun forTests(directory: Path): Database = sqlite(directory.resolve("tests.db").toString())

    private fun sqlite(file: String): Database = Database.connect("jdbc:sqlite:$file", driver = "org.sqlite.JDBC")
}
