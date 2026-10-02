// Copyright (c) 2026 Riadh MNASRI. Licensed under the MIT License.
package dojo.lcd

object Lcd {
    private val glyphs = mapOf(
        1 to listOf("   ", "  |", "  |"),
        2 to listOf(" _ ", " _|", "|_ "),
    )

    fun render(number: Int): String = glyphs.getValue(number).joinToString("\n")
}
