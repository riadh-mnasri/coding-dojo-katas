// Copyright (c) 2026 Riadh MNASRI. Licensed under the MIT License.
package dojo.rsa

object Arithmetic {
    /** L'inverse de [value] modulo [modulus], par l'algorithme d'Euclide étendu. */
    fun modularInverse(value: Long, modulus: Long): Long {
        var (oldR, r) = value to modulus
        var (oldS, s) = 1L to 0L
        while (r != 0L) {
            val quotient = oldR / r
            oldR = r.also { r = oldR - quotient * r }
            oldS = s.also { s = oldS - quotient * s }
        }
        return Math.floorMod(oldS, modulus)
    }

    fun gcd(a: Long, b: Long): Long = if (b == 0L) a else gcd(b, a % b)
}
