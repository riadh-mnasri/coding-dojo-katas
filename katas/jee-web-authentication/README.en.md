# JEE Web Authentication

🇫🇷 [Version française](README.md) · Kata: [codingdojo.org/kata/JEEWebAuthentication](https://codingdojo.org/kata/JEEWebAuthentication/)

## The kata

Write a servlet filter authenticating a web application's requests:

- through request parameters (`username`, `password`), checked against an **LDAP**;
- successful logins are recorded in a **single sign-on registry**, and the token is returned in a cookie;
- a request presenting a valid token goes through.

The LDAP and the SSO registry are written by other teams: only their interfaces are known, injected through setters.

The kata is mostly about **mocks versus stubs**.

## Mocks and stubs

- **Stubs** (hand-written) for the LDAP and the SSO registry: they return prepared answers. Tests then check **state** (does the session exist?).
- **Mocks** (MockK) for `HttpServletRequest`, `HttpServletResponse` and `FilterChain`: huge interfaces, of which only a few **interactions** are verified (`chain.doFilter` called? `sendError(401)`?).

## TDD walkthrough

🔴 red · 🟢 green · 🔵 refactor · 📌 test green on first run

| # | Step | What happened |
|---|---|---|
| ⛔ | false red undone | My first red failed because the MockK and servlet API jars were not downloaded yet (the guard runs offline): the wrong reason. Undone before pushing, and the guard now refuses a red step with neither a compilation error nor a failing test. While redoing the commits I first mixed this test into the script commit; fixed before pushing too. |
| 1 | 🔴 `refuse an anonymous request` | The interfaces and the filter do not exist. |
| ⛔ | rejected green | MockK's *runtime* jars were still missing: green refused by the guard, then replayed once the dependencies were downloaded. |
| 2 | 🟢 `refuse every request` | The kata's two interfaces, setter injection, and a systematic 401. |
| 3 | 🔴 `let a request with a valid SSO cookie through` | The chain is never called. |
| 4 | 🟢 `accept a valid SSO token from the cookie` | Read the `SSO_TOKEN` cookie, validated by the registry. |
| 5 | 🔴 `log in with valid credentials and set the SSO cookie` | No login through parameters. |
| 6 | 🟢 `open a session from the request parameters` | Minimum: a user name is enough to open the session. The password is not checked yet. |
| 7 | 🔴 `refuse wrong credentials` | Exposes the step 6 minimum: a wrong password opens a session. |
| 8 | 🟢 `check credentials against the LDAP` | The LDAP call, forced by this test. |
| 9 | 🔴 `end the session on logout` | The session survives the logout. |
| 10 | 🟢 `end the SSO session on logout` | `registry.endSession(token)`. |
| 11 | 🔵 `name the three ways in` | `ssoToken`, `hasValidCredentials`: the `when` reads like the rules. |

## Takeaways

- Stubs **provide** answers, mocks **verify** calls. Mocking the small business interfaces would have coupled the tests to the implementation; a stub checked through its state is sturdier.
- Step 6 shows the value of the minimum: without test 7, the LDAP call would not have been driven by any test.
- On the tooling side, two dependency traps were blocked by the guard instead of producing false reds or false greens.

## Running the tests

```bash
./gradlew :katas:jee-web-authentication:test
git log --reverse --format='%s%n%b' strict-tdd-start.. -- katas/jee-web-authentication   # the TDD history
```
