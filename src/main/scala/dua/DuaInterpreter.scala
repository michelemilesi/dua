package dua

import scala.util.matching.Regex

val rollCmd = """r(oll)? (\d+)?d(\d+)([+|-]\d+)?""".r

object DuaInterpreter {
  def parse(line : String) : Boolean =
    line match
      case rollCmd(_, nDice, dice, modifier) =>
        val rolled = Dice.roll(nDice, dice, modifier)
        println(s"rolled $rolled")
        true
      case "quit" => false
      case _ =>
        println("Unknown command")
        true

}
