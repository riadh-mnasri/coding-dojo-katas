// Copyright (c) 2026 Riadh MNASRI. Licensed under the MIT License.
package dojo.pizza

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.withContext

/** Partie en terminal (non testée, sans logique) : les alertes du four s'affichent pendant qu'on tape. */
fun main() = runBlocking {
    coroutineScope {
        val oven = Oven(this) { println("🔔 $it") }
        val commands = Commands(oven)
        println("Commands: cook a <recipe> pizza, show queue, get out the pizza, quit")
        while (true) {
            val line = withContext(Dispatchers.IO) { readlnOrNull() } ?: break
            if (line == "quit") break
            println(runCatching { commands.execute(line) }.getOrElse { it.message })
        }
        oven.queue().forEach { _ -> runCatching { oven.takeOut() } }
    }
}
