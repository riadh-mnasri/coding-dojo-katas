// Copyright (c) 2026 Riadh MNASRI. Licensed under the MIT License.
package dojo.markov

/** Première partie : pour chaque mot, la fréquence de chacun des mots qui le suivent. */
class MarkovChain private constructor(private val transitions: Map<String, Map<String, Double>>) {

    fun followersOf(word: String): Map<String, Double> = transitions[word].orEmpty()

    /** Deuxième partie : un texte de [words] mots, en partant de [startingWith]. */
    fun generate(words: Int, startingWith: String): String {
        val text = mutableListOf(startingWith)
        while (text.size < words) text += followersOf(text.last()).keys.first()
        return text.joinToString(" ")
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
