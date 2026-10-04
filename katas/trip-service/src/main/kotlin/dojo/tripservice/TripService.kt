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
                tripList = tripsBy(user)
            }
            return tripList
        } else {
            throw UserNotLoggedInException()
        }
    }

    protected open fun loggedUser(): User? = UserSession.getInstance().getLoggedUser()

    protected open fun tripsBy(user: User): List<Trip> = TripDAO.findTripsByUser(user)
}
