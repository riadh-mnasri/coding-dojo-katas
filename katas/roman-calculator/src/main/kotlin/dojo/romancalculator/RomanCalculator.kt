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

    /** Formes soustractives et leur forme additive, la plus longue d'abord à chaque niveau. */
    private val subtractives = listOf(
        "CM" to "DCCCC", "CD" to "CCCC",
        "XC" to "LXXXX", "XL" to "XXXX",
        "IX" to "VIIII", "IV" to "IIII",
    )

    fun add(left: String, right: String): String {
        val sorted = (left + right).toList().sortedBy { ORDER.indexOf(it) }.joinToString("")
        val grouped = groupings.fold(sorted) { roman, (many, one) -> roman.replace(many, one) }
        return subtractives.fold(grouped) { roman, (short, long) -> roman.replace(long, short) }
    }
}
