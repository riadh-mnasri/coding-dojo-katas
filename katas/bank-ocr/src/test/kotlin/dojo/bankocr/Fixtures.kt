// Copyright (c) 2026 Riadh MNASRI. Licensed under the MIT License.
package dojo.bankocr

/** Les cas de test de l'énoncé, extraits tels quels (espaces compris) : 4 lignes d'entrée puis « => attendu ». */
data class UseCase(val entry: String, val expected: String) {
    override fun toString() = expected
}

fun useCases(resource: String): List<UseCase> =
    UseCase::class.java.getResource("/$resource")!!.readText().trimEnd('\n').split("\n").chunked(5).map { chunk ->
        UseCase(chunk.take(4).joinToString("\n"), chunk[4].removePrefix("=> ").trim())
    }
