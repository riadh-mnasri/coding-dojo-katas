// Copyright (c) 2026 Riadh MNASRI. Licensed under the MIT License.
package dojo.markov

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

class MarkovChainTest {

    private val text = "les hommes libres peuvent rester libres ou bien vendre leur liberté"

    @Test
    fun `a single word has no follower`() {
        assertThat(MarkovChain.learn("bonjour").followersOf("bonjour")).isEmpty()
    }
}
