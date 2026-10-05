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
    private val startedFamilies = mutableSetOf<String>()
    private val naughtyFamilies = mutableSetOf<String>()

    fun receive(present: Present) {
        if (present.family in naughtyFamilies) return
        waiting.addLast(present)
        dispatch()
    }

    /** Les cadeaux en attente de cette famille sont jetés, ainsi que ceux qui arriveront ensuite. */
    fun cancel(family: String) {
        naughtyFamilies += family
        waiting.removeAll { it.family == family }
    }

    /** Un cadeau d'une famille déjà commencée passe d'abord ; sinon, le plus ancien. */
    private fun nextPresent(): Present {
        val sameFamily = waiting.firstOrNull { it.family in startedFamilies }
        return if (sameFamily != null) sameFamily.also { waiting.remove(it) } else waiting.removeFirst()
    }

    private fun dispatch() {
        while (freeElves.isNotEmpty() && waiting.isNotEmpty()) {
            val elf = freeElves.removeFirst()
            val present = nextPresent()
            startedFamilies += present.family
            scope.launch {
                elf.deliver(present)
                freeElves.addLast(elf)
                dispatch()
            }
        }
    }
}
