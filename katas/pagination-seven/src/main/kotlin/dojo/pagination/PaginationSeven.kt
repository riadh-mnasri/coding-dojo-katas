// Copyright (c) 2026 Riadh MNASRI. Licensed under the MIT License.
package dojo.pagination

/**
 * Pagination sur 7 cases au plus : la première page, la dernière, la page courante et ses voisines,
 * les pages intermédiaires étant regroupées sous « … ».
 */
object PaginationSeven {
    private const val SLOTS = 7

    /** Au début ou à la fin, on montre 5 pages d'affilée : 5 + « … » + la page à l'autre bout = 7 cases. */
    private const val EDGE = SLOTS - 2

    private val ELLIPSIS: Int? = null

    fun render(page: Int, total: Int): String {
        val slots = when {
            total <= SLOTS -> (1..total).toList()
            page < EDGE -> (1..EDGE).toList() + listOf(ELLIPSIS, total)
            page > total - EDGE + 1 -> listOf(1, ELLIPSIS) + (total - EDGE + 1..total).toList()
            else -> listOf(1, ELLIPSIS, page - 1, page, page + 1, ELLIPSIS, total)
        }
        return slots.joinToString(" ") {
            when (it) {
                ELLIPSIS -> "…"
                page -> "($it)"
                else -> "$it"
            }
        }
    }
}
