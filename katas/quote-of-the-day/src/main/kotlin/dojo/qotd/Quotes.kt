// Copyright (c) 2026 Riadh MNASRI. Licensed under the MIT License.
package dojo.qotd

import kotlin.random.Random

/** Le cœur du service, sans HTTP : tirer une citation, différente de la précédente. */
class Quotes(private val collection: List<String>, private val random: Random = Random.Default) {
    private var last: String? = null

    fun next(): String {
        val candidates = collection.filter { it != last }.ifEmpty { collection }
        return candidates.random(random).also { last = it }
    }
}
