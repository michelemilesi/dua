package dua

enum Ability(val name: String):
  case STR extends Ability("str")
  case DEX extends Ability("dex")
  case CON extends Ability("con")
  case INT extends Ability("int")
  case WIS extends Ability("wis")
  case CHA extends Ability("cha")
end Ability

case class Abilities(str: Int = 0, dex: Int = 0, con: Int = 0, int: Int = 0, wis: Int = 0, cha: Int = 0) {
  def modify(mod: Abilities): Abilities = Abilities(
    str = this.str + mod.str,
    dex = this.dex + mod.dex,
    con = this.con + mod.con,
    int = this.int + mod.int,
    wis = this.wis + mod.wis,
    cha = this.cha + mod.cha
  )
  private def apply(ability: Ability, f: Int => Int): Int = ability match {
    case Ability.STR => f(str)
    case Ability.DEX => f(dex)
    case Ability.CON => f(con)
    case Ability.INT => f(int)
    case Ability.WIS => f(wis)
    case Ability.CHA => f(cha)
  }
  def mod(ability: Ability): Int = apply(ability, v => Math.floor((v - 10.0) / 2.0).toInt)

  def get(ability: Ability): Int = apply(ability, v => v)
}

object Abilities {
  def getText(ability: Ability)(using DuaStatus)  : String = DuaMessages.getMessage(ability.name)
  def getShortText(ability: Ability)(using DuaStatus)  : String = DuaMessages.getMessage(s"${ability.name}.short")
}