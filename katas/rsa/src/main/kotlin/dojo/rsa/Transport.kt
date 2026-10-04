// Copyright (c) 2026 Riadh MNASRI. Licensed under the MIT License.
package dojo.rsa

import java.util.Base64

/** Pour transmettre le message chiffré par mail : uniquement des caractères lisibles. */
object Transport {
    fun encode(encrypted: ByteArray): String = Base64.getEncoder().encodeToString(encrypted)

    fun decode(text: String): ByteArray = Base64.getDecoder().decode(text)
}
