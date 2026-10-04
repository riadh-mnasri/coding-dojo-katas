// Copyright (c) 2026 Riadh MNASRI. Licensed under the MIT License.
package dojo.dfs

sealed interface SearchResult

data class Found(val path: List<String>) : SearchResult

data object NotFound : SearchResult

/**
 * Variante « événementielle » : chaque appel à [step] pose au plus une question. L'état qui vivait dans la
 * pile d'appels de [DepthFirstSearch] doit donc être gardé à la main, dans une pile de cadres.
 */
class StepByStepSearch(private val guide: Guide, start: String) {

    private class Frame(val place: String, val path: List<String>) {
        var goalChecked = false
        var exits: ArrayDeque<String>? = null
    }

    private val stack = ArrayDeque(listOf(Frame(start, listOf(start))))
    private val visited = mutableSetOf(start)

    var result: SearchResult? = null
        private set

    fun step() {
        if (result != null) return
        val frame = stack.lastOrNull() ?: return run { result = NotFound }
        when {
            !frame.goalChecked -> {
                frame.goalChecked = true
                if (guide.isGoal(frame.place)) result = Found(frame.path)
            }
            frame.exits == null -> frame.exits = ArrayDeque(guide.exitsOf(frame.place))
            else -> {
                val next = frame.exits!!.removeFirstOrNull()
                when {
                    next == null -> stack.removeLast()
                    next !in visited -> {
                        visited += next
                        stack.addLast(Frame(next, frame.path + next))
                    }
                }
            }
        }
    }
}
