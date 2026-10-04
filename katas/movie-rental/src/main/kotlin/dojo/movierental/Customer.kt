// Copyright (c) 2026 Riadh MNASRI. Licensed under the MIT License.
package dojo.movierental

class Customer(val name: String) {
    private val rentals = ArrayList<Rental>()

    fun addRental(arg: Rental) {
        rentals.add(arg)
    }

    fun statement(): String {
        val header = "Rental Record for $name\n"
        val lines = rentals.joinToString("") { "\t${it.movie.title}\t${it.charge()}\n" }
        val footer = "Amount owed is ${totalCharge()}\nYou earned ${totalFrequentRenterPoints()} frequent renter points"
        return header + lines + footer
    }

    fun htmlStatement(): String {
        val header = "<h1>Rental Record for <em>$name</em></h1>\n<table>\n"
        val lines = rentals.joinToString("") { "  <tr><td>${it.movie.title}</td><td>${it.charge()}</td></tr>\n" }
        val footer = "</table>\n<p>Amount owed is <em>${totalCharge()}</em></p>\n" +
            "<p>You earned <em>${totalFrequentRenterPoints()}</em> frequent renter points</p>"
        return header + lines + footer
    }

    private fun totalCharge(): Double = rentals.sumOf { it.charge() }

    private fun totalFrequentRenterPoints(): Int = rentals.sumOf { it.frequentRenterPoints() }
}
