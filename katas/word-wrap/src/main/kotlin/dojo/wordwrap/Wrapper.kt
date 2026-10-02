// Copyright (c) 2026 Riadh MNASRI. Licensed under the MIT License.
package dojo.wordwrap

object Wrapper {

    /** Insère des retours à la ligne pour qu'aucune ligne ne dépasse [column] caractères. */
    fun wrap(text: String, column: Int): String {
        if (text.length <= column) return text
        val space = text.lastIndexOf(' ', column)
        return if (space >= 0) breakAt(text, space, skip = 1, column) else breakAt(text, column, skip = 0, column)
    }

    /** Coupe à [index] (en sautant l'espace éventuel) et enveloppe le reste. */
    private fun breakAt(text: String, index: Int, skip: Int, column: Int) =
        text.substring(0, index) + "\n" + wrap(text.substring(index + skip), column)
}
