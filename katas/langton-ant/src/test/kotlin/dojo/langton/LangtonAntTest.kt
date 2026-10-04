// Copyright (c) 2026 Riadh MNASRI. Licensed under the MIT License.
package dojo.langton

import dojo.langton.Color.BLACK
import dojo.langton.Color.WHITE
import dojo.langton.Direction.EAST
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

class LangtonAntTest {

    @Test
    fun `on a white square the ant turns right, flips it to black and moves forward`() {
        // Given: une fourmi en (0, 0) tournée vers le nord, sur un plan entièrement blanc
        val world = World()

        // When
        world.step()

        // Then
        assertThat(world.ant).isEqualTo(Ant(Position(1, 0), EAST))
        assertThat(world.colorAt(Position(0, 0))).isEqualTo(BLACK)
        assertThat(world.colorAt(Position(1, 0))).isEqualTo(WHITE)
    }

    @Test
    fun `on a black square the ant turns left, flips it to white and moves forward`() {
        // Given: après un premier pas, la case d'origine est noire ; on y ramène la fourmi en quatre pas
        val world = World()
        repeat(4) { world.step() }
        assertThat(world.ant).isEqualTo(Ant(Position(0, 0), Direction.NORTH))
        assertThat(world.colorAt(Position(0, 0))).isEqualTo(BLACK)

        // When
        world.step()

        // Then
        assertThat(world.ant).isEqualTo(Ant(Position(-1, 0), Direction.WEST))
        assertThat(world.colorAt(Position(0, 0))).isEqualTo(WHITE)
    }
}
