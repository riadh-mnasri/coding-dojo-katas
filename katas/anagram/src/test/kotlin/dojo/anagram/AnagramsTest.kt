// Copyright (c) 2026 Riadh MNASRI. Licensed under the MIT License.
package dojo.anagram

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Assertions.assertTimeoutPreemptively
import org.junit.jupiter.api.Assumptions.assumeTrue
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.function.ThrowingSupplier
import java.io.File
import java.time.Duration

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

    /**
     * Le dictionnaire web2 (Webster 1934, domaine public) livré avec macOS ; ailleurs, le test est ignoré.
     * Les 52 paires attendues ont été calculées à part, avec un script Python indépendant.
     */
    @Test
    fun `finds the 52 two-word anagrams of documenting in web2 within ten seconds`() {
        val web2 = File("/usr/share/dict/web2")
        assumeTrue(web2.exists(), "no web2 dictionary on this machine")
        val words = web2.readLines().filter { it.isNotBlank() && it.all(Char::isLetter) }

        val anagrams = assertTimeoutPreemptively(
            Duration.ofSeconds(10),
            ThrowingSupplier { Anagrams(words).twoWordAnagramsOf("documenting") },
        )

        assertThat(anagrams).hasSize(52).contains("document" to "gin", "coming" to "tuned", "medoc" to "tuning")
    }
}
