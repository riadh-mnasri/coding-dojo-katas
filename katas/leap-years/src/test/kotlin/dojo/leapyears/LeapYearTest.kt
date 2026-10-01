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
}
