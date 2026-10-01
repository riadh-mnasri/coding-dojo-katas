// Copyright (c) 2026 Riadh MNASRI. Licensed under the MIT License.
package dojo.codecracker

class CodeCracker(alphabet: String, key: String) {
    private val decryption = key.zip(alphabet).toMap()

    fun decrypt(message: String): String = message.map { decryption[it] ?: it }.joinToString("")

    companion object {
        const val ALPHABET = "abcdefghijklmnopqrstuvwxyz"
        const val KATA_KEY = "!)\"(£*%&><@abcdefghijklmno"

        val kata = CodeCracker(ALPHABET, KATA_KEY)
    }
}
