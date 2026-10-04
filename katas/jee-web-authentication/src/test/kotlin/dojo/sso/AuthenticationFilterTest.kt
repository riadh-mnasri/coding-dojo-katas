// Copyright (c) 2026 Riadh MNASRI. Licensed under the MIT License.
package dojo.sso

import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import jakarta.servlet.FilterChain
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.junit.jupiter.api.Test

/**
 * Deux sortes de doublures, comme le veut le kata :
 * - des **stubs** écrits à la main pour le LDAP et le registre SSO, qui renvoient des réponses préparées ;
 * - des **mocks** MockK pour la requête, la réponse et la chaîne, dont on vérifie les appels.
 */
class AuthenticationFilterTest {

    private class StubLdap(private val accounts: Map<String, String>) : LdapAuthenticationGateway {
        override fun credentialsAreValid(userName: String, password: String) = accounts[userName] == password
    }

    private class StubRegistry : SingleSignOnRegistry {
        val sessions = mutableMapOf<String, String>()
        override fun tokenIsValid(token: String) = token in sessions
        override fun registerNewSession(userName: String) = "token-of-$userName".also { sessions[it] = userName }
        override fun endSession(token: String) {
            sessions -= token
        }
    }

    private val registry = StubRegistry()
    private val filter = AuthenticationFilter().apply {
        setLdapAuthenticationGateway(StubLdap(mapOf("alice" to "secret")))
        setSingleSignOnRegistry(registry)
    }

    private val response = mockk<HttpServletResponse>(relaxed = true)
    private val chain = mockk<FilterChain>(relaxed = true)

    private fun request(cookies: Map<String, String> = emptyMap(), parameters: Map<String, String> = emptyMap()) =
        mockk<HttpServletRequest>().apply {
            every { this@apply.cookies } returns cookies.map { (name, value) -> jakarta.servlet.http.Cookie(name, value) }
                .toTypedArray().ifEmpty { null }
            every { getParameter(any()) } answers { parameters[firstArg()] }
        }

    @Test
    fun `an anonymous request is refused`() {
        val anonymous = request()

        filter.doFilter(anonymous, response, chain)

        verify { response.sendError(HttpServletResponse.SC_UNAUTHORIZED) }
        verify(exactly = 0) { chain.doFilter(any(), any()) }
    }
}
