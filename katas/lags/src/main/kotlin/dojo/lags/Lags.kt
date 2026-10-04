// Copyright (c) 2026 Riadh MNASRI. Licensed under the MIT License.
package dojo.lags

data class Request(val id: String, val start: Int, val duration: Int, val price: Int) {
    val end get() = start + duration
}

object Lags {
    fun bestGain(requests: List<Request>): Int = best(requests.sortedBy { it.start })

    /** Soit on refuse la première demande, soit on l'accepte et on ne garde que celles qui partent après son retour. */
    private fun best(requests: List<Request>): Int {
        if (requests.isEmpty()) return 0
        val first = requests.first()
        val rest = requests.drop(1)
        return maxOf(best(rest), first.price + best(rest.filter { it.start >= first.end }))
    }
}
