// Copyright (c) 2026 Riadh MNASRI. Licensed under the MIT License.
package dojo.potter

import java.math.BigDecimal

object Potter {
    fun price(books: List<Int>): BigDecimal = BigDecimal(8) * BigDecimal(books.size)
}
