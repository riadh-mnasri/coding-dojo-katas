// Copyright (c) 2026 Riadh MNASRI. Licensed under the MIT License.
package dojo.anagram

/**
 * Anagrammes en deux mots. Au lieu de comparer toutes les paires du dictionnaire (quadratique),
 * on ne garde que les mots écrits avec les lettres de la cible, indexés par leurs lettres triées :
 * pour chaque premier mot, le second se trouve en une recherche dans l'index.
 */
class Anagrams(words: Collection<String>) {
    private val dictionary = words.map(String::lowercase).toSet()

    fun twoWordAnagramsOf(word: String): Set<Pair<String, String>> {
        val target = word.lowercase()
        val candidates = dictionary.filter { it.isWrittenWithLettersOf(target) }
        val bySignature = candidates.groupBy(::signature)
        return candidates.flatMap { first ->
            val remainder = target.minusLettersOf(first)
            bySignature[signature(remainder)].orEmpty().map { second -> ordered(first, second) }
        }.toSet()
    }

    private fun signature(word: String) = word.toList().sorted().joinToString("")

    private fun ordered(a: String, b: String) = if (a <= b) a to b else b to a

    private fun String.isWrittenWithLettersOf(target: String): Boolean {
        val available = target.groupingBy { it }.eachCount()
        return groupingBy { it }.eachCount().all { (letter, count) -> count <= (available[letter] ?: 0) }
    }

    private fun String.minusLettersOf(word: String): String {
        val remaining = toMutableList()
        word.forEach { remaining.remove(it) }
        return remaining.joinToString("")
    }
}
