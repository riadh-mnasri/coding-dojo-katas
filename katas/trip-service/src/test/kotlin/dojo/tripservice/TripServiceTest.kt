// Copyright (c) 2026 Riadh MNASRI. Licensed under the MIT License.
package dojo.tripservice

import org.assertj.core.api.Assertions.assertThat
import org.assertj.core.api.Assertions.assertThatThrownBy
import org.junit.jupiter.api.Test

class TripServiceTest {

    private val guest: User? = null
    private val anotherUser = User()
    private var loggedInUser: User? = null
    private val registeredUser = User()
    private val toBrazil = Trip()

    /** La couture : en test, l'utilisateur connecté vient du test et non du singleton de session. */
    private inner class TestableTripService : TripService() {
        override fun loggedUser(): User? = loggedInUser

        override fun tripsBy(user: User): List<Trip> = user.trips()
    }

    @Test
    fun `a guest cannot see anybody's trips`() {
        loggedInUser = guest
        val service = TestableTripService()

        assertThatThrownBy { service.getTripsByUser(anotherUser) }.isInstanceOf(UserNotLoggedInException::class.java)
    }

    @Test
    fun `no trips are shown when the users are not friends`() {
        loggedInUser = registeredUser
        val stranger = User().apply {
            addFriend(anotherUser)
            addTrip(toBrazil)
        }

        assertThat(TestableTripService().getTripsByUser(stranger)).isEmpty()
    }

    @Test
    fun `the trips of a friend are shown`() {
        loggedInUser = registeredUser
        val friend = User().apply {
            addFriend(anotherUser)
            addFriend(registeredUser)
            addTrip(toBrazil)
        }

        assertThat(TestableTripService().getTripsByUser(friend)).containsExactly(toBrazil)
    }
}
