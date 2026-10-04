// Copyright (c) 2026 Riadh MNASRI. Licensed under the MIT License.
package dojo.rsa

import kotlin.random.Random

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

        /**
         * Deux nombres premiers distincts entre 2^12 et 2^16 donnent toujours 2^24 < N < 2^32 ;
         * c est tiré au hasard jusqu'à être premier avec n.
         */
        fun generate(random: Random): KeyPair {
            val p = randomPrime(random)
            var q = randomPrime(random)
            while (q == p) q = randomPrime(random)
            val totient = (p - 1) * (q - 1)
            var c = random.nextLong(3, totient)
            while (Arithmetic.gcd(c, totient) != 1L) c = random.nextLong(3, totient)
            return of(p, q, c)
        }

        private fun randomPrime(random: Random): Long {
            while (true) {
                val candidate = random.nextLong(4_097, 65_536)
                if (isPrime(candidate)) return candidate
            }
        }

        private fun isPrime(n: Long): Boolean = n >= 2 && (2L..kotlin.math.sqrt(n.toDouble()).toLong()).none { n % it == 0L }
    }
}
