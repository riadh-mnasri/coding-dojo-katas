# RSA

🇫🇷 [Version française](README.md) · Kata: [codingdojo.org/kata/rsa](https://codingdojo.org/kata/rsa/)

## The kata

Encrypt and decrypt a message with RSA, using small keys:

- pick two primes p and q with 2^24 + 1 < N = p × q < 2^32;
- n = (p − 1)(q − 1), c coprime with n (public key (N, c)), d = c⁻¹ mod n (private key (N, d));
- encrypt: split the message into 3-byte blocks, each block a becomes a^c mod N on 4 bytes;
- decrypt: the reverse with d;
- transmit: encode the encrypted message in base64.

The kata gives a full example (p = 51581, q = 60101, c = 66797, "Hello world!" → `FQGtKYcinGkgGvkOQ2pvWw==`), used as is as the expected value.

> ⚠️ An educational kata: 32-bit keys and block splitting without a padding scheme offer **no** real security.

## TDD walkthrough

🔴 red · 🟢 green · 🔵 refactor · 📌 test green on first run

| # | Step | What happened |
|---|---|---|
| 1 | 🔴 `compute the modular inverse of the sample` | Does not compile. |
| 2 | 🟢 `compute modular inverses with the extended Euclidean algorithm` | Extended Euclid, as the kata suggests: d = 1336940133 comes out. |
| 3 | 🔴 `build the sample key pair from p, q and c` | `KeyPair` does not exist. |
| 4 | 🟢 `derive the public and private keys` | N, n, then d. |
| 5 | 🔴 `encrypt the sample message into its blocks` | `Rsa` does not exist. Expected: the kata's four blocks. |
| 6 | 🟢 `encrypt 3-byte blocks into 4-byte blocks` | Modular exponentiation (`BigInteger.modPow`): a^c would overflow a `Long` by far. |
| 7 | 🔴 `decrypt the sample back to the message` | `decrypt` does not exist. |
| 8 | 🟢 `decrypt 4-byte blocks back into 3-byte blocks` | The reverse path with d. |
| 9 | 🔴 `encode the encrypted message in base64 and back` | Expected: `FQGtKYcinGkgGvkOQ2pvWw==`. |
| 10 | 🟢 `encode and decode the encrypted message in base64` | `java.util.Base64`. |
| 11 | 🔴 `round-trip a message whose length is not a multiple of three` | "Bonjour" (7 bytes) comes back as "Bonjou??r": the last, incomplete block is misread. |
| 12 | 🟢 `pad the last block with zero bytes and strip them back` | This kata's convention (the statement sets none): pad with zero bytes, stripped on decryption. Limit: a message itself ending with zero bytes would lose them. |
| 13 | 🔴 `refuse keys outside the kata constraints` | p = 3, q = 5, or c = 2 (no inverse, n being even) are accepted. |
| 14 | 🟢 `check the modulus range and that c is coprime with n` | Two `require`s and a GCD. |
| 15 | 🔴 `generate keys from random primes` | `generate` does not exist; the test checks the round trip over 20 seeds. |
| 16 | 🟢 `generate keys from random primes in the allowed range` | Two distinct primes between 2^12 and 2^16 always give N in the required range; c is drawn until coprime with n. |

## Solution

```kotlin
fun encrypt(message: ByteArray, key: PublicKey): ByteArray =
    padded(message).chunked(3).flatMap { block ->
        toBytes(power(toNumber(block), key.exponent, key.modulus), 4)
    }.toByteArray()
```

Why 3 bytes become 4: a 3-byte block is below 2^24 < N, so it can be encrypted; and the result, below N < 2^32, fits in 4 bytes.

## Takeaways

The kata's encrypted example is a golden expected value: at each step (inverse, keys, blocks, base64), the test compared the result with a value computed by someone else.

## Running the tests

```bash
./gradlew :katas:rsa:test
git log --reverse --format='%s%n%b' strict-tdd-start.. -- katas/rsa   # the TDD history
```
