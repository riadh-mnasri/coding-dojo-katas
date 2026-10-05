// Copyright (c) 2026 Riadh MNASRI. Licensed under the MIT License.
package dojo.sudoku

sealed interface CellValue {
    data object Unknown : CellValue
    data class Known(val number: Int) : CellValue
    data object Impossible : CellValue
}

class Cell {
    private val possible = (1..9).toMutableSet()

    fun isPossible(number: Int) = number in possible

    fun exclude(number: Int) {
        possible -= number
    }

    fun value(): CellValue = when (possible.size) {
        0 -> CellValue.Impossible
        1 -> CellValue.Known(possible.single())
        else -> CellValue.Unknown
    }
}
