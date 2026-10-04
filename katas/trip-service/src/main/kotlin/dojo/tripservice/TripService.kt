// Copyright (c) 2026 Riadh MNASRI. Licensed under the MIT License.
package dojo.tripservice

open class TripService(private val tripDAO: TripDAO = TripDAO()) {

    fun getTripsByUser(user: User): List<Trip> {
        val loggedUser = loggedUser() ?: throw UserNotLoggedInException()
        return if (user.isFriendsWith(loggedUser)) tripDAO.tripsBy(user) else emptyList()
    }

    protected open fun loggedUser(): User? = UserSession.getInstance().getLoggedUser()
}
