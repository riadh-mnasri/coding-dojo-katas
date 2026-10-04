// Copyright (c) 2026 Riadh MNASRI. Licensed under the MIT License.
package dojo.movierental

import org.assertj.core.api.Assertions.assertThat
import org.assertj.core.api.Assertions.assertThatThrownBy
import org.junit.jupiter.api.Test

/** Tests de caractérisation : les montants attendus sont calculés à la main à partir des règles de tarification. */
class CustomerTest {

    private fun customer(name: String, vararg rentals: Rental) = Customer(name).apply { rentals.forEach(::addRental) }

    @Test
    fun `the statement of the kata example`() {
        val martin = customer(
            "martin",
            Rental(Movie("Ran", Movie.REGULAR), 3),
            Rental(Movie("Trois Couleurs: Bleu", Movie.REGULAR), 2),
        )

        assertThat(martin.statement()).isEqualTo(
            "Rental Record for martin\n\tRan\t3.5\n\tTrois Couleurs: Bleu\t2.0\nAmount owed is 5.5\nYou earned 2 frequent renter points",
        )
    }

    @Test
    fun `new releases cost 3 per day and give a bonus point from the second day`() {
        val statement = customer("ann", Rental(Movie("Dune", Movie.NEW_RELEASE), 3)).statement()

        assertThat(statement).isEqualTo("Rental Record for ann\n\tDune\t9.0\nAmount owed is 9.0\nYou earned 2 frequent renter points")
    }

    @Test
    fun `children's movies cost 1,5 for three days, then 1,5 per extra day`() {
        val statement = customer(
            "tom",
            Rental(Movie("Totoro", Movie.CHILDRENS), 2),
            Rental(Movie("Kiki", Movie.CHILDRENS), 4),
        ).statement()

        assertThat(statement).isEqualTo(
            "Rental Record for tom\n\tTotoro\t1.5\n\tKiki\t3.0\nAmount owed is 4.5\nYou earned 2 frequent renter points",
        )
    }

    @Test
    fun `a customer without rentals owes nothing`() {
        assertThat(customer("zoe").statement())
            .isEqualTo("Rental Record for zoe\nAmount owed is 0.0\nYou earned 0 frequent renter points")
    }

    @Test
    fun `an unknown price code is rejected instead of costing nothing`() {
        assertThatThrownBy { Movie("Mystery", 42) }.isInstanceOf(IllegalArgumentException::class.java)
    }

    @Test
    fun `the HTML statement of the kata example`() {
        val martin = customer(
            "martin",
            Rental(Movie("Ran", Movie.REGULAR), 3),
            Rental(Movie("Trois Couleurs: Bleu", Movie.REGULAR), 2),
        )

        assertThat(martin.htmlStatement()).isEqualTo(
            """
            <h1>Rental Record for <em>martin</em></h1>
            <table>
              <tr><td>Ran</td><td>3.5</td></tr>
              <tr><td>Trois Couleurs: Bleu</td><td>2.0</td></tr>
            </table>
            <p>Amount owed is <em>5.5</em></p>
            <p>You earned <em>2</em> frequent renter points</p>
            """.trimIndent(),
        )
    }
}
