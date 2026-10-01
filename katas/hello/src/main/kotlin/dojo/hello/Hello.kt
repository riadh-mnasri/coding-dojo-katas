// Copyright (c) 2026 Riadh MNASRI. Licensed under the MIT License.
package dojo.hello

/** Le port de sortie : l'affichage est un détail, on ne le connaît que par cette interface. */
fun interface Display {
    fun show(message: String)
}

class Greeter(private val display: Display) {
    fun greet() = display.show("Hello, World!")
}

fun main() = Greeter(::println).greet()
