// Copyright (c) 2026 Riadh MNASRI. Licensed under the MIT License.
package dojo.romancalculator

/**
 * Addition de chiffres romains sans jamais passer par des entiers :
 * uniquement du développement, du tri, du regroupement et de la réécriture de chaînes.
 */
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

    // Chaque opérande est développé séparément : "VIII" + "VII" ne doit pas faire apparaître un faux "IV".
    fun add(left: String, right: String): String =
        compress(group(sort(expand(left) + expand(right))))

    private fun expand(roman: String) =
        subtractives.fold(roman) { acc, (short, long) -> acc.replace(short, long) }

    private fun sort(roman: String) =
        roman.toList().sortedBy { ORDER.indexOf(it) }.joinToString("")

    private fun group(roman: String) =
        groupings.fold(roman) { acc, (many, one) -> acc.replace(many, one) }

    private fun compress(roman: String) =
        subtractives.fold(roman) { acc, (short, long) -> acc.replace(long, short) }
}
