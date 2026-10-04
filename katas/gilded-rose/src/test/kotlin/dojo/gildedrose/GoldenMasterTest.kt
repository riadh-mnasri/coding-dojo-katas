// Copyright (c) 2026 Riadh MNASRI. Licensed under the MIT License.
package dojo.gildedrose

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import java.io.File

/**
 * Test de caractérisation (golden master) : on ne sait pas encore ce que le code *devrait* faire,
 * on fige ce qu'il *fait*. La sortie reçue est écrite dans build/ ; une fois relue, elle devient la référence.
 */
class GoldenMasterTest {

    private fun fixture() = listOf(
        Item("+5 Dexterity Vest", 10, 20),
        Item("Aged Brie", 2, 0),
        Item("Elixir of the Mongoose", 5, 7),
        Item("Sulfuras, Hand of Ragnaros", 0, 80),
        Item("Sulfuras, Hand of Ragnaros", -1, 80),
        Item("Backstage passes to a TAFKAL80ETC concert", 15, 20),
        Item("Backstage passes to a TAFKAL80ETC concert", 10, 49),
        Item("Backstage passes to a TAFKAL80ETC concert", 5, 49),
        Item("Conjured Mana Cake", 3, 6),
    )

    private fun thirtyDays(): String {
        val items = fixture()
        val shop = GildedRose(items)
        return (0..30).joinToString("") { day ->
            val report = "-------- day $day --------\n" + items.joinToString("\n", postfix = "\n\n")
            shop.updateQuality()
            report
        }
    }

    @Test
    fun `thirty days of the reference fixture match the approved output`() {
        val received = thirtyDays()
        File("build/golden-master.received.txt").writeText(received)

        val approved = javaClass.getResource("/golden-master.approved.txt")?.readText()

        assertThat(received).isEqualTo(approved)
    }
}
