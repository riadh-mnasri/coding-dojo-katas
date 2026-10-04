// Copyright (c) 2026 Riadh MNASRI. Licensed under the MIT License.
package dojo.birthday

import java.nio.file.Path
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import kotlin.io.path.readLines

/** Adaptateur du port [FriendRepository] : le fichier plat de l'énoncé, avec sa ligne d'en-tête. */
class FlatFileFriendRepository(private val file: Path) : FriendRepository {
    override fun all(): List<Friend> = file.readLines().drop(1).filter { it.isNotBlank() }.map { line ->
        val (lastName, firstName, birthDate, email) = line.split(",").map(String::trim)
        Friend(lastName, firstName, LocalDate.parse(birthDate, DATE), email)
    }

    private companion object {
        val DATE: DateTimeFormatter = DateTimeFormatter.ofPattern("yyyy/MM/dd")
    }
}
