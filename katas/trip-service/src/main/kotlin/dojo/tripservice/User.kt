package dojo.tripservice

class User {
    private val trips = ArrayList<Trip>()
    private val friends = ArrayList<User>()

    fun getFriends(): List<User> = friends

    fun isFriendsWith(anotherUser: User): Boolean = anotherUser in friends

    fun addFriend(user: User) {
        friends.add(user)
    }

    fun addTrip(trip: Trip) {
        trips.add(trip)
    }

    fun trips(): List<Trip> = trips
}

class Trip

class UserNotLoggedInException : RuntimeException()

class CollaboratorCallException(message: String) : RuntimeException(message)
