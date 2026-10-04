// Copyright (c) 2026 Riadh MNASRI. Licensed under the MIT License.
package dojo.tripservice

import org.assertj.core.api.Assertions.assertThatThrownBy
import org.junit.jupiter.api.Test

class TripServiceTest {

    private val guest: User? = null
    private val anotherUser = User()
    private var loggedInUser: User? = null

    /** La couture : en test, l'utilisateur connecté vient du test et non du singleton de session. */
    private inner class TestableTripService : TripService() {
        override fun loggedUser(): User? = loggedInUser
    }

    @Test
    fun `a guest cannot see anybody's trips`() {
        loggedInUser = guest
        val service = TestableTripService()

        assertThatThrownBy { service.getTripsByUser(anotherUser) }.isInstanceOf(UserNotLoggedInException::class.java)
    }
}
