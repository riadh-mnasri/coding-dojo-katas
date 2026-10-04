// Copyright (c) 2026 Riadh MNASRI. Licensed under the MIT License.
package dojo.lags

data class Request(val id: String, val start: Int, val duration: Int, val price: Int)

object Lags {
    fun bestGain(requests: List<Request>): Int = requests.sumOf { it.price }
}
