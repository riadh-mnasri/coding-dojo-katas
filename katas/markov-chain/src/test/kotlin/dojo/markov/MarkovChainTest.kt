// Copyright (c) 2026 Riadh MNASRI. Licensed under the MIT License.
package dojo.markov

import org.assertj.core.api.Assertions.assertThat
import org.assertj.core.api.Assertions.assertThatThrownBy
import org.junit.jupiter.api.Test
import kotlin.random.Random

class MarkovChainTest {

    /** Un tirage aléatoire piloté par le test : il renvoie les valeurs prévues, dans l'ordre. */
    private class ScriptedRandom(vararg draws: Double) : Random() {
        private val draws = ArrayDeque(draws.toList())
        override fun nextBits(bitCount: Int) = error("not used")
        override fun nextDouble() = draws.removeFirst()
    }

    private val text = "les hommes libres peuvent rester libres ou bien vendre leur liberté"

    @Test
    fun `a single word has no follower`() {
        assertThat(MarkovChain.learn("bonjour").followersOf("bonjour")).isEmpty()
    }

    @Test
    fun `each word knows its followers and how often they follow`() {
        val chain = MarkovChain.learn(text)

        assertThat(chain.followersOf("les")).isEqualTo(mapOf("hommes" to 1.0))
        assertThat(chain.followersOf("libres")).isEqualTo(mapOf("peuvent" to 0.5, "ou" to 0.5))
        assertThat(chain.followersOf("liberté")).isEmpty()
    }

    @Test
    fun `a chain with a single path generates that path`() {
        val chain = MarkovChain.learn("le chat dort")

        assertThat(chain.generate(words = 3, startingWith = "le")).isEqualTo("le chat dort")
    }

    @Test
    fun `the next word is drawn according to the frequencies`() {
        // Given : après « libres », « peuvent » occupe [0 ; 0,5[ et « ou » [0,5 ; 1[
        val chain = MarkovChain.learn(text)

        // When
        val low = chain.generate(words = 2, startingWith = "libres", random = ScriptedRandom(0.2))
        val high = chain.generate(words = 2, startingWith = "libres", random = ScriptedRandom(0.75))

        // Then
        assertThat(low).isEqualTo("libres peuvent")
        assertThat(high).isEqualTo("libres ou")
    }

    @Test
    fun `at a dead end the text restarts from a random word`() {
        // Given : « dort » n'a pas de suivant ; le tirage 0,0 relance sur le premier mot connu
        val chain = MarkovChain.learn("le chat dort")

        // When
        val generated = chain.generate(words = 5, startingWith = "chat", random = ScriptedRandom(0.0, 0.0, 0.0, 0.0))

        // Then
        assertThat(generated).isEqualTo("chat dort le chat dort")
    }

    @Test
    fun `a text without any pair of words cannot generate anything longer`() {
        val chain = MarkovChain.learn("bonjour")

        assertThatThrownBy { chain.generate(words = 3, startingWith = "bonjour") }
            .isInstanceOf(IllegalStateException::class.java)
    }
}
