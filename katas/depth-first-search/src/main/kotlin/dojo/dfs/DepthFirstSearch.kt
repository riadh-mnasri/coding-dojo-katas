// Copyright (c) 2026 Riadh MNASRI. Licensed under the MIT License.
package dojo.dfs

/** L'interlocuteur à qui la recherche pose ses questions : un humain au terminal, ou une conversation simulée. */
interface Guide {
    fun isGoal(place: String): Boolean
    fun exitsOf(place: String): List<String>
}

class DepthFirstSearch(private val guide: Guide) {
    /** La pile d'appels porte le chemin : on n'a besoin ni de graphe ni de pile explicite. */
    fun pathFrom(start: String): List<String>? {
        if (guide.isGoal(start)) return listOf(start)
        return guide.exitsOf(start).firstNotNullOfOrNull { exit -> pathFrom(exit)?.let { listOf(start) + it } }
    }
}
