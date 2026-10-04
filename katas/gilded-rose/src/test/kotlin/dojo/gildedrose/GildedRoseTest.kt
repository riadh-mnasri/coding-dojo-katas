// Copyright (c) 2026 Riadh MNASRI. Licensed under the MIT License.
package dojo.gildedrose

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.CsvSource

/** Une ligne par règle de l'énoncé : nom, sellIn et quality avant, puis après un jour. */
class GildedRoseTest {

    @ParameterizedTest(name = "{0} ({1}, {2}) -> ({3}, {4})")
    @CsvSource(
        delimiter = '|',
        value = [
            "Elixir | 5 | 7 | 4 | 6",
            "Elixir | 0 | 7 | -1 | 5",
            "Elixir | 5 | 0 | 4 | 0",
            "Aged Brie | 5 | 7 | 4 | 8",
            "Aged Brie | 0 | 7 | -1 | 9",
            "Aged Brie | 5 | 50 | 4 | 50",
            "Aged Brie | 0 | 49 | -1 | 50",
            "Sulfuras, Hand of Ragnaros | 5 | 80 | 5 | 80",
            "Sulfuras, Hand of Ragnaros | -1 | 80 | -1 | 80",
            "Backstage passes to a TAFKAL80ETC concert | 11 | 20 | 10 | 21",
            "Backstage passes to a TAFKAL80ETC concert | 10 | 20 | 9 | 22",
            "Backstage passes to a TAFKAL80ETC concert | 5 | 20 | 4 | 23",
            "Backstage passes to a TAFKAL80ETC concert | 5 | 49 | 4 | 50",
            "Backstage passes to a TAFKAL80ETC concert | 0 | 20 | -1 | 0",
        ],
    )
    fun `updates sellIn and quality according to the rules`(name: String, sellIn: Int, quality: Int, nextSellIn: Int, nextQuality: Int) {
        val item = Item(name, sellIn, quality)

        GildedRose(listOf(item)).updateQuality()

        assertThat(item.sellIn to item.quality).isEqualTo(nextSellIn to nextQuality)
    }

    @ParameterizedTest(name = "{0} ({1}, {2}) -> ({3}, {4})")
    @CsvSource(
        delimiter = '|',
        value = [
            "Conjured Mana Cake | 3 | 6 | 2 | 4",
            "Conjured Mana Cake | 0 | 6 | -1 | 2",
            "Conjured Mana Cake | 3 | 1 | 2 | 0",
        ],
    )
    fun `conjured items degrade twice as fast as normal items`(name: String, sellIn: Int, quality: Int, nextSellIn: Int, nextQuality: Int) {
        val item = Item(name, sellIn, quality)

        GildedRose(listOf(item)).updateQuality()

        assertThat(item.sellIn to item.quality).isEqualTo(nextSellIn to nextQuality)
    }
}
