// Copyright (c) 2026 Riadh MNASRI. Licensed under the MIT License.
package dojo.qotd

import com.sun.net.httpserver.HttpExchange
import com.sun.net.httpserver.HttpServer
import java.net.InetSocketAddress
import java.net.URLDecoder

/** L'adaptateur web : traduit une requête HTTP en appel à [Quotes], et la réponse en texte. */
class QuoteServer(private val quotes: Quotes, port: Int = 8080) {
    private val server = HttpServer.create(InetSocketAddress(port), 0).apply {
        createContext("/") { exchange -> handle(exchange) }
    }

    val port: Int get() = server.address.port

    fun start() = server.start()

    fun stop() = server.stop(0)

    private fun handle(exchange: HttpExchange) {
        val search = exchange.requestURI.rawQuery?.split("&")
            ?.map { it.split("=", limit = 2) }
            ?.firstOrNull { it[0] == "q" }
            ?.getOrNull(1)
            ?.let { URLDecoder.decode(it, Charsets.UTF_8) }
        val quote = quotes.next(containing = search)
        val (status, body) = if (quote == null) 404 to "No quote contains '$search'" else 200 to quote
        val bytes = body.toByteArray()
        exchange.responseHeaders.add("Content-Type", "text/plain; charset=utf-8")
        exchange.sendResponseHeaders(status, bytes.size.toLong())
        exchange.responseBody.use { it.write(bytes) }
    }
}

fun main() {
    val quotes = Quotes(
        listOf(
            "Simplicity is prerequisite for reliability. (Edsger W. Dijkstra)",
            "Make it work, make it right, make it fast. (Kent Beck)",
            "Programs must be written for people to read. (Harold Abelson)",
        ),
    )
    QuoteServer(quotes).start()
    println("Quote of the day on http://localhost:8080/ (add ?q=word to search)")
}
