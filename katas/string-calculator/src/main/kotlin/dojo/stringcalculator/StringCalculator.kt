// Copyright (c) 2026 Riadh MNASRI. Licensed under the MIT License.
package dojo.stringcalculator

import java.math.BigDecimal

object StringCalculator {
    fun add(numbers: String): String {
        if (numbers.isEmpty()) return "0"
        return numbers.split(",")
            .map(::BigDecimal)
            .fold(BigDecimal.ZERO, BigDecimal::add)
            .stripTrailingZeros()
            .toPlainString()
    }
}
