// Copyright (c) 2026 Riadh MNASRI. Licensed under the MIT License.
package dojo.anagram

class Anagrams(private val dictionary: Collection<String>) {

    fun twoWordAnagramsOf(word: String): Set<Pair<String, String>> {
        val target = word.toList().sorted()
        return dictionary.flatMap { first ->
            dictionary.filter { second -> first <= second && (first + second).toList().sorted() == target }
                .map { second -> first to second }
        }.toSet()
    }
}
