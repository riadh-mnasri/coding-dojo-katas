// Copyright (c) 2026 Riadh MNASRI. Licensed under the MIT License.
package dojo.tripservice

open class TripService {

    fun getTripsByUser(user: User): List<Trip> {
        val loggedUser = loggedUser() ?: throw UserNotLoggedInException()
        return if (user.isFriendsWith(loggedUser)) tripsBy(user) else emptyList()
    }

    protected open fun loggedUser(): User? = UserSession.getInstance().getLoggedUser()

    protected open fun tripsBy(user: User): List<Trip> = TripDAO.findTripsByUser(user)
}
