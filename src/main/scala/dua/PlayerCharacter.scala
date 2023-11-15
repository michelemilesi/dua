package dua

import dua.Ability._

case class PlayerCharacter(var name: Option[String] = None, var race: Option[Race] = None, var sex: Option[Sex] = None,
                           var characterClass: Option[CharacterClass] = None, var abilities: Option[Abilities] = None,
                           var hp: Option[HitPoint] = None, var level: Int = 1, inventory: Inventory = Inventory()
                          )
  extends Printable {
  override def print(variant: Option[Any] = None)(using DuaStatus): String = List(name, characterClass, race, hp)
    .filter(_.isDefined)
    .map(_.get)
    .map(a => a match
      case p: Printable => p.print(sex)
      case _ => a.toString)
    .mkString(" ")
    .capitalize

  def show(using DuaStatus): Unit = {
    println()
    println(f"Nome: ${name.getOrElse("")}%-20s Razza: ${race.get.getName().capitalize}%-10s " +
      f"Classe: ${characterClass.get.getName(sex).capitalize}%-10s Livello: $level%2d Sesso: ${sex.getOrElse("-")}")
    val (hpCurr, hpMax, hpTemp) = hp match
      case Some(_hp) => (_hp.actual, _hp.max, _hp.temp)
      case _ => (0, 0, 0)
    abilities match {
      case Some(abs) =>
        println(f"${Abilities.getShortText(STR)}: ${abs.str}%2d (${abs.mod(STR)}%+2d) PF $hpCurr%3d/$hpMax%3d (Temp $hpTemp%3d)")
        println(f"${Abilities.getShortText(DEX)}: ${abs.dex}%2d (${abs.mod(DEX)}%+2d)")
        println(f"${Abilities.getShortText(CON)}: ${abs.con}%2d (${abs.mod(CON)}%+2d)")
        println(f"${Abilities.getShortText(INT)}: ${abs.int}%2d (${abs.mod(INT)}%+2d)")
        println(f"${Abilities.getShortText(WIS)}: ${abs.wis}%2d (${abs.mod(WIS)}%+2d)")
        println(f"${Abilities.getShortText(CHA)}: ${abs.cha}%2d (${abs.mod(CHA)}%+2d)")
        println("------------------------------------------------------------")
        println(f"MO: ${inventory.coins.gold}%3d MA: ${inventory.coins.silver}%3d MR: ${inventory.coins.copper}%3d Peso: ${inventory.weight}%3.2f")
      case _ => println("")
    }
  }
}