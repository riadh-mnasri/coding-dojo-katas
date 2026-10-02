// Copyright (c) 2026 Riadh MNASRI. Licensed under the MIT License.
package dojo.wordwrap

object Wrapper {
    fun wrap(text: String, column: Int): String =
        if (text.length <= column) text
        else text.substring(0, column) + "\n" + wrap(text.substring(column), column)
}
