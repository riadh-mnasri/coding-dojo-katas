// Copyright (c) 2026 Riadh MNASRI. Licensed under the MIT License.
package dojo.birthday

import java.time.LocalDate
import kotlin.io.path.Path

/** Point d'assemblage : c'est le seul endroit qui choisit les adaptateurs. */
fun main(args: Array<String>) {
    val file = Path(args.firstOrNull() ?: "friends.csv")
    BirthdayService(FlatFileFriendRepository(file), ConsoleMessageSender()).sendGreetings(LocalDate.now())
}
