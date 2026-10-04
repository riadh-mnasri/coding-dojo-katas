// Copyright (c) 2026 Riadh MNASRI. Licensed under the MIT License.
package dojo.rsa

data class PublicKey(val modulus: Long, val exponent: Long)

data class PrivateKey(val modulus: Long, val exponent: Long)

data class KeyPair(val public: PublicKey, val private: PrivateKey) {
    companion object {
        private const val MIN_MODULUS = (1L shl 24) + 2
        private const val MAX_MODULUS = (1L shl 32) - 1

        /** N = p × q, n = (p − 1) × (q − 1), d = c⁻¹ mod n. */
        fun of(p: Long, q: Long, c: Long): KeyPair {
            val modulus = p * q
            val totient = (p - 1) * (q - 1)
            require(modulus in MIN_MODULUS..MAX_MODULUS) { "N = $modulus must be between 2^24 + 1 and 2^32" }
            require(c in 2 until totient && Arithmetic.gcd(c, totient) == 1L) { "c = $c must be coprime with n = $totient" }
            return KeyPair(PublicKey(modulus, c), PrivateKey(modulus, Arithmetic.modularInverse(c, totient)))
        }
    }
}
