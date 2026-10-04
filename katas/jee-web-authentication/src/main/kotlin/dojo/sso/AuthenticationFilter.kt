// Copyright (c) 2026 Riadh MNASRI. Licensed under the MIT License.
package dojo.sso

import jakarta.servlet.Filter
import jakarta.servlet.FilterChain
import jakarta.servlet.ServletRequest
import jakarta.servlet.ServletResponse
import jakarta.servlet.http.Cookie
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse

class AuthenticationFilter : Filter {
    private lateinit var ldap: LdapAuthenticationGateway
    private lateinit var registry: SingleSignOnRegistry

    /** Injection par mutateurs, comme le suppose l'énoncé avec son framework d'injection. */
    fun setLdapAuthenticationGateway(gateway: LdapAuthenticationGateway) {
        ldap = gateway
    }

    fun setSingleSignOnRegistry(registry: SingleSignOnRegistry) {
        this.registry = registry
    }

    /** Trois entrées possibles : se déconnecter, présenter un jeton SSO valide, ou se connecter par identifiants. */
    override fun doFilter(request: ServletRequest, response: ServletResponse, chain: FilterChain) {
        val http = request as HttpServletRequest
        val reply = response as HttpServletResponse
        val token = ssoToken(http)
        when {
            token != null && http.getParameter("logout") != null -> registry.endSession(token)
            token != null && registry.tokenIsValid(token) -> chain.doFilter(request, response)
            hasValidCredentials(http) -> {
                reply.addCookie(Cookie(SSO_COOKIE, registry.registerNewSession(http.getParameter("username"))))
                chain.doFilter(request, response)
            }
            else -> reply.sendError(HttpServletResponse.SC_UNAUTHORIZED)
        }
    }

    private fun ssoToken(request: HttpServletRequest): String? =
        request.cookies?.firstOrNull { it.name == SSO_COOKIE }?.value

    private fun hasValidCredentials(request: HttpServletRequest): Boolean {
        val userName = request.getParameter("username") ?: return false
        val password = request.getParameter("password") ?: return false
        return ldap.credentialsAreValid(userName, password)
    }

    private companion object {
        const val SSO_COOKIE = "SSO_TOKEN"
    }
}
