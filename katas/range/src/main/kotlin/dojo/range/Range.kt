// Copyright (c) 2026 Riadh MNASRI. Licensed under the MIT License.
package dojo.range

/** Un intervalle d'entiers, chaque borne ouverte « ( ) » ou fermée « [ ] ». */
class Range(
    private val start: Int,
    private val startIncluded: Boolean,
    private val end: Int,
    private val endIncluded: Boolean,
) {
    private val first = if (startIncluded) start else start + 1
    private val last = if (endIncluded) end else end - 1

    init {
        require(first <= last) { "A range must hold at least one point" }
    }

    fun contains(vararg values: Int): Boolean = values.all { it in first..last }

    fun allPoints(): List<Int> = (first..last).toList()

    fun endPoints(): Pair<Int, Int> = first to last

    fun containsRange(other: Range): Boolean = contains(other.first, other.last)

    fun overlapsRange(other: Range): Boolean = first <= other.last && other.first <= last

    // Sur des entiers, deux notations qui couvrent les mêmes points décrivent le même intervalle.
    override fun equals(other: Any?) = other is Range && first == other.first && last == other.last

    override fun hashCode() = 31 * first + last

    override fun toString() = "[$first,$last]"

    companion object {
        private val NOTATION = Regex("""([\[(])\s*(-?\d+)\s*,\s*(-?\d+)\s*([])])""")

        fun parse(notation: String): Range {
            val (open, start, end, close) = NOTATION.matchEntire(notation.trim())?.destructured
                ?: throw IllegalArgumentException("Not a range: $notation")
            return Range(start.toInt(), open == "[", end.toInt(), close == "]")
        }
    }
}
