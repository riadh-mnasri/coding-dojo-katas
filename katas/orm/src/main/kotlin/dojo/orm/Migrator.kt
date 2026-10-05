// Copyright (c) 2026 Riadh MNASRI. Licensed under the MIT License.
package dojo.orm

import org.jetbrains.exposed.sql.Database
import org.jetbrains.exposed.sql.SchemaUtils
import org.jetbrains.exposed.sql.Table
import org.jetbrains.exposed.sql.Transaction
import org.jetbrains.exposed.sql.deleteAll
import org.jetbrains.exposed.sql.insert
import org.jetbrains.exposed.sql.selectAll
import org.jetbrains.exposed.sql.transactions.transaction

/** Une évolution du schéma, avec de quoi la défaire. */
class Migration(val version: Int, val up: Transaction.() -> Unit, val down: Transaction.() -> Unit)

/** La version du schéma, enregistrée dans la base elle-même. */
object SchemaVersion : Table("schema_version") {
    val version = integer("version")
}

class Migrator(private val database: Database, private val migrations: List<Migration>) {

    fun currentVersion(): Int = transaction(database) {
        SchemaUtils.create(SchemaVersion)
        SchemaVersion.selectAll().singleOrNull()?.get(SchemaVersion.version) ?: 0
    }

    fun migrateTo(target: Int) {
        val current = currentVersion()
        transaction(database) {
            migrations.filter { it.version in (current + 1)..target }.sortedBy { it.version }.forEach { it.up(this) }
            SchemaVersion.deleteAll()
            SchemaVersion.insert { it[version] = target }
        }
    }
}

object Migrations {
    val all = listOf(
        Migration(1, up = { SchemaUtils.create(Persons) }, down = { SchemaUtils.drop(Persons) }),
    )
    val LATEST = all.maxOf { it.version }
}
