// Copyright (c) 2026 Riadh MNASRI. Licensed under the MIT License.
package dojo.leapyears

class LeapYear {

    fun isLeap(year: Int): Boolean = when {
        year.isDivisibleBy(400) -> true
        year.isDivisibleBy(100) -> false
        else -> year.isDivisibleBy(4)
    }

    private fun Int.isDivisibleBy(divisor: Int) = this % divisor == 0
}
