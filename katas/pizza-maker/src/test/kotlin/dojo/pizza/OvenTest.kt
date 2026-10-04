// Copyright (c) 2026 Riadh MNASRI. Licensed under the MIT License.
package dojo.pizza

import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.TestScope
import kotlinx.coroutines.test.advanceTimeBy
import kotlinx.coroutines.test.runCurrent
import kotlinx.coroutines.test.runTest
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

/** Le temps est virtuel : `advanceTimeBy(45_000)` fait passer 45 secondes instantanément. */
@OptIn(ExperimentalCoroutinesApi::class)
class OvenTest {

    private val alerts = mutableListOf<String>()

    private fun TestScope.oven() = Oven(backgroundScope, alerts::add)

    @Test
    fun `a pizza is announced as cooked after 45 seconds`() = runTest {
        val oven = oven()

        oven.cook("Margherita")
        advanceTimeBy(44_999)
        runCurrent()
        assertThat(alerts).isEmpty()

        advanceTimeBy(1)
        runCurrent()
        assertThat(alerts).containsExactly("Margherita is cooked!")
    }

    @Test
    fun `taking a cooked pizza out earns a point`() = runTest {
        val oven = oven()
        oven.cook("Margherita")
        advanceTimeBy(50_000)

        val pizza = oven.takeOut()

        assertThat(pizza).isEqualTo(Pizza("Margherita", PizzaState.COOKED))
        assertThat(oven.points).isEqualTo(1)
    }

    @Test
    fun `a pizza left 15 seconds after cooking is burned and earns nothing`() = runTest {
        val oven = oven()
        oven.cook("Regina")
        advanceTimeBy(60_001)

        assertThat(alerts).containsExactly("Regina is cooked!", "Regina is burned!")
        assertThat(oven.takeOut().state).isEqualTo(PizzaState.BURNED)
        assertThat(oven.points).isZero()
    }

    @Test
    fun `a pizza taken out in time is never announced as burned`() = runTest {
        val oven = oven()
        oven.cook("Regina")
        advanceTimeBy(50_000)
        oven.takeOut()

        advanceTimeBy(30_000)

        assertThat(alerts).containsExactly("Regina is cooked!")
    }
}
