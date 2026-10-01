// Copyright (c) 2026 Riadh MNASRI. Licensed under the MIT License.
package dojo.dictionaryreplacer

object DictionaryReplacer {
    fun replace(text: String, dictionary: Map<String, String>): String =
        dictionary.entries.fold(text) { acc, (key, value) -> acc.replace("\$$key\$", value) }
}
