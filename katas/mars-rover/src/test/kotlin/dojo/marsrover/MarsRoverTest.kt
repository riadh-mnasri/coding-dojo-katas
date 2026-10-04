// Copyright (c) 2026 Riadh MNASRI. Licensed under the MIT License.
package dojo.marsrover

import dojo.marsrover.Direction.EAST
import org.assertj.core.api.Assertions.assertThat
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
}
