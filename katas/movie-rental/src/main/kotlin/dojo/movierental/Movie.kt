package dojo.movierental

class Movie(val title: String, var priceCode: Int) {
    companion object {
        const val CHILDRENS = 2
        const val NEW_RELEASE = 1
        const val REGULAR = 0
    }
}

class Rental(val movie: Movie, val daysRented: Int)
