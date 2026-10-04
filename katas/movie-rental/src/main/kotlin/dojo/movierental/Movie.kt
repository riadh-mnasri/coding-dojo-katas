// Copyright (c) 2026 Riadh MNASRI. Licensed under the MIT License.
package dojo.movierental

/** Le tarif d'un film : chaque catégorie sait calculer son prix et ses points de fidélité. */
sealed interface Price {
    fun charge(daysRented: Int): Double
    fun frequentRenterPoints(daysRented: Int): Int = 1

    data object Regular : Price {
        override fun charge(daysRented: Int) = 2.0 + if (daysRented > 2) (daysRented - 2) * 1.5 else 0.0
    }

    data object NewRelease : Price {
        override fun charge(daysRented: Int) = daysRented * 3.0
        override fun frequentRenterPoints(daysRented: Int) = if (daysRented > 1) 2 else 1
    }

    data object Childrens : Price {
        override fun charge(daysRented: Int) = 1.5 + if (daysRented > 3) (daysRented - 3) * 1.5 else 0.0
    }
}

/** Les codes de tarif d'origine sont conservés pour les appelants existants. */
class Movie(val title: String, priceCode: Int) {
    var price: Price = priceFor(priceCode)

    var priceCode: Int = priceCode
        set(value) {
            field = value
            price = priceFor(value)
        }

    companion object {
        const val CHILDRENS = 2
        const val NEW_RELEASE = 1
        const val REGULAR = 0

        private fun priceFor(code: Int): Price = when (code) {
            REGULAR -> Price.Regular
            NEW_RELEASE -> Price.NewRelease
            CHILDRENS -> Price.Childrens
            else -> throw IllegalArgumentException("Unknown price code $code")
        }
    }
}

class Rental(val movie: Movie, val daysRented: Int) {
    fun charge(): Double = movie.price.charge(daysRented)

    fun frequentRenterPoints(): Int = movie.price.frequentRenterPoints(daysRented)
}
