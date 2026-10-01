// Copyright (c) 2026 Riadh MNASRI. Licensed under the MIT License.
package dojo.dictionaryreplacer

object DictionaryReplacer {
    private val placeholder = Regex("""\$(\w+)\$""")

    fun replace(text: String, dictionary: Map<String, String>): String =
        placeholder.replace(text) { match -> dictionary[match.groupValues[1]] ?: match.value }
}
