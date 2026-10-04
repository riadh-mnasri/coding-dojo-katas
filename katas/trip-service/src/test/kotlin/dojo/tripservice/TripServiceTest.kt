// Copyright (c) 2026 Riadh MNASRI. Licensed under the MIT License.
package dojo.tripservice

import org.assertj.core.api.Assertions.assertThatThrownBy
import org.junit.jupiter.api.Test

class TripServiceTest {

    private val guest: User? = null
    private val anotherUser = User()

    @Test
    fun `a guest cannot see anybody's trips`() {
        val service = TripService()

        assertThatThrownBy { service.getTripsByUser(anotherUser) }.isInstanceOf(UserNotLoggedInException::class.java)
    }
}
