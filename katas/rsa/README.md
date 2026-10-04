# RSA

🇬🇧 [English version](README.en.md) · Énoncé : [codingdojo.org/kata/rsa](https://codingdojo.org/kata/rsa/)

## Le kata

Chiffrer et déchiffrer un message avec l'algorithme RSA, sur de petites clés :

- choisir deux premiers p et q avec 2^24 + 1 < N = p × q < 2^32 ;
- n = (p − 1)(q − 1), c premier avec n (clé publique (N, c)), d = c⁻¹ mod n (clé privée (N, d)) ;
- chiffrer : découper le message en blocs de 3 octets, chaque bloc a devient a^c mod N sur 4 octets ;
- déchiffrer : l'inverse avec d ;
- transmettre : encoder le message chiffré en base64.

L'énoncé fournit un exemple complet (p = 51581, q = 60101, c = 66797, « Hello world! » → `FQGtKYcinGkgGvkOQ2pvWw==`), utilisé tel quel comme attendu.

> ⚠️ Kata pédagogique : des clés de 32 bits et un découpage sans schéma de bourrage n'offrent **aucune** sécurité réelle.

## Démarche TDD

🔴 rouge · 🟢 vert · 🔵 refactor · 📌 test vert du premier coup

| # | Étape | Ce qui s'est passé |
|---|---|---|
| 1 | 🔴 `compute the modular inverse of the sample` | Compilation impossible. |
| 2 | 🟢 `compute modular inverses with the extended Euclidean algorithm` | Euclide étendu, comme le suggère l'énoncé : on retrouve d = 1336940133. |
| 3 | 🔴 `build the sample key pair from p, q and c` | `KeyPair` n'existe pas. |
| 4 | 🟢 `derive the public and private keys` | N, n, puis d. |
| 5 | 🔴 `encrypt the sample message into its blocks` | `Rsa` n'existe pas. Attendus : les quatre blocs de l'énoncé. |
| 6 | 🟢 `encrypt 3-byte blocks into 4-byte blocks` | Exponentiation modulaire (`BigInteger.modPow`) : a^c déborderait de loin un `Long`. |
| 7 | 🔴 `decrypt the sample back to the message` | `decrypt` n'existe pas. |
| 8 | 🟢 `decrypt 4-byte blocks back into 3-byte blocks` | Le chemin inverse avec d. |
| 9 | 🔴 `encode the encrypted message in base64 and back` | Attendu : `FQGtKYcinGkgGvkOQ2pvWw==`. |
| 10 | 🟢 `encode and decode the encrypted message in base64` | `java.util.Base64`. |
| 11 | 🔴 `round-trip a message whose length is not a multiple of three` | « Bonjour » (7 octets) revient en « Bonjou??r » : le dernier bloc incomplet est mal relu. |
| 12 | 🟢 `pad the last block with zero bytes and strip them back` | Convention de ce kata (l'énoncé n'en fixe pas) : on complète par des octets nuls, retirés au déchiffrement. Limite : un message qui finit lui-même par des octets nuls les perdrait. |
| 13 | 🔴 `refuse keys outside the kata constraints` | p = 3, q = 5, ou c = 2 (pas d'inverse, n étant pair) sont acceptés. |
| 14 | 🟢 `check the modulus range and that c is coprime with n` | Deux `require`, et un PGCD. |
| 15 | 🔴 `generate keys from random primes` | `generate` n'existe pas ; le test vérifie l'aller-retour sur 20 graines. |
| 16 | 🟢 `generate keys from random primes in the allowed range` | Deux premiers distincts entre 2^12 et 2^16 donnent toujours N dans l'intervalle voulu ; c est tiré jusqu'à être premier avec n. |

## Solution

```kotlin
fun encrypt(message: ByteArray, key: PublicKey): ByteArray =
    padded(message).chunked(3).flatMap { block ->
        toBytes(power(toNumber(block), key.exponent, key.modulus), 4)
    }.toByteArray()
```

Pourquoi 3 octets deviennent 4 : un bloc de 3 octets vaut moins de 2^24 < N, donc il est chiffrable ; et le résultat, inférieur à N < 2^32, tient sur 4 octets.

## Ce que j'en retiens

L'exemple chiffré de l'énoncé est un attendu en or : à chaque étape (inverse, clés, blocs, base64), le test comparait le résultat à une valeur calculée par quelqu'un d'autre.

## Lancer les tests

```bash
./gradlew :katas:rsa:test
git log --reverse --format='%s%n%b' strict-tdd-start.. -- katas/rsa   # l'historique TDD
```
