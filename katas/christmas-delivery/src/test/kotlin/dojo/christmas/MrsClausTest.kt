// Copyright (c) 2026 Riadh MNASRI. Licensed under the MIT License.
package dojo.christmas

import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.TestScope
import kotlinx.coroutines.test.advanceTimeBy
import kotlinx.coroutines.test.runCurrent
import kotlinx.coroutines.test.runTest
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import kotlin.time.Duration.Companion.seconds

@OptIn(ExperimentalCoroutinesApi::class)
class MrsClausTest {

    private val sleigh = RecordingSleigh()

    private fun TestScope.mrsClaus(elves: Int) =
        MrsClaus(List(elves) { Elf("elf-$it", packingTime = 10.seconds, sleigh) }, backgroundScope)

    private fun TestScope.after(seconds: Int) {
        advanceTimeBy(seconds * 1_000L)
        runCurrent()
    }

    private fun present(name: String, family: String = name) = Present(name, family)

    @Test
    fun `story 2 - presents are handed to the free elves, who work in parallel`() = runTest {
        val claus = mrsClaus(elves = 2)

        claus.receive(present("Train"))
        claus.receive(present("Doll"))
        after(10)

        assertThat(sleigh.packed).containsExactly(present("Train"), present("Doll"))
    }

    @Test
    fun `story 2 - presents wait when every elf is busy`() = runTest {
        val claus = mrsClaus(elves = 1)

        claus.receive(present("Train"))
        claus.receive(present("Doll"))
        after(10)
        assertThat(sleigh.packed).containsExactly(present("Train"))

        after(10)
        assertThat(sleigh.packed).containsExactly(present("Train"), present("Doll"))
    }

    @Test
    fun `story 3 - a family already started goes first`() = runTest {
        val claus = mrsClaus(elves = 1)

        claus.receive(present("Smith train", family = "Smith"))
        claus.receive(present("Jones doll", family = "Jones"))
        claus.receive(present("Smith kite", family = "Smith"))
        after(30)

        assertThat(sleigh.packed.map { it.name }).containsExactly("Smith train", "Smith kite", "Jones doll")
    }

    @Test
    fun `story 3 - elves never wait for a family, they take another one`() = runTest {
        val claus = mrsClaus(elves = 2)

        claus.receive(present("Smith train", family = "Smith"))
        claus.receive(present("Jones doll", family = "Jones"))
        after(10)

        // Les deux lutins ont travaillé en même temps, même si les familles sont mélangées : ils coûtent trop cher pour attendre.
        assertThat(sleigh.packed).hasSize(2)
    }
}
