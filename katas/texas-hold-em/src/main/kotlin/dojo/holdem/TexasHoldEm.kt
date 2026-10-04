// Copyright (c) 2026 Riadh MNASRI. Licensed under the MIT License.
package dojo.holdem

object TexasHoldEm {
    fun announce(input: String): String = input

    fun rank(cards: String): String = Hand.best(cards.split(" ").map(Card::parse)).category.label
}
