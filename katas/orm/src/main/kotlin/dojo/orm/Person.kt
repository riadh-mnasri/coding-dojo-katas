// Copyright (c) 2026 Riadh MNASRI. Licensed under the MIT License.
package dojo.orm

import java.time.LocalDate

/** L'objet métier : il ne sait rien de la base. */
data class Person(val name: String, val surname: String, val birthDate: LocalDate)
