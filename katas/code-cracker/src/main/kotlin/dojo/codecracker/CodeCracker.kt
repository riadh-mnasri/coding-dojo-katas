// Copyright (c) 2026 Riadh MNASRI. Licensed under the MIT License.
package dojo.codecracker

class CodeCracker(alphabet: String, key: String) {
    private val encryption = alphabet.zip(key).toMap()
    private val decryption = key.zip(alphabet).toMap()

    fun encrypt(message: String): String = message.map { encryption[it] ?: it }.joinToString("")

    fun decrypt(message: String): String = message.map { decryption[it] ?: it }.joinToString("")

    companion object {
        const val ALPHABET = "abcdefghijklmnopqrstuvwxyz"
        const val KATA_KEY = "!)\"(£*%&><@abcdefghijklmno"

        val kata = CodeCracker(ALPHABET, KATA_KEY)
    }
}
