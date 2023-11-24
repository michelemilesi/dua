package dua.inventory

import scala.annotation.targetName

case class Coins(var gold: Int = 0, var silver: Int = 0, var copper: Int = 0) {
  def weight: Weight = Weight((gold + silver + copper) * 10)
  
  @targetName("add")
  def +(coins: Coins) : Unit = {
    gold += coins.gold
    silver += coins.silver
    copper += coins.copper
  }
}
