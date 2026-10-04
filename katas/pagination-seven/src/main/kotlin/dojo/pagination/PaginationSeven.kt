// Copyright (c) 2026 Riadh MNASRI. Licensed under the MIT License.
package dojo.pagination

object PaginationSeven {
    private val ELLIPSIS: Int? = null

    fun render(page: Int, total: Int): String {
        val slots = if (total <= 7) (1..total).toList() else listOf(1, ELLIPSIS, page - 1, page, page + 1, ELLIPSIS, total)
        return slots.joinToString(" ") {
            when (it) {
                ELLIPSIS -> "…"
                page -> "($it)"
                else -> "$it"
            }
        }
    }
}
