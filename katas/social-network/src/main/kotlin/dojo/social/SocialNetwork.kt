// Copyright (c) 2026 Riadh MNASRI. Licensed under the MIT License.
package dojo.social

import java.time.Instant

data class Post(val id: Int, val author: String, val text: String, val at: Instant)

class SocialNetwork(private val clock: () -> Instant) {
    private val posts = mutableListOf<Post>()
    private val following = mutableMapOf<String, MutableSet<String>>()

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
}
