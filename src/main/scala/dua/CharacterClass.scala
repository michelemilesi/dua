package dua

import dua.Ability.*
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
}

class Warrior extends AbstractCharacterClass("warrior") {
  override def getHitPointDice: Int = 10
  override def getStartingGold: Coins = roll(5 d4) * 10 GP
}

object CharacterClass {
  val RANGER = new Ranger()
  val WARRIOR = new Warrior()
  val CLASSES : List[CharacterClass] = List(RANGER, WARRIOR)
}
