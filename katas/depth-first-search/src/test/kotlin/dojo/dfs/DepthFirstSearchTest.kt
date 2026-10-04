// Copyright (c) 2026 Riadh MNASRI. Licensed under the MIT License.
package dojo.dfs

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

class DepthFirstSearchTest {

    /** La conversation simulée : elle répond comme le ferait un humain et note chaque question posée. */
    private class ScriptedGuide(private val exits: Map<String, List<String>>, private val goal: String) : Guide {
        val questions = mutableListOf<String>()

        override fun isGoal(place: String): Boolean {
            questions += "Is $place the goal?"
            return place == goal
        }

        override fun exitsOf(place: String): List<String> {
            questions += "What are the exits of $place?"
            return exits[place].orEmpty()
        }
    }

    @Test
    fun `the one-node graph where we already are at the goal`() {
        val guide = ScriptedGuide(exits = emptyMap(), goal = "A")

        assertThat(DepthFirstSearch(guide).pathFrom("A")).containsExactly("A")
    }

    @Test
    fun `the one-node graph without the goal has no path`() {
        val guide = ScriptedGuide(exits = emptyMap(), goal = "Z")

        assertThat(DepthFirstSearch(guide).pathFrom("A")).isNull()
    }

    @Test
    fun `the two-node graph is crossed through its exit`() {
        val guide = ScriptedGuide(exits = mapOf("A" to listOf("B")), goal = "B")

        assertThat(DepthFirstSearch(guide).pathFrom("A")).containsExactly("A", "B")
    }

    @Test
    fun `a 2x2 maze needs to backtrack out of a dead end`() {
        // A B
        // C D    A ouvre sur B (impasse) et sur C, qui mène à D.
        val guide = ScriptedGuide(exits = mapOf("A" to listOf("B", "C"), "C" to listOf("D")), goal = "D")

        assertThat(DepthFirstSearch(guide).pathFrom("A")).containsExactly("A", "C", "D")
    }

    @Test
    fun `a 3x3 maze with corridors both ways does not loop forever`() {
        // A B C
        // D E F
        // G H I    Les couloirs se parcourent dans les deux sens, donc le labyrinthe a des boucles.
        val corridors = listOf("A" to "B", "B" to "C", "A" to "D", "B" to "E", "D" to "E", "E" to "H", "G" to "H", "H" to "I")
        val exits = (corridors + corridors.map { (a, b) -> b to a }).groupBy({ it.first }, { it.second })
        val guide = ScriptedGuide(exits, goal = "I")

        val path = DepthFirstSearch(guide).pathFrom("A")

        assertThat(path).isNotNull().startsWith("A").endsWith("I").doesNotHaveDuplicates()
        path!!.zipWithNext().forEach { (from, to) -> assertThat(exits.getValue(from)).contains(to) }
    }

    @Test
    fun `on a full two-level binary tree, questions go deep before going wide`() {
        //       R
        //    L     M
        //   a b   c d      le but est c
        val guide = ScriptedGuide(
            exits = mapOf("R" to listOf("L", "M"), "L" to listOf("a", "b"), "M" to listOf("c", "d")),
            goal = "c",
        )

        val path = DepthFirstSearch(guide).pathFrom("R")

        assertThat(path).containsExactly("R", "M", "c")
        assertThat(guide.questions).containsExactly(
            "Is R the goal?", "What are the exits of R?",
            "Is L the goal?", "What are the exits of L?",
            "Is a the goal?", "What are the exits of a?",
            "Is b the goal?", "What are the exits of b?",
            "Is M the goal?", "What are the exits of M?",
            "Is c the goal?",
        )
    }

    @Test
    fun `the event-driven search asks at most one question per step and finds the same path`() {
        val exits = mapOf("R" to listOf("L", "M"), "L" to listOf("a", "b"), "M" to listOf("c", "d"))
        val guide = ScriptedGuide(exits, goal = "c")
        val search = StepByStepSearch(guide, start = "R")

        while (search.result == null) {
            val asked = guide.questions.size
            search.step()
            assertThat(guide.questions.size - asked).isLessThanOrEqualTo(1)
        }

        assertThat(search.result).isEqualTo(Found(listOf("R", "M", "c")))
    }
}
