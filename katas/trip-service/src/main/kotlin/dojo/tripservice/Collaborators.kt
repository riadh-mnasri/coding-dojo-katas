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

open class TripDAO {
    /** Méthode d'instance ajoutée pour pouvoir injecter le DAO ; l'accès statique historique est conservé. */
    open fun tripsBy(user: User): List<Trip> = findTripsByUser(user)

    companion object {
        fun findTripsByUser(user: User): List<Trip> {
            throw CollaboratorCallException("TripDAO should not be invoked on an unit test.")
        }
    }
}
