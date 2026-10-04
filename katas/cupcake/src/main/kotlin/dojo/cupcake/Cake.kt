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

/** Décorateur : une garniture enveloppe un gâteau et complète son nom. */
abstract class Topping(private val cake: Cake, private val emoji: String) : Cake {
    override fun name() = "${cake.name()} ${if (cake is Topping) "and" else "with"} $emoji"
}

class Chocolate(cake: Cake) : Topping(cake, "🍫")

class Nuts(cake: Cake) : Topping(cake, "🥜")
