// Copyright (c) 2026 Riadh MNASRI. Licensed under the MIT License.
package dojo.codecracker

/**
 * Chiffrement par substitution : la n-ième lettre de l'alphabet correspond au n-ième symbole de la clé.
 * Les caractères absents de la table (espaces, ponctuation...) traversent sans changement.
 */
class CodeCracker(alphabet: String, key: String) {
    init {
        require(alphabet.length == key.length) { "Alphabet and key must have the same length" }
        require(key.toSet().size == key.length) { "Key symbols must be unique to be decrypted" }
    }

    private val encryption = alphabet.zip(key).toMap()
    private val decryption = encryption.entries.associate { (plain, cipher) -> cipher to plain }

    fun encrypt(message: String): String = message.substitute(encryption)

    fun decrypt(message: String): String = message.substitute(decryption)

    private fun String.substitute(table: Map<Char, Char>) = map { table[it] ?: it }.joinToString("")

    companion object {
        const val ALPHABET = "abcdefghijklmnopqrstuvwxyz"
        const val KATA_KEY = "!)\"(£*%&><@abcdefghijklmno"

        val kata = CodeCracker(ALPHABET, KATA_KEY)
    }
}
