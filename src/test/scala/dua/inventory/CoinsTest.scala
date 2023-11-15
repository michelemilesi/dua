package dua.inventory

import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should

import dua.inventory.Coins

class CoinsTest extends AnyFlatSpec with should.Matchers {
  "Coins" should "have a weight" in {
    val coins = Coins(30, 40, 40)
    coins.weight should be (1.10)
  }
}
