// Copyright (c) 2026 Riadh MNASRI. Licensed under the MIT License.
package dojo.qotd

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import kotlin.random.Random

class QuotesTest {

    private val collection = listOf(
        "Simplicity is prerequisite for reliability.",
        "Make it work, make it right, make it fast.",
        "Programs must be written for people to read.",
    )

    /** Un tirage piloté : toujours l'indice 0 parmi les citations possibles. */
    private val firstOne = object : Random() {
        override fun nextBits(bitCount: Int) = 0
    }

    @Test
    fun `gives a quote from the collection`() {
        assertThat(Quotes(collection, firstOne).next()).isIn(collection)
    }

    @Test
    fun `every visit gives a different quote than the previous one`() {
        val quotes = Quotes(collection, firstOne)

        val visits = List(4) { quotes.next() }

        visits.zipWithNext().forEach { (previous, current) -> assertThat(current).isNotEqualTo(previous) }
    }
}
