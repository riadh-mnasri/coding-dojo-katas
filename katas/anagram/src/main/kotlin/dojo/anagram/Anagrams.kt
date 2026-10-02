// Copyright (c) 2026 Riadh MNASRI. Licensed under the MIT License.
package dojo.anagram

class Anagrams(words: Collection<String>) {
    private val dictionary = words.map(String::lowercase).toSet()

    fun twoWordAnagramsOf(word: String): Set<Pair<String, String>> {
        val target = word.lowercase().toList().sorted()
        return dictionary.flatMap { first ->
            dictionary.filter { second -> first <= second && (first + second).toList().sorted() == target }
                .map { second -> first to second }
        }.toSet()
    }
}
