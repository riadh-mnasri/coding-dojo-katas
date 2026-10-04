package dojo.tripservice

open class TripService {

    fun getTripsByUser(user: User): List<Trip> {
        var tripList: List<Trip> = ArrayList()
        val loggedUser: User? = loggedUser()
        var isFriend = false
        if (loggedUser != null) {
            for (friend in user.getFriends()) {
                if (friend == loggedUser) {
                    isFriend = true
                    break
                }
            }
            if (isFriend) {
                tripList = TripDAO.findTripsByUser(user)
            }
            return tripList
        } else {
            throw UserNotLoggedInException()
        }
    }

    protected open fun loggedUser(): User? = UserSession.getInstance().getLoggedUser()
}
