// Copyright (c) 2026 Riadh MNASRI. Licensed under the MIT License.
package dojo.anagram

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

class AnagramsTest {

    /** La liste proposée par l'énoncé : un en-tête, puis des mots séparés par des blancs. */
    private val kataWordList: List<String> =
        javaClass.getResource("/kata-wordlist.txt")!!.readText().lines().drop(1).flatMap { it.split(Regex("\\s+")) }
            .filter { it.isNotBlank() && it.all(Char::isLetter) }

    @Test
    fun `an empty dictionary gives no anagram`() {
        assertThat(Anagrams(emptyList()).twoWordAnagramsOf("documenting")).isEmpty()
    }

    @Test
    fun `finds two words that use exactly the letters of the word`() {
        val anagrams = Anagrams(listOf("document", "gin", "cat")).twoWordAnagramsOf("documenting")

        assertThat(anagrams).containsExactly("document" to "gin")
    }

    @Test
    fun `the same word can be used twice`() {
        assertThat(Anagrams(listOf("ab")).twoWordAnagramsOf("abab")).containsExactly("ab" to "ab")
    }

    @Test
    fun `dictionary words are compared and returned in lower case`() {
        assertThat(Anagrams(listOf("Document", "GIN")).twoWordAnagramsOf("documenting")).containsExactly("document" to "gin")
    }

    @Test
    fun `the word list suggested by the kata holds no two-word anagram of documenting`() {
        assertThat(kataWordList).hasSizeGreaterThan(1500)

        assertThat(Anagrams(kataWordList).twoWordAnagramsOf("documenting")).isEmpty()
    }
}
