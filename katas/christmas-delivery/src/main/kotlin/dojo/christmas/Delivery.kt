// Copyright (c) 2026 Riadh MNASRI. Licensed under the MIT License.
package dojo.christmas

import kotlinx.coroutines.delay
import kotlin.time.Duration

/** L'interface fournie par le Père Noël. */
interface SantasSleigh {
    fun pack(present: Present)
}

data class Present(val name: String, val family: String)

class Elf(val name: String, private val packingTime: Duration, private val sleigh: SantasSleigh) {
    suspend fun deliver(present: Present) {
        delay(packingTime)
        sleigh.pack(present)
    }
}
