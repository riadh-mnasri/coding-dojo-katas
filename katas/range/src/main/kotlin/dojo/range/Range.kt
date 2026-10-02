// Copyright (c) 2026 Riadh MNASRI. Licensed under the MIT License.
package dojo.range

/** Un intervalle d'entiers, chaque borne ouverte « ( ) » ou fermée « [ ] ». */
class Range(
    private val start: Int,
    private val startIncluded: Boolean,
    private val end: Int,
    private val endIncluded: Boolean,
) {
    fun contains(vararg values: Int): Boolean = values.all { value ->
        (if (startIncluded) value >= start else value > start) && (if (endIncluded) value <= end else value < end)
    }

    companion object {
        private val NOTATION = Regex("""([\[(])\s*(-?\d+)\s*,\s*(-?\d+)\s*([])])""")

        fun parse(notation: String): Range {
            val (open, start, end, close) = NOTATION.matchEntire(notation.trim())?.destructured
                ?: throw IllegalArgumentException("Not a range: $notation")
            return Range(start.toInt(), open == "[", end.toInt(), close == "]")
        }
    }
}
