// Copyright (c) 2026 Riadh MNASRI. Licensed under the MIT License.
package dojo.rsa

data class PublicKey(val modulus: Long, val exponent: Long)

data class PrivateKey(val modulus: Long, val exponent: Long)

data class KeyPair(val public: PublicKey, val private: PrivateKey) {
    companion object {
        /** N = p × q, n = (p − 1) × (q − 1), d = c⁻¹ mod n. */
        fun of(p: Long, q: Long, c: Long): KeyPair {
            val modulus = p * q
            val totient = (p - 1) * (q - 1)
            return KeyPair(PublicKey(modulus, c), PrivateKey(modulus, Arithmetic.modularInverse(c, totient)))
        }
    }
}
