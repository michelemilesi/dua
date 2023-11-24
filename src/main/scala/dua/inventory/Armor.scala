package dua.inventory

import dua.inventory._

import dua.time.*
import dua.inventory.ArmorType.*
import dua.time.GameTime

import scala.language.postfixOps

abstract class Armor(override val name: String, val armorType: ArmorType)
                    (w: Weight, v: Coins)
  extends Item(name, w, v), BodyWear {

  override def acModifier: Int = baseAC + bonusAC

  def baseAC: Int = 0
  def bonusAC: Int = 0
  def maxDexModifier: Option[Int] = None
  def minStrength: Int = 0
  def stealthDisadvantage: Boolean = false

  def wearTime: GameTime = armorType match
    case LIGHT_ARMOR => 1 minute
    case MEDIUM_ARMOR => 5 minutes
    case HEAVY_ARMOR => 10 minutes
    case _ => 10 minutes

  def removeTime: GameTime = armorType match
    case LIGHT_ARMOR => 1 minute
    case MEDIUM_ARMOR => 1 minute
    case HEAVY_ARMOR => 5 minutes
    case _ => 5 minutes
}

case object PaddedArmor extends Armor("padded armor", LIGHT_ARMOR)
                               ( 4 Kg, 5 GP) {
  override def baseAC: Int = 11
  override def stealthDisadvantage: Boolean = true
}

case object LeatherArmor extends Armor("leather armor", LIGHT_ARMOR)( 5 Kg, 10 GP) {
  override def baseAC: Int = 11
}

case object StuddedLeatherArmor extends Armor("studded leather armor", LIGHT_ARMOR)( 6.5 Kg, 45 GP) {
  override def baseAC: Int = 12
}

case object Hide extends Armor("hide", MEDIUM_ARMOR)( 6 Kg, 10 GP) {
  override def baseAC: Int = 12
  override def maxDexModifier: Option[Int] = Some(2)
}

case object ChainShirt extends Armor("chain shirt", MEDIUM_ARMOR)( 10 Kg, 50 GP) {
  override def baseAC: Int = 13
  override def maxDexModifier: Option[Int] = Some(2)
}

case object ScaleMail extends Armor("scale mail", MEDIUM_ARMOR)( 22.5 Kg, 50 GP) {
  override def baseAC: Int = 14
  override def maxDexModifier: Option[Int] = Some(2)
  override def stealthDisadvantage: Boolean = true
}

case object Breastplate extends Armor("breastplate", MEDIUM_ARMOR)( 10 Kg, 400 GP) {
  override def baseAC: Int = 14
  override def maxDexModifier: Option[Int] = Some(2)
}

case object HalfPlateArmor extends Armor("half plate armor", MEDIUM_ARMOR)( 20 Kg, 750 GP) {
  override def baseAC: Int = 14
  override def maxDexModifier: Option[Int] = Some(2)
  override def stealthDisadvantage: Boolean = true
}

case object RingMail extends Armor("ring mail", HEAVY_ARMOR)( 20 Kg, 30 GP) {
  override def baseAC: Int = 14
  override def maxDexModifier: Option[Int] = Some(0)
  override def stealthDisadvantage: Boolean = true
}

case object ChainMail extends Armor("chain mail", HEAVY_ARMOR)( 27.5 Kg, 75 GP) {
  override def baseAC: Int = 16
  override def maxDexModifier: Option[Int] = Some(0)
  override def stealthDisadvantage: Boolean = true
  override def minStrength: Int = 13
}

case object SplintArmor extends Armor("splint armor", HEAVY_ARMOR)( 30 Kg, 200 GP) {
  override def baseAC: Int = 17
  override def maxDexModifier: Option[Int] = Some(0)
  override def stealthDisadvantage: Boolean = true
  override def minStrength: Int = 15
}

case object PlateArmor extends Armor("plate armor", HEAVY_ARMOR)( 32.5 Kg, 1500 GP) {
  override def baseAC: Int = 18
  override def maxDexModifier: Option[Int] = Some(0)
  override def stealthDisadvantage: Boolean = true
  override def minStrength: Int = 15
}