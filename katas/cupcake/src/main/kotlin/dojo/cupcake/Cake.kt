// Copyright (c) 2026 Riadh MNASRI. Licensed under the MIT License.
package dojo.cupcake

import java.math.BigDecimal

interface Cake {
    fun name(): String
    fun price(): BigDecimal
}

class Cupcake : Cake {
    override fun name() = "🧁"
    override fun price() = BigDecimal("1")
}

class Cookie : Cake {
    override fun name() = "🍪"
    override fun price() = BigDecimal("2")
}

/** Décorateur : une garniture enveloppe un gâteau et complète son nom. */
abstract class Topping(private val cake: Cake, private val emoji: String) : Cake {
    override fun name() = "${cake.name()} ${if (cake is Topping) "and" else "with"} $emoji"
    override fun price() = cake.price()
}

class Chocolate(cake: Cake) : Topping(cake, "🍫")

class Nuts(cake: Cake) : Topping(cake, "🥜")
