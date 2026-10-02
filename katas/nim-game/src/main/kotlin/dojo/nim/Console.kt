// Copyright (c) 2026 Riadh MNASRI. Licensed under the MIT License.
package dojo.nim

/** Partie en terminal : une fine couche non testée au-dessus de [NimGame]. */
fun main() {
    print("Joueur 1 : ")
    val first = readln().ifBlank { "Joueur 1" }
    print("Joueur 2 : ")
    val second = readln().ifBlank { "Joueur 2" }
    val game = NimGame(first, second)
    while (game.winner == null) {
        println("| ".repeat(game.sticks) + " (${game.sticks})")
        print("${game.currentPlayer}, combien d'allumettes (1 à 3) ? ")
        runCatching { game.take(readln().trim().toInt()) }.onFailure { println(it.message) }
    }
    println("${game.winner} a gagné !")
}
