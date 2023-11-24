package dua

import dua.Ability.*
import dua.Proficiency._
import dua.actions.*
import dua.actions.DiceRoll.*
import dua.inventory._
import dua.inventory.Coins

import scala.language.postfixOps

trait CharacterClass {
  def getName(sex: Option[Sex] = None)(using status: DuaStatus): String
  def getHitPointDice: Int
  def getStartingGold: Coins

  def spellCastingAbility: Option[Ability]

  def getProficiencies: List[Proficiency]

  def getStartingEquipments: List[List[StartingEquipment]] = List.empty[List[StartingEquipment]]
}

abstract class AbstractCharacterClass(name: String) extends CharacterClass, Printable {
  override def getName(sex: Option[Sex] = None)(using status: DuaStatus): String = name
  override def print(variant: Option[Any] = None)(using status: DuaStatus): String = name
  override def spellCastingAbility: Option[Ability] = None
}

class Ranger extends AbstractCharacterClass("ranger") {
  override def getHitPointDice: Int = 10

  override def getStartingGold: Coins = roll(5 d4) * 10 GP

  override def spellCastingAbility: Option[Ability] = Some(WIS)

  override def getProficiencies: List[Proficiency] = List(SIMPLE_WEAPONS, MARTIAL_WEAPONS, LIGHT_ARMORS, MEDIUM_ARMORS,
    SHIELDS)

  override def getStartingEquipments: List[List[StartingEquipment]] = List(
    List(
      StartingEquipment().withItem(1, ScaleMail)
      ,
      StartingEquipment().withItem(1, LeatherArmor)
    ),
    List(StartingEquipment().withItem(2, Shortsword)),
    List(StartingEquipment()
      .withItem(1, Longbow)
      .withItem(1, Quiver)
      .withItem(20, Arrow)
    ),
    List(StartingEquipment.DUNGEONEER_PACK, StartingEquipment.EXPLORER_PACK)
  )
  // (a) Scale Mail or (b) leather armor
  //• (a) two shortswords or (b) two simple melee weapons
  //• (a) a dungeoneer's pack or (b) an explorer's pack
  //• A longbow and a quiver of 20 arrows
}

class Warrior extends AbstractCharacterClass("warrior") {
  override def getHitPointDice: Int = 10
  override def getStartingGold: Coins = roll(5 d4) * 10 GP

  override def getProficiencies: List[Proficiency] = List(SIMPLE_WEAPONS, MARTIAL_WEAPONS, LIGHT_ARMORS, MEDIUM_ARMORS,
    HEAVY_ARMORS, SHIELDS)
}

object CharacterClass {
  val RANGER = new Ranger()
  val WARRIOR = new Warrior()
  val CLASSES : List[CharacterClass] = List(RANGER, WARRIOR)
}
