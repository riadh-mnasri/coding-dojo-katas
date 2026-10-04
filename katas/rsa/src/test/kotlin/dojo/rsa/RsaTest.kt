// Copyright (c) 2026 Riadh MNASRI. Licensed under the MIT License.
package dojo.rsa

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

/** Les valeurs de l'exemple de l'énoncé : p = 51581, q = 60101, c = 66797. */
class RsaTest {

    @Test
    fun `the modular inverse of c modulo n is d`() {
        assertThat(Arithmetic.modularInverse(66_797L, 3_099_958_000L)).isEqualTo(1_336_940_133L)
    }

    @Test
    fun `builds the key pair of the sample`() {
        val keys = KeyPair.of(p = 51_581, q = 60_101, c = 66_797)

        assertThat(keys.public).isEqualTo(PublicKey(3_100_069_681L, 66_797L))
        assertThat(keys.private).isEqualTo(PrivateKey(3_100_069_681L, 1_336_940_133L))
    }
}
