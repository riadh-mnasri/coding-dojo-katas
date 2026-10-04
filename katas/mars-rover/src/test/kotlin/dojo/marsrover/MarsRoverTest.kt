// Copyright (c) 2026 Riadh MNASRI. Licensed under the MIT License.
package dojo.marsrover

import dojo.marsrover.Direction.EAST
import org.assertj.core.api.Assertions.assertThat
import org.assertj.core.api.Assertions.assertThatThrownBy
import org.junit.jupiter.api.Test

class MarsRoverTest {

    @Test
    fun `the rover starts where its arrow is on the map`() {
        val mission = Mission.parse("🟩🟩\n➡️🟩")

        assertThat(mission.rover).isEqualTo(Rover(Position(0, 0), EAST))
    }

    @Test
    fun `moving forward goes one tile in the facing direction`() {
        val mission = Mission.parse("🟩🟩\n➡️🟩")

        assertThat(mission.execute("⬆️")).isEqualTo(Rover(Position(1, 0), EAST))
    }

    @Test
    fun `turning right and left changes the direction only`() {
        val mission = Mission.parse("➡️")

        assertThat(mission.execute("➡️")).isEqualTo(Rover(Position(0, 0), Direction.SOUTH))
        assertThat(mission.execute("⬅️")).isEqualTo(Rover(Position(0, 0), Direction.NORTH))
        assertThat(mission.execute("⬅️⬅️⬅️⬅️")).isEqualTo(Rover(Position(0, 0), EAST))
    }

    @Test
    fun `the rover does nothing in front of an obstacle`() {
        val mission = Mission.parse("🟩🟩\n➡️🌳")

        assertThat(mission.execute("⬆️")).isEqualTo(Rover(Position(0, 0), EAST))
        assertThat(mission.execute("⬅️⬆️➡️⬆️")).isEqualTo(Rover(Position(1, 1), EAST))
    }

    @Test
    fun `the rover does not leave the map`() {
        val mission = Mission.parse("🟩🟩\n⬅️🟩")

        assertThat(mission.execute("⬆️")).isEqualTo(Rover(Position(0, 0), Direction.WEST))
        assertThat(mission.execute("➡️⬆️⬆️⬆️")).isEqualTo(Rover(Position(0, 1), Direction.NORTH))
    }

    @Test
    fun `drives across the first map of the kata`() {
        val mission = Mission.parse(
            """
            🟩🟩🌳🟩🟩
            🟩🟩🟩🟩🟩
            🟩🟩🟩🌳🟩
            🟩🌳🟩🟩🟩
            ➡️🟩🟩🟩🟩
            """.trimIndent(),
        )

        // Trois cases vers l'est, puis vers le nord jusqu'à l'arbre en (3, 2), qui bloque les avancées suivantes.
        assertThat(mission.execute("⬆️⬆️⬆️⬅️⬆️⬆️⬆️⬆️")).isEqualTo(Rover(Position(3, 1), Direction.NORTH))
    }

    @Test
    fun `drives across the second map of the kata`() {
        val mission = Mission.parse(
            """
            🟫🟫🪨🟫🟫
            🟫🟫🟫🟫🟫
            🟫🟫🟫🟫🟫
            🟫🟫🟫🟫🟫
            ⬆️🟫🟫🟫🟫
            """.trimIndent(),
        )

        // Tout au nord (le bord arrête la 5e avancée), une case vers l'est, le rocher en (2, 4) bloque, puis demi-tour vers le sud.
        assertThat(mission.execute("⬆️⬆️⬆️⬆️⬆️➡️⬆️⬆️⬅️⬅️⬅️⬆️")).isEqualTo(Rover(Position(1, 3), Direction.SOUTH))
    }

    @Test
    fun `an unknown command is rejected`() {
        assertThatThrownBy { Mission.parse("➡️").execute("⬇️") }.isInstanceOf(IllegalArgumentException::class.java)
    }
}
