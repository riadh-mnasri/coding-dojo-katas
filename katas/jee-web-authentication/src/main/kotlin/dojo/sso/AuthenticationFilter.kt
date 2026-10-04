// Copyright (c) 2026 Riadh MNASRI. Licensed under the MIT License.
package dojo.sso

import jakarta.servlet.Filter
import jakarta.servlet.FilterChain
import jakarta.servlet.ServletRequest
import jakarta.servlet.ServletResponse
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
        (response as HttpServletResponse).sendError(HttpServletResponse.SC_UNAUTHORIZED)
    }
}
