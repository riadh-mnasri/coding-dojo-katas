// Copyright (c) 2026 Riadh MNASRI. Licensed under the MIT License.
package dojo.pizza

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlin.time.Duration.Companion.seconds

class Oven(private val scope: CoroutineScope, private val alert: (String) -> Unit) {

    fun cook(recipe: String) {
        scope.launch {
            delay(45.seconds)
            alert("$recipe is cooked!")
        }
    }
}
