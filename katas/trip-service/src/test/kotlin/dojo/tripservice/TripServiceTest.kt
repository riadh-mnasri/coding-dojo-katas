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
    private val tripDAO = object : TripDAO() {
        override fun tripsBy(user: User): List<Trip> = user.trips()
    }

    private inner class TestableTripService : TripService(tripDAO) {
        override fun loggedUser(): User? = loggedInUser
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
        val stranger = aUser().friendsWith(anotherUser).withTrips(toBrazil).build()

        assertThat(TestableTripService().getTripsByUser(stranger)).isEmpty()
    }

    @Test
    fun `the trips of a friend are shown`() {
        loggedInUser = registeredUser
        val friend = aUser().friendsWith(anotherUser, registeredUser).withTrips(toBrazil).build()

        assertThat(TestableTripService().getTripsByUser(friend)).containsExactly(toBrazil)
    }

    private fun aUser() = UserBuilder()

    private class UserBuilder {
        private var friends = emptyList<User>()
        private var trips = emptyList<Trip>()

        fun friendsWith(vararg users: User) = apply { friends = users.toList() }

        fun withTrips(vararg trips: Trip) = apply { this.trips = trips.toList() }

        fun build() = User().apply {
            friends.forEach(::addFriend)
            trips.forEach(::addTrip)
        }
    }
}
