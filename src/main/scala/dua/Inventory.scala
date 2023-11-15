package dua

import dua.inventory.Coins

class Inventory {
  val coins = Coins()

  def weight : BigDecimal = coins.weight
}
