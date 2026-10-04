// Copyright (c) 2026 Riadh MNASRI. Licensed under the MIT License.
package dojo.lags

data class Request(val id: String, val start: Int, val duration: Int, val price: Int) {
    val end get() = start + duration
}

/**
 * Le meilleur gain de l'avion, par programmation dynamique.
 *
 * Les demandes sont triées par départ. `best[i]` est le meilleur gain avec les demandes à partir de i :
 * soit on refuse la demande i (`best[i + 1]`), soit on l'accepte et on enchaîne avec la première demande
 * qui part après son retour (trouvée par dichotomie). On remplit le tableau de la fin vers le début.
 */
object Lags {
    fun bestGain(requests: List<Request>): Int {
        val sorted = requests.sortedBy { it.start }
        val starts = sorted.map { it.start }
        val best = IntArray(sorted.size + 1)
        for (i in sorted.indices.reversed()) {
            val next = firstStartingFrom(starts, sorted[i].end)
            best[i] = maxOf(best[i + 1], sorted[i].price + best[next])
        }
        return best[0]
    }

    /** L'indice de la première demande qui part à [time] ou après (ou la taille de la liste s'il n'y en a pas). */
    private fun firstStartingFrom(starts: List<Int>, time: Int): Int {
        var low = 0
        var high = starts.size
        while (low < high) {
            val middle = (low + high) / 2
            if (starts[middle] < time) low = middle + 1 else high = middle
        }
        return low
    }
}
