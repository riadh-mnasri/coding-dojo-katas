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
abstract class Topping(private val cake: Cake, private val emoji: String, private val cost: BigDecimal) : Cake {
    override fun name() = "${cake.name()} ${if (cake is Topping) "and" else "with"} $emoji"
    override fun price() = cake.price() + cost
}

class Chocolate(cake: Cake) : Topping(cake, "🍫", BigDecimal("0.1"))

class Nuts(cake: Cake) : Topping(cake, "🥜", BigDecimal("0.2"))

/** Composite : un lot de gâteaux, lui-même vendu comme un gâteau (on peut donc faire des lots de lots). */
class Bundle(private vararg val cakes: Cake) : Cake {
    override fun name(): String = TODO("not described yet")
    override fun price(): BigDecimal = cakes.fold(BigDecimal.ZERO) { total, cake -> total + cake.price() } * BigDecimal("0.9")
}
