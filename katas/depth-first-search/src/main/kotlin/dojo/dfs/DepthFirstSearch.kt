// Copyright (c) 2026 Riadh MNASRI. Licensed under the MIT License.
package dojo.dfs

/** L'interlocuteur à qui la recherche pose ses questions : un humain au terminal, ou une conversation simulée. */
interface Guide {
    fun isGoal(place: String): Boolean
    fun exitsOf(place: String): List<String>
}

class DepthFirstSearch(private val guide: Guide) {
    fun pathFrom(start: String): List<String>? = if (guide.isGoal(start)) listOf(start) else null
}
