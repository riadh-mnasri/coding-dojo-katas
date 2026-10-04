// Copyright (c) 2026 Riadh MNASRI. Licensed under the MIT License.
package dojo.employeereport

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

class EmployeeReportTest {

    private val employees = listOf(
        Employee("Max", 17),
        Employee("Sepp", 18),
        Employee("Nina", 15),
        Employee("Mike", 51),
    )

    private val report = EmployeeReport(employees)

    @Test
    fun `lists only employees of 18 or more, who may work on Sundays`() {
        // Seule l'appartenance compte ici : ni l'ordre ni la casse ne sont vérifiés.
        assertThat(report.sundayWorkers()).extracting<String> { it.lowercase() }
            .containsExactlyInAnyOrder("sepp", "mike")
    }
}
