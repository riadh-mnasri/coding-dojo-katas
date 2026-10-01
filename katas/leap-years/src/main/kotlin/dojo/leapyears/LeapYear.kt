// Copyright (c) 2026 Riadh MNASRI. Licensed under the MIT License.
package dojo.leapyears

/**
 * Calendrier grégorien, avec en option la règle des 4000 ans proposée en extension du kata.
 */
class LeapYear(private val withFourThousandYearRule: Boolean = false) {

    fun isLeap(year: Int): Boolean = when {
        withFourThousandYearRule && year.isDivisibleBy(4000) -> false
        year.isDivisibleBy(400) -> true
        year.isDivisibleBy(100) -> false
        else -> year.isDivisibleBy(4)
    }

    private fun Int.isDivisibleBy(divisor: Int) = this % divisor == 0
}
