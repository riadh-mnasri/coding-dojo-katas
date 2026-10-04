// Copyright (c) 2026 Riadh MNASRI. Licensed under the MIT License.
package dojo.dfs

/** Le guide humain, au terminal : de l'exploration manuelle, que les tests rejouent avec un guide scripté. */
class ConsoleGuide : Guide {
    override fun isGoal(place: String): Boolean {
        print("Sommes-nous arrivés en $place ? (o/n) ")
        return readln().trim().lowercase().startsWith("o")
    }

    override fun exitsOf(place: String): List<String> {
        print("Quelles sont les sorties de $place ? (séparées par des espaces) ")
        return readln().split(" ").filter { it.isNotBlank() }
    }
}

fun main() {
    print("Où sommes-nous ? ")
    val path = DepthFirstSearch(ConsoleGuide()).pathFrom(readln().trim())
    println(path?.joinToString(" → ") ?: "Pas de chemin.")
}
