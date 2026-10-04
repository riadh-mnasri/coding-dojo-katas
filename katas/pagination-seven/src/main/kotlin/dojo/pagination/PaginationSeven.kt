// Copyright (c) 2026 Riadh MNASRI. Licensed under the MIT License.
package dojo.pagination

object PaginationSeven {
    fun render(page: Int, total: Int): String =
        (1..total).joinToString(" ") { if (it == page) "($it)" else "$it" }
}
