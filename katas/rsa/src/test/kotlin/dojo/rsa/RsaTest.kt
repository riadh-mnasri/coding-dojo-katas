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

    private val keys = KeyPair.of(p = 51_581, q = 60_101, c = 66_797)

    @Test
    fun `encrypts each 3-byte block of the sample into a 4-byte block`() {
        val encrypted = Rsa.encrypt("Hello world!".toByteArray(), keys.public)

        assertThat(blocksOf4(encrypted)).containsExactly(352_431_401L, 2_267_192_425L, 538_638_606L, 1_131_048_795L)
    }

    private fun blocksOf4(bytes: ByteArray): List<Long> =
        bytes.toList().chunked(4).map { block -> block.fold(0L) { value, byte -> (value shl 8) or (byte.toLong() and 0xFF) } }

    @Test
    fun `decrypting gives the sample message back`() {
        val encrypted = Rsa.encrypt("Hello world!".toByteArray(), keys.public)

        assertThat(String(Rsa.decrypt(encrypted, keys.private))).isEqualTo("Hello world!")
    }

    @Test
    fun `the encrypted message travels as readable base64`() {
        val encrypted = Rsa.encrypt("Hello world!".toByteArray(), keys.public)

        assertThat(Transport.encode(encrypted)).isEqualTo("FQGtKYcinGkgGvkOQ2pvWw==")
        assertThat(Transport.decode("FQGtKYcinGkgGvkOQ2pvWw==")).isEqualTo(encrypted)
    }
}
