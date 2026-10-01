// Copyright (c) 2026 Riadh MNASRI. Licensed under the MIT License.
package dojo.hello

fun interface Display {
    fun show(message: String)
}

class Greeter(private val display: Display) {
    fun greet() = display.show("Hello, World!")
}

fun main() = Greeter(::println).greet()
