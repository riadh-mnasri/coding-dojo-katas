# JEE Web Authentication

🇬🇧 [English version](README.en.md) · Énoncé : [codingdojo.org/kata/JEEWebAuthentication](https://codingdojo.org/kata/JEEWebAuthentication/)

## Le kata

Écrire un filtre servlet qui authentifie les requêtes d'une application web :

- par paramètres de requête (`username`, `password`), vérifiés auprès d'un **LDAP** ;
- les connexions réussies sont enregistrées dans un **registre SSO**, et le jeton renvoyé dans un cookie ;
- une requête qui présente un jeton valide passe.

Le LDAP et le registre SSO sont écrits par d'autres équipes : on n'en connaît que les interfaces, injectées par mutateurs.

Le kata sert surtout à parler de **mocks et de stubs**.

## Mocks et stubs

- **Stubs** (écrits à la main) pour le LDAP et le registre SSO : ils renvoient des réponses préparées. On vérifie ensuite un **état** (la session existe-t-elle ?).
- **Mocks** (MockK) pour `HttpServletRequest`, `HttpServletResponse` et `FilterChain` : des interfaces énormes, dont on ne vérifie que quelques **interactions** (`chain.doFilter` appelé ? `sendError(401)` ?).

## Démarche TDD

🔴 rouge · 🟢 vert · 🔵 refactor · 📌 test vert du premier coup

| # | Étape | Ce qui s'est passé |
|---|---|---|
| ⛔ | faux rouge annulé | Mon premier rouge échouait parce que les jars de MockK et de l'API servlet n'étaient pas encore téléchargés (le garde-fou tourne hors ligne) : la mauvaise raison. Annulé avant de pousser, et le garde-fou refuse désormais un rouge sans erreur de compilation ni test en échec. En refaisant les commits, j'ai d'abord mêlé ce test au commit du script ; corrigé aussi avant de pousser. |
| 1 | 🔴 `refuse an anonymous request` | Les interfaces et le filtre n'existent pas. |
| ⛔ | vert refusé | Les jars du *runtime* de MockK manquaient encore : vert refusé par le garde-fou, puis rejoué une fois les dépendances téléchargées. |
| 2 | 🟢 `refuse every request` | Les deux interfaces de l'énoncé, l'injection par mutateurs, et un 401 systématique. |
| 3 | 🔴 `let a request with a valid SSO cookie through` | La chaîne n'est jamais appelée. |
| 4 | 🟢 `accept a valid SSO token from the cookie` | Lecture du cookie `SSO_TOKEN`, validation par le registre. |
| 5 | 🔴 `log in with valid credentials and set the SSO cookie` | Pas de connexion par paramètres. |
| 6 | 🟢 `open a session from the request parameters` | Minimum : un nom d'utilisateur suffit pour ouvrir la session. Le mot de passe n'est pas encore vérifié. |
| 7 | 🔴 `refuse wrong credentials` | Démasque le minimum de l'étape 6 : un mauvais mot de passe ouvre une session. |
| 8 | 🟢 `check credentials against the LDAP` | L'appel au LDAP, imposé par ce test. |
| 9 | 🔴 `end the session on logout` | La session survit à la déconnexion. |
| 10 | 🟢 `end the SSO session on logout` | `registry.endSession(token)`. |
| 11 | 🔵 `name the three ways in` | `ssoToken`, `hasValidCredentials` : le `when` se lit comme les règles. |

## Ce que j'en retiens

- Les stubs servent à **fournir** des réponses, les mocks à **vérifier** des appels. Utiliser un mock pour les petites interfaces métier aurait couplé les tests à l'implémentation ; un stub vérifié par son état est plus robuste.
- L'étape 6 montre l'intérêt du minimum : sans le test 7, l'appel au LDAP n'aurait été piloté par aucun test.
- Côté outillage, deux pièges de dépendances ont été bloqués par le garde-fou au lieu de produire de faux rouges ou de faux verts.

## Lancer les tests

```bash
./gradlew :katas:jee-web-authentication:test
git log --reverse --format='%s%n%b' strict-tdd-start.. -- katas/jee-web-authentication   # l'historique TDD
```
