// Copyright (c) 2026 Riadh MNASRI. Licensed under the MIT License.
package dojo.romancalculator

object RomanCalculator {
    private const val ORDER = "MDCLXVI"

    /** Regroupements du plus petit au plus grand, pour que les retenues se propagent. */
    private val groupings = listOf(
        "IIIII" to "V", "VV" to "X",
        "XXXXX" to "L", "LL" to "C",
        "CCCCC" to "D", "DD" to "M",
    )

    fun add(left: String, right: String): String {
        val sorted = (left + right).toList().sortedBy { ORDER.indexOf(it) }.joinToString("")
        return groupings.fold(sorted) { roman, (many, one) -> roman.replace(many, one) }
            .replace("VIIII", "IX")
            .replace("IIII", "IV")
    }
}
