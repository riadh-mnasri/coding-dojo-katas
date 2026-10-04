// Copyright (c) 2026 Riadh MNASRI. Licensed under the MIT License.
package dojo.birthday

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.io.TempDir
import java.nio.file.Path
import java.time.LocalDate
import kotlin.io.path.writeText

/** Test d'intégration de l'adaptateur : un vrai fichier, au format de l'énoncé. */
class FlatFileFriendRepositoryTest {

    @Test
    fun `reads every friend of the file, skipping the header`(@TempDir directory: Path) {
        val file = directory.resolve("friends.csv").apply {
            writeText(
                """
                last_name, first_name, date_of_birth, email
                Doe, John, 1982/10/08, john.doe@foobar.com
                Ann, Mary, 1975/09/11, mary.ann@foobar.com
                """.trimIndent(),
            )
        }

        assertThat(FlatFileFriendRepository(file).all()).containsExactly(
            Friend("Doe", "John", LocalDate.of(1982, 10, 8), "john.doe@foobar.com"),
            Friend("Ann", "Mary", LocalDate.of(1975, 9, 11), "mary.ann@foobar.com"),
        )
    }
}
