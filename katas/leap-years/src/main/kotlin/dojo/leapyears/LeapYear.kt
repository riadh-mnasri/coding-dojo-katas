// Copyright (c) 2026 Riadh MNASRI. Licensed under the MIT License.
package dojo.leapyears

class LeapYear {
    fun isLeap(year: Int): Boolean = year % 4 == 0 && year % 100 != 0
}
