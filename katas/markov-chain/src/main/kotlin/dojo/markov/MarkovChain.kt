// Copyright (c) 2026 Riadh MNASRI. Licensed under the MIT License.
package dojo.markov

import kotlin.random.Random

/** Première partie : pour chaque mot, la fréquence de chacun des mots qui le suivent. */
class MarkovChain private constructor(private val transitions: Map<String, Map<String, Double>>) {

    fun followersOf(word: String): Map<String, Double> = transitions[word].orEmpty()

    /** Deuxième partie : un texte de [words] mots, en partant de [startingWith]. */
    fun generate(words: Int, startingWith: String, random: Random = Random.Default): String {
        val text = mutableListOf(startingWith)
        while (text.size < words) text += draw(followersOf(text.last()), random)
        return text.joinToString(" ")
    }

    /** Tire un mot : chacun occupe sur [0 ; 1[ une part égale à sa fréquence. */
    private fun draw(followers: Map<String, Double>, random: Random): String {
        val target = random.nextDouble()
        var cumulated = 0.0
        return followers.entries.first { (_, frequency) ->
            cumulated += frequency
            target < cumulated
        }.key
    }

    companion object {
        fun learn(text: String): MarkovChain {
            val words = text.split(Regex("\\s+")).filter { it.isNotBlank() }
            val transitions = words.zipWithNext()
                .groupBy({ it.first }, { it.second })
                .mapValues { (_, followers) -> followers.groupingBy { it }.eachCount().mapValues { (_, n) -> n.toDouble() / followers.size } }
            return MarkovChain(transitions)
        }
    }
}
