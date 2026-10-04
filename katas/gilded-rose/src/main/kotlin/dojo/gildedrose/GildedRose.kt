// Copyright (c) 2026 Riadh MNASRI. Licensed under the MIT License.
package dojo.gildedrose

/**
 * La boutique de l'auberge du Gilded Rose. Chaque jour, chaque objet vieillit selon sa catégorie.
 * La classe [Item] appartient au gobelin du coin : on ne la modifie pas.
 */
class GildedRose(var items: List<Item>) {

    fun updateQuality() {
        items.forEach(::updateItem)
    }

    private fun updateItem(item: Item) {
        when (item.name) {
            SULFURAS -> Unit
            AGED_BRIE -> ageBrie(item)
            BACKSTAGE_PASSES -> ageBackstagePasses(item)
            else -> if (item.name.startsWith(CONJURED)) ageConjured(item) else ageNormally(item)
        }
    }

    /** Un objet ordinaire perd 1 de qualité par jour, 2 une fois la date de vente passée. */
    private fun ageNormally(item: Item) {
        decreaseQuality(item)
        item.sellIn--
        if (item.sellIn < 0) decreaseQuality(item)
    }

    /** Un objet invoqué se dégrade deux fois plus vite qu'un objet ordinaire. */
    private fun ageConjured(item: Item) {
        repeat(2) { decreaseQuality(item) }
        item.sellIn--
        if (item.sellIn < 0) repeat(2) { decreaseQuality(item) }
    }

    /** Le brie se bonifie : +1 par jour, +2 une fois la date passée. */
    private fun ageBrie(item: Item) {
        increaseQuality(item)
        item.sellIn--
        if (item.sellIn < 0) increaseQuality(item)
    }

    /** +1, +2 à 10 jours ou moins, +3 à 5 jours ou moins ; ne vaut plus rien après le concert. */
    private fun ageBackstagePasses(item: Item) {
        increaseQuality(item)
        if (item.sellIn < 11) increaseQuality(item)
        if (item.sellIn < 6) increaseQuality(item)
        item.sellIn--
        if (item.sellIn < 0) item.quality = 0
    }

    private fun increaseQuality(item: Item) {
        if (item.quality < MAX_QUALITY) item.quality++
    }

    private fun decreaseQuality(item: Item) {
        if (item.quality > 0) item.quality--
    }

    private companion object {
        const val MAX_QUALITY = 50
        const val SULFURAS = "Sulfuras, Hand of Ragnaros"
        const val AGED_BRIE = "Aged Brie"
        const val BACKSTAGE_PASSES = "Backstage passes to a TAFKAL80ETC concert"
        const val CONJURED = "Conjured"
    }
}
