package dua

import dua.actions.DiceRoll

class HitPoint(val max : Int) extends Printable {
  var current : Int = max
  var temp : Int = 0
  
  def actual : Int = current + temp 
  
  def reset : Unit = {
    current = max
    temp = 0
  }

  override def print(variant: Option[Any])(using status: DuaStatus): String = {
    s"[$actual/$max]"
  }
}

object HitPoint {
  def apply(level: Int, dice: Int, modifier: Int) : HitPoint = if (level == 1) {
    new HitPoint(dice + modifier)
  } else {
    new HitPoint(DiceRoll.roll(level - 1, dice).collect() + dice + level * modifier)
  }
}
