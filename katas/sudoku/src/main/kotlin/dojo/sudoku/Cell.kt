// Copyright (c) 2026 Riadh MNASRI. Licensed under the MIT License.
package dojo.sudoku

sealed interface CellValue {
    data object Unknown : CellValue
}

class Cell {
    private val possible = (1..9).toMutableSet()

    fun isPossible(number: Int) = number in possible

    fun value(): CellValue = CellValue.Unknown
}
