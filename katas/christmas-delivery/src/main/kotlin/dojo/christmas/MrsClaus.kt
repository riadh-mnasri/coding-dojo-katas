// Copyright (c) 2026 Riadh MNASRI. Licensed under the MIT License.
package dojo.christmas

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

/**
 * Mère Noël reçoit les cadeaux des machines et les confie aux lutins libres ;
 * s'il n'y en a aucun, elle garde les cadeaux jusqu'à ce qu'un lutin revienne.
 */
class MrsClaus(elves: List<Elf>, private val scope: CoroutineScope) {
    private val freeElves = ArrayDeque(elves)
    private val waiting = ArrayDeque<Present>()

    fun receive(present: Present) {
        waiting.addLast(present)
        dispatch()
    }

    private fun dispatch() {
        while (freeElves.isNotEmpty() && waiting.isNotEmpty()) {
            val elf = freeElves.removeFirst()
            val present = waiting.removeFirst()
            scope.launch {
                elf.deliver(present)
                freeElves.addLast(elf)
                dispatch()
            }
        }
    }
}
