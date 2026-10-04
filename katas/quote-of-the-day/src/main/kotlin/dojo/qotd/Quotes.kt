// Copyright (c) 2026 Riadh MNASRI. Licensed under the MIT License.
package dojo.qotd

import kotlin.random.Random

class Quotes(private val collection: List<String>, private val random: Random = Random.Default) {
    fun next(): String = collection.random(random)
}
