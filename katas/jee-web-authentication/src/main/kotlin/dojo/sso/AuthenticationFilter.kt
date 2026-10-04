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

    override fun doFilter(request: ServletRequest, response: ServletResponse, chain: FilterChain) {
        val token = (request as HttpServletRequest).cookies?.firstOrNull { it.name == SSO_COOKIE }?.value
        val userName = request.getParameter("username")
        val password = request.getParameter("password")
        when {
            token != null && registry.tokenIsValid(token) -> chain.doFilter(request, response)
            userName != null && password != null && ldap.credentialsAreValid(userName, password) -> {
                val newToken = registry.registerNewSession(userName)
                (response as HttpServletResponse).addCookie(Cookie(SSO_COOKIE, newToken))
                chain.doFilter(request, response)
            }
            else -> (response as HttpServletResponse).sendError(HttpServletResponse.SC_UNAUTHORIZED)
        }
    }

    private companion object {
        const val SSO_COOKIE = "SSO_TOKEN"
    }
}
