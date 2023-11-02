package dua

import dua.actions._
import dua.actions.Console._

import scala.util.matching.Regex

val rollCmd = """r(oll)? (\d+)?d(\d+)([+|-]\d+)?""".r

object DuaInterpreter {
  def parse(line : String)(using status: DuaStatus) : Boolean =
    line match
//      case rollCmd(_, nDice, dice, modifier) =>
//        val rolled = Dice.roll(nDice, dice, modifier)
//        println(s"rolled $rolled")
//        true
      case "quit" => !quit()
      case _ =>
        println(f"""Unknown command: "${line}"!""")
        true

}
