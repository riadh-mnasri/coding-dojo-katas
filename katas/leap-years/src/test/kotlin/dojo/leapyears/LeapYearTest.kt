// Copyright (c) 2026 Riadh MNASRI. Licensed under the MIT License.
package dojo.leapyears

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.ValueSource

class LeapYearTest {

    @ParameterizedTest
    @ValueSource(ints = [2017, 2018, 2019])
    fun `years not divisible by 4 are not leap years`(year: Int) {
        assertThat(LeapYear().isLeap(year)).isFalse()
    }

    @ParameterizedTest
    @ValueSource(ints = [2008, 2012, 2016])
    fun `years divisible by 4 but not by 100 are leap years`(year: Int) {
        assertThat(LeapYear().isLeap(year)).isTrue()
    }

    @ParameterizedTest
    @ValueSource(ints = [1700, 1800, 1900, 2100])
    fun `years divisible by 100 but not by 400 are not leap years`(year: Int) {
        assertThat(LeapYear().isLeap(year)).isFalse()
    }

    @ParameterizedTest
    @ValueSource(ints = [1600, 2000, 2400])
    fun `years divisible by 400 are leap years`(year: Int) {
        assertThat(LeapYear().isLeap(year)).isTrue()
    }

    @ParameterizedTest
    @ValueSource(ints = [4000, 8000, 12000])
    fun `with the 4000-year rule, years divisible by 4000 are not leap years`(year: Int) {
        assertThat(LeapYear(withFourThousandYearRule = true).isLeap(year)).isFalse()
    }
}
