package dojo.tripservice

class UserSession private constructor() {

    fun getLoggedUser(): User? {
        throw CollaboratorCallException("UserSession.getLoggedUser() should not be called in an unit test")
    }

    companion object {
        private val session = UserSession()

        fun getInstance() = session
    }
}

object TripDAO {
    fun findTripsByUser(user: User): List<Trip> {
        throw CollaboratorCallException("TripDAO should not be invoked on an unit test.")
    }
}
