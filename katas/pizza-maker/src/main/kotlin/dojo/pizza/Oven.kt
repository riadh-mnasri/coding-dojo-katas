// Copyright (c) 2026 Riadh MNASRI. Licensed under the MIT License.
package dojo.pizza

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlin.time.Duration.Companion.seconds

enum class PizzaState { COOKING, COOKED, BURNED }

data class Pizza(val recipe: String, val state: PizzaState)

class Oven(private val scope: CoroutineScope, private val alert: (String) -> Unit) {
    private class Slot(val recipe: String, var state: PizzaState = PizzaState.COOKING) {
        var timer: Job? = null
    }

    private val slots = mutableListOf<Slot>()

    var points = 0
        private set

    fun cook(recipe: String) {
        val slot = Slot(recipe).also { slots += it }
        slot.timer = scope.launch {
            delay(45.seconds)
            slot.state = PizzaState.COOKED
            alert("$recipe is cooked!")
            delay(15.seconds)
            slot.state = PizzaState.BURNED
            alert("$recipe is burned!")
        }
    }

    fun takeOut(): Pizza {
        val slot = slots.removeFirst()
        slot.timer?.cancel()
        if (slot.state == PizzaState.COOKED) points++
        return Pizza(slot.recipe, slot.state)
    }
}
