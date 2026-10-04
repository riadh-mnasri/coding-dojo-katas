// Copyright (c) 2026 Riadh MNASRI. Licensed under the MIT License.
package dojo.cupcake

interface Cake {
    fun name(): String
}

class Cupcake : Cake {
    override fun name() = "🧁"
}

class Cookie : Cake {
    override fun name() = "🍪"
}

class Chocolate(private val cake: Cake) : Cake {
    override fun name() = "${cake.name()} with 🍫"
}
