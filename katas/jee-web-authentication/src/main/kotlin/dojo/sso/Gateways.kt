// Copyright (c) 2026 Riadh MNASRI. Licensed under the MIT License.
package dojo.sso

/** Fourni par l'équipe LDAP : on n'en connaît que cette interface. */
interface LdapAuthenticationGateway {
    fun credentialsAreValid(userName: String, password: String): Boolean
}

/** Fourni par l'équipe SSO : on n'en connaît que cette interface. */
interface SingleSignOnRegistry {
    fun tokenIsValid(token: String): Boolean
    fun registerNewSession(userName: String): String
    fun endSession(token: String)
}
