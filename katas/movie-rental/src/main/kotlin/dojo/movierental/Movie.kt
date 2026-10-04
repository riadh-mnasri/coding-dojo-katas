package dojo.movierental

class Movie(val title: String, var priceCode: Int) {
    companion object {
        const val CHILDRENS = 2
        const val NEW_RELEASE = 1
        const val REGULAR = 0
    }
}

class Rental(val movie: Movie, val daysRented: Int) {
    fun charge(): Double {
        var amount = 0.0
        when (movie.priceCode) {
            Movie.REGULAR -> {
                amount += 2.0
                if (daysRented > 2) amount += (daysRented - 2) * 1.5
            }
            Movie.NEW_RELEASE -> amount += (daysRented * 3).toDouble()
            Movie.CHILDRENS -> {
                amount += 1.5
                if (daysRented > 3) amount += (daysRented - 3) * 1.5
            }
        }
        return amount
    }

    /** Un point par location, un de plus pour une nouveauté louée au moins deux jours. */
    fun frequentRenterPoints(): Int = if (movie.priceCode == Movie.NEW_RELEASE && daysRented > 1) 2 else 1
}
