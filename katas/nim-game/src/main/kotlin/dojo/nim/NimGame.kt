// Copyright (c) 2026 Riadh MNASRI. Licensed under the MIT License.
package dojo.nim

class NimGame(private val first: String, private val second: String) {
    var sticks = 10
        private set
    var currentPlayer = first
        private set

    fun take(count: Int) {
        sticks -= count
        currentPlayer = if (currentPlayer == first) second else first
    }
}
