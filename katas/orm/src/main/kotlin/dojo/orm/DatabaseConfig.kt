// Copyright (c) 2026 Riadh MNASRI. Licensed under the MIT License.
package dojo.orm

import org.jetbrains.exposed.sql.Database
import java.nio.file.Path

object DatabaseConfig {
    /** La base de test, tests.db, dans le dossier donné. */
    fun forTests(directory: Path): Database = sqlite(directory.resolve("tests.db").toString())

    /** La base de production, dont l'URL JDBC est donnée par la variable d'environnement DB_URL. */
    fun forProduction(environment: Map<String, String> = System.getenv()): Database {
        val url = environment["DB_URL"] ?: error("DB_URL is not set")
        return Database.connect(url)
    }

    private fun sqlite(file: String): Database = Database.connect("jdbc:sqlite:$file", driver = "org.sqlite.JDBC")
}
