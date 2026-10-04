// Copyright (c) 2026 Riadh MNASRI. Licensed under the MIT License.
package dojo.holdem

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

class TexasHoldEmTest {

    @Test
    fun `a folded hand is repeated without rank`() {
        assertThat(TexasHoldEm.announce("9h 5s")).isEqualTo("9h 5s")
    }

    @Test
    fun `a full hand is ranked with its best five cards`() {
        assertThat(TexasHoldEm.rank("Kc 9s Ks Kd 9d 3c 6d")).isEqualTo("Full House")
        assertThat(TexasHoldEm.rank("9c Ah Ks Kd 9d 3c 6d")).isEqualTo("Two Pair")
        assertThat(TexasHoldEm.rank("4d 2d Ks Kd 9d 3c 6d")).isEqualTo("Flush")
    }

    @Test
    fun `straights are recognised, the ace also playing low`() {
        assertThat(TexasHoldEm.rank("5c 6d 7h 8s 9c Kd 2h")).isEqualTo("Straight")
        assertThat(TexasHoldEm.rank("Ac 2d 3h 4s 5c Kd Kh")).isEqualTo("Straight")
        assertThat(TexasHoldEm.rank("Tc Jc Qc Kc Ac 2d 3h")).isEqualTo("Straight Flush")
    }

    @Test
    fun `announces the round of the kata`() {
        val round = """
            Kc 9s Ks Kd 9d 3c 6d
            9c Ah Ks Kd 9d 3c 6d
            Ac Qc Ks Kd 9d 3c
            9h 5s
            4d 2d Ks Kd 9d 3c 6d
            7s Ts Ks Kd 9d
        """.trimIndent()

        assertThat(TexasHoldEm.announce(round)).isEqualTo(
            """
            Kc 9s Ks Kd 9d 3c 6d Full House (winner)
            9c Ah Ks Kd 9d 3c 6d Two Pair
            Ac Qc Ks Kd 9d 3c
            9h 5s
            4d 2d Ks Kd 9d 3c 6d Flush
            7s Ts Ks Kd 9d
            """.trimIndent(),
        )
    }

    @Test
    fun `players with equal best hands all win`() {
        val round = "2c 3d As Ks Qs Js Ts\n4h 5h As Ks Qs Js Ts\n9h 9c As Ks Qs"

        assertThat(TexasHoldEm.announce(round)).isEqualTo(
            "2c 3d As Ks Qs Js Ts Straight Flush (winner)\n4h 5h As Ks Qs Js Ts Straight Flush (winner)\n9h 9c As Ks Qs",
        )
    }

    @Test
    fun `the kicker decides between equal pairs`() {
        val round = "Ah 2c Kd Kc 9s 7d 4h\nQh 3c Kd Kc 9s 7d 4h"

        assertThat(TexasHoldEm.announce(round)).isEqualTo("Ah 2c Kd Kc 9s 7d 4h Pair (winner)\nQh 3c Kd Kc 9s 7d 4h Pair")
    }
}
