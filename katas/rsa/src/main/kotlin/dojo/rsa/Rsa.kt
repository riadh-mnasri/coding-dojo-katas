// Copyright (c) 2026 Riadh MNASRI. Licensed under the MIT License.
package dojo.rsa

import java.math.BigInteger

object Rsa {
    private const val CLEAR_BLOCK = 3
    private const val CIPHER_BLOCK = 4

    /** Chaque bloc de 3 octets, lu comme un entier a, devient a^c mod N sur 4 octets. */
    fun encrypt(message: ByteArray, key: PublicKey): ByteArray =
        message.toList().chunked(CLEAR_BLOCK).flatMap { block ->
            toBytes(power(toNumber(block), key.exponent, key.modulus), CIPHER_BLOCK)
        }.toByteArray()

    /** Chaque bloc de 4 octets, lu comme un entier a, redevient a^d mod N sur 3 octets. */
    fun decrypt(encrypted: ByteArray, key: PrivateKey): ByteArray =
        encrypted.toList().chunked(CIPHER_BLOCK).flatMap { block ->
            toBytes(power(toNumber(block), key.exponent, key.modulus), CLEAR_BLOCK)
        }.toByteArray()

    private fun power(base: Long, exponent: Long, modulus: Long): Long =
        BigInteger.valueOf(base).modPow(BigInteger.valueOf(exponent), BigInteger.valueOf(modulus)).toLong()

    private fun toNumber(block: List<Byte>): Long = block.fold(0L) { value, byte -> (value shl 8) or (byte.toLong() and 0xFF) }

    private fun toBytes(number: Long, size: Int): List<Byte> = (size - 1 downTo 0).map { shift -> (number shr (8 * shift)).toByte() }
}
