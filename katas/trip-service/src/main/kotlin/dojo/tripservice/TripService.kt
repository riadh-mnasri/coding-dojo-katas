// Copyright (c) 2026 Riadh MNASRI. Licensed under the MIT License.
package dojo.tripservice

/**
 * Les voyages d'un utilisateur ne sont visibles que de ses amis. L'appelant fournit l'utilisateur connecté
 * (lu dans la session, au bord de l'application) : le service ne dépend plus d'aucun singleton.
 */
class TripService(private val tripDAO: TripDAO = TripDAO()) {

    fun getFriendTrips(friend: User, loggedInUser: User?): List<Trip> {
        if (loggedInUser == null) throw UserNotLoggedInException()
        return if (friend.isFriendsWith(loggedInUser)) tripDAO.tripsBy(friend) else emptyList()
    }
}
