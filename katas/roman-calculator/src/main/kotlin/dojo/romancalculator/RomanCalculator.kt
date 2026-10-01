// Copyright (c) 2026 Riadh MNASRI. Licensed under the MIT License.
package dojo.romancalculator

object RomanCalculator {
    private const val ORDER = "MDCLXVI"

    fun add(left: String, right: String): String =
        (left + right).toList().sortedBy { ORDER.indexOf(it) }.joinToString("")
}
