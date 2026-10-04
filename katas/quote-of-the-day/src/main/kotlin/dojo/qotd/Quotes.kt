// Copyright (c) 2026 Riadh MNASRI. Licensed under the MIT License.
package dojo.qotd

import kotlin.random.Random

/** Le cœur du service, sans HTTP : tirer une citation, différente de la précédente. */
class Quotes(private val collection: List<String>, private val random: Random = Random.Default) {
    private var last: String? = null

    /** Une citation au hasard, parmi celles qui contiennent [containing] s'il est donné ; `null` si aucune. */
    fun next(containing: String? = null): String? {
        val matching = collection.filter { containing == null || it.contains(containing, ignoreCase = true) }
        if (matching.isEmpty()) return null
        val candidates = matching.filter { it != last }.ifEmpty { matching }
        return candidates.random(random).also { last = it }
    }
}
