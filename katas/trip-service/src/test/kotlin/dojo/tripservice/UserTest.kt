// Copyright (c) 2026 Riadh MNASRI. Licensed under the MIT License.
package dojo.tripservice

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

class UserTest {

    private val bob = User()
    private val paul = User()

    @Test
    fun `knows whether it is friends with another user`() {
        val user = User().apply { addFriend(bob) }

        assertThat(user.isFriendsWith(bob)).isTrue()
        assertThat(user.isFriendsWith(paul)).isFalse()
    }
}
