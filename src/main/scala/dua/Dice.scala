package dua

import scala.util.Try

object Dice {
  private def roll(dice:Int): Int = math.ceil(math.random() * dice).toInt

  def roll(n : String, dice: String, modifier : String) : Int =
    val _n = Try(n.toInt).toOption.getOrElse(1)
    val _dice = Try(dice.toInt).toOption.getOrElse(0)
    val _modifier = Try(modifier.toInt).toOption.getOrElse(0)
    roll(_n, _dice, _modifier)

  def roll(n : Int, dice: Int, modifier : Int) : Int =
    if (n == 0)
      modifier
    else
      modifier + roll(dice) + roll(n-1, dice, 0)
}
