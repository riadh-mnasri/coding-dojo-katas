// Copyright (c) 2026 Riadh MNASRI. Licensed under the MIT License.
package dojo.minesweeper

class Field(private val rows: List<String>) {
    fun hints(): List<String> = rows.map { row -> row.map { '0' }.joinToString("") }
}
