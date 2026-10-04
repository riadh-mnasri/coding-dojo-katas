// Copyright (c) 2026 Riadh MNASRI. Licensed under the MIT License.
package dojo.qotd

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.Test
import java.net.URI
import java.net.http.HttpClient
import java.net.http.HttpRequest
import java.net.http.HttpResponse

/** Test d'intégration : un vrai serveur sur un port libre, interrogé par un vrai client HTTP. */
class QuoteServerTest {

    private val server = QuoteServer(Quotes(listOf("Keep it simple.", "Fail fast.")), port = 0).also { it.start() }

    @AfterEach
    fun stop() = server.stop()

    private fun get(path: String): HttpResponse<String> = HttpClient.newHttpClient().send(
        HttpRequest.newBuilder(URI("http://localhost:${server.port}$path")).build(),
        HttpResponse.BodyHandlers.ofString(),
    )

    @Test
    fun `a visit returns a quote`() {
        val response = get("/")

        assertThat(response.statusCode()).isEqualTo(200)
        assertThat(response.body()).isIn("Keep it simple.", "Fail fast.")
    }

    @Test
    fun `the q parameter searches the quotes`() {
        assertThat(get("/?q=fast").body()).isEqualTo("Fail fast.")
        assertThat(get("/?q=nothing").statusCode()).isEqualTo(404)
    }
}
