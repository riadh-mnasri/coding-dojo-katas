// Copyright (c) 2026 Riadh MNASRI. Licensed under the MIT License.
package dojo.employeereport

data class Employee(val name: String, val age: Int)

class EmployeeReport(private val employees: List<Employee>) {
    fun sundayWorkers(): List<String> = employees.filter { it.age >= 18 }.map { it.name.uppercase() }.sorted()
}
