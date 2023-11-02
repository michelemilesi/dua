package dua.actions

case class DiceRoll(rolls: List[Int]) {
  def collect(): Int = rolls.sum

  def takeMax(): Int = rolls.max

  def takeMin(): Int = rolls.min
  
  def skipBest(): Int = {
    var best = rolls.headOption.getOrElse(0)
    val total = rolls.foldRight(0)((v, sum) => {
      best = if v > best then v else best
      sum + v
    })
    total - best
  }

  def skipWorst(): Int = {
    var worst = rolls.headOption.getOrElse(0)
    val total = rolls.foldRight(0)((v, sum) => {
      worst = if v < worst then v else worst
      sum + v
    })
    total - worst
  }
}

object DiceRoll {
  def roll(diceRoll: DiceRoll): Int = diceRoll.collect()

  def max(diceRoll: DiceRoll): Int = diceRoll.takeMax()
  def min(diceRoll: DiceRoll): Int = diceRoll.takeMin()
  
  def skipBest(diceRoll: DiceRoll):Int = diceRoll.skipBest()
  def skipWorst(diceRoll: DiceRoll):Int = diceRoll.skipWorst()
  def roll(n: Int, dice: Int): DiceRoll = DiceRoll((for (_ <- 1 to n) yield math.ceil(math.random() * dice).toInt).toList)
}
