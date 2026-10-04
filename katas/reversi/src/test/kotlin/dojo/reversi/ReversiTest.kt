// Copyright (c) 2026 Riadh MNASRI. Licensed under the MIT License.
package dojo.reversi

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

class ReversiTest {

    /** Un plateau 8 × 8 dont seules les lignes données sont remplies, suivi du joueur dont c'est le tour. */
    private fun position(rows: Map<Int, String>, turn: Char): String =
        (1..8).joinToString("\n") { rows[it] ?: "........" } + "\n$turn"

    @Test
    fun `no opponent piece means no legal move`() {
        assertThat(Reversi.legalMoves(position(mapOf(4 to "...B...."), 'B'))).isEmpty()
    }

    @Test
    fun `a move is legal when it flips an opponent piece on the same row`() {
        assertThat(Reversi.legalMoves(position(mapOf(4 to "...BW..."), 'B'))).containsExactly("F4")
    }

    @Test
    fun `moves can capture along columns and diagonals`() {
        val vertical = position(mapOf(3 to "...B....", 4 to "...W...."), 'B')
        val diagonal = position(mapOf(3 to "..B.....", 4 to "...W...."), 'B')

        assertThat(Reversi.legalMoves(vertical)).containsExactly("D5")
        assertThat(Reversi.legalMoves(diagonal)).containsExactly("E5")
    }
}
