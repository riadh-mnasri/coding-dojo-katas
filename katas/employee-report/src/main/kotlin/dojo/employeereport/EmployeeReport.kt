// Copyright (c) 2026 Riadh MNASRI. Licensed under the MIT License.
package dojo.employeereport

data class Employee(val name: String, val age: Int)

/** Le rapport du dimanche : qui peut travailler, en majuscules, du dernier au premier nom. */
class EmployeeReport(private val employees: List<Employee>) {

    fun sundayWorkers(): List<String> = employees
        .filter { it.age >= MINIMUM_SUNDAY_AGE }
        .map { it.name.uppercase() }
        .sortedDescending()

    private companion object {
        /** Les moins de 18 ans n'ont pas le droit de travailler le dimanche. */
        const val MINIMUM_SUNDAY_AGE = 18
    }
}
