// Copyright (c) 2026 Riadh MNASRI. Licensed under the MIT License.
package dojo.orm

import org.jetbrains.exposed.dao.id.IntIdTable
import org.jetbrains.exposed.sql.Database
import org.jetbrains.exposed.sql.ResultRow
import org.jetbrains.exposed.sql.insertAndGetId
import org.jetbrains.exposed.sql.javatime.date
import org.jetbrains.exposed.sql.selectAll
import org.jetbrains.exposed.sql.transactions.transaction

/** La table, côté base : distincte de l'objet métier [Person]. */
object Persons : IntIdTable("persons") {
    val name = varchar("name", 100)
    val surname = varchar("surname", 100)
    val birthDate = date("birth_date")
}

/** Le carnet de contacts : traduit entre les objets métier et les lignes de la base. */
class Contacts(private val database: Database) {

    init {
        Migrator(database, Migrations.all).migrateTo(Migrations.LATEST)
    }

    fun save(person: Person): Int = transaction(database) {
        Persons.insertAndGetId {
            it[name] = person.name
            it[surname] = person.surname
            it[birthDate] = person.birthDate
        }.value
    }

    fun find(id: Int): Person? = transaction(database) {
        Persons.selectAll().where { Persons.id eq id }.singleOrNull()?.toPerson()
    }

    fun all(): List<Person> = transaction(database) { Persons.selectAll().map { it.toPerson() } }

    private fun ResultRow.toPerson() = Person(this[Persons.name], this[Persons.surname], this[Persons.birthDate])
}
