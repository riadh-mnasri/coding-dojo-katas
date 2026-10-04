// Copyright (c) 2026 Riadh MNASRI. Licensed under the MIT License.
package dojo.christmas

import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.launch
import kotlinx.coroutines.test.advanceTimeBy
import kotlinx.coroutines.test.runCurrent
import kotlinx.coroutines.test.runTest
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import kotlin.time.Duration.Companion.seconds

/** Le traîneau de test : il note ce qui est chargé, et quand (en temps virtuel). */
class RecordingSleigh : SantasSleigh {
    val packed = mutableListOf<Present>()
    override fun pack(present: Present) {
        packed += present
    }
}

@OptIn(ExperimentalCoroutinesApi::class)
class ElfTest {

    @Test
    fun `story 1 - an elf takes a while to put a present on the sleigh`() = runTest {
        val sleigh = RecordingSleigh()
        val elf = Elf("Pepper", packingTime = 10.seconds, sleigh)
        val teddy = Present("Teddy bear", family = "Smith")

        launch { elf.deliver(teddy) }
        advanceTimeBy(9_999)
        runCurrent()
        assertThat(sleigh.packed).isEmpty()

        advanceTimeBy(1)
        runCurrent()
        assertThat(sleigh.packed).containsExactly(teddy)
    }
}
