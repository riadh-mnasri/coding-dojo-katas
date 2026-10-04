// Copyright (c) 2026 Riadh MNASRI. Licensed under the MIT License.
package dojo.social

import java.time.Instant

data class Post(val id: Int, val author: String, val text: String, val at: Instant) {
    /** Les noms précédés de @ ; « @Charlotte » ne mentionne pas Charlie. */
    val mentions: Set<String> get() = MENTION.findAll(text).map { it.groupValues[1] }.toSet()

    private companion object {
        val MENTION = Regex("""@(\w+)""")
    }
}

data class DirectMessage(val from: String, val to: String, val text: String, val at: Instant)

class SocialNetwork(private val clock: () -> Instant) {
    private val posts = mutableListOf<Post>()
    private val following = mutableMapOf<String, MutableSet<String>>()
    private val directMessages = mutableListOf<DirectMessage>()

    fun post(author: String, text: String): Post = Post(posts.size + 1, author, text, clock()).also { posts += it }

    fun timeline(author: String): List<Post> = posts.filter { it.author == author }.sortedByDescending { it.at }

    fun follow(follower: String, followee: String) {
        following.getOrPut(follower) { mutableSetOf() } += followee
    }

    /** Le mur : ses propres messages et ceux des personnes suivies, du plus récent au plus ancien. */
    fun wall(user: String): List<Post> {
        val authors = following[user].orEmpty() + user
        return posts.filter { it.author in authors }.sortedByDescending { it.at }
    }

    fun mentionsOf(user: String): List<Post> = posts.filter { user in it.mentions }.sortedByDescending { it.at }

    /** Un message privé ne figure dans aucune timeline : seul le destinataire le voit, dans sa boîte. */
    fun sendDirectMessage(from: String, to: String, text: String) {
        directMessages += DirectMessage(from, to, text, clock())
    }

    fun inbox(user: String): List<DirectMessage> = directMessages.filter { it.to == user }.sortedByDescending { it.at }

    fun linkTo(post: Post): String = "$BASE_URL/${post.author}/messages/${post.id}"

    fun open(link: String): Post {
        val id = link.substringAfterLast("/messages/").toIntOrNull()
        return posts.firstOrNull { it.id == id && link == linkTo(it) } ?: throw IllegalArgumentException("No message at $link")
    }

    private companion object {
        const val BASE_URL = "https://social.example"
    }
}
