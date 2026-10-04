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

    @Test
    fun `is sorted by name, descending`() {
        // Story 4 : l'exigence de tri a changé, ce test a été modifié plutôt qu'un second test ajouté.
        // Seul l'ordre compte, sans figer le contenu ni la casse.
        val names = report.sundayWorkers()

        assertThat(names).isSortedAccordingTo(String.CASE_INSENSITIVE_ORDER.reversed())
    }

    @Test
    fun `names are capitalized`() {
        // Seule la casse compte : chaque nom est en majuscules, quels que soient les noms et leur ordre.
        assertThat(report.sundayWorkers()).isNotEmpty().allSatisfy { assertThat(it).isUpperCase() }
    }

    @Test
    fun `the whole report, as a readable example`() {
        // Un seul test fige la sortie complète : il sert de documentation, les autres restent ciblés.
        assertThat(report.sundayWorkers()).containsExactly("SEPP", "MIKE")
    }
}
