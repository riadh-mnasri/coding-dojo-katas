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
}
