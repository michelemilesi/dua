package dua.inventory

import dua.inventory.WeaponType._
import dua.inventory.DamageType._
import dua.actions._
import dua.inventory._
import dua.actions.DiceRoll._

import scala.language.postfixOps

trait Finesse

trait HeavyWeapon

trait LightWeapon extends OffHandEquipment

trait Loading

trait Reach

trait Thrown(val min: Int, val max: Int)

trait TwoHanded

trait Versatile(val twoHandDamage: () => Int)

abstract class Weapon(override val name: String, val weaponType: WeaponType, val damage: () => Int)
                     (w: Weight, v: Coins)
  extends Item(name, w, v), MainHandEquipment {

}

abstract class HandToHandWeapon(override val name: String, override val weaponType: WeaponType,
                                override val damage: () => Int, val damageType: DamageType)
                                (w: Weight, v: Coins)
  extends Weapon(name, weaponType, damage)(w, v)

abstract class RangedWeapon[Ammunition](override val name: String, override val weaponType: WeaponType,
                                        override val damage: () => Int, min: Int, max: Int)
                                       (w: Weight, v: Coins)
  extends Weapon(name, weaponType, damage)(w, v) {
}

abstract class ThrowingWeapon(override val name: String, override val weaponType: WeaponType,
                              override val damage: () => Int, min: Int, max: Int, val damageType: DamageType)
                             (w: Weight, v: Coins)
  extends Weapon(name, weaponType, damage)(w, v), Thrown(min, max)

case object Club extends HandToHandWeapon("club", SIMPLE_WEAPON, () => roll(1 d4), BLUDGEONING)(1 Kg, 1 SP),
  LightWeapon

case object Dagger extends HandToHandWeapon("dagger", SIMPLE_WEAPON, () => roll(1 d4), PIERCING)(0.5 Kg,	2 GP),
  Finesse, LightWeapon, Thrown(20, 60)

case object GreatClub	extends HandToHandWeapon("great club", SIMPLE_WEAPON, () => roll(1 d8),
  BLUDGEONING)(5 Kg, 1 SP),
  TwoHanded

case object Handaxe	extends HandToHandWeapon("handaxe", SIMPLE_WEAPON, () => roll(1 d6), SLASHING)(1 Kg, 5 GP),
  LightWeapon, Thrown(20, 60)

case object Javelin	extends HandToHandWeapon("javelin", SIMPLE_WEAPON, () => roll(1 d6), PIERCING)(1 Kg, 5 SP),
  Thrown(30, 120)

case object LightHammer	extends HandToHandWeapon("light hammer", SIMPLE_WEAPON, () => roll(1 d4),
  BLUDGEONING)(1 Kg, 2 GP),
  LightWeapon, Thrown(20, 60)

case object Mace extends HandToHandWeapon("mace", SIMPLE_WEAPON, () => roll(1 d6), BLUDGEONING)(2 Kg, 5 GP)

case object Quarterstaff extends HandToHandWeapon("quarterstaff", SIMPLE_WEAPON, () => roll(1 d6),
  BLUDGEONING)(2 Kg, 2 SP),
  Versatile(() => roll(1 d8))

case object Sickle extends HandToHandWeapon("sickle", SIMPLE_WEAPON, () => roll(1 d4), SLASHING)(1.0 Kg,
  1 GP), LightWeapon

case object Spear	extends HandToHandWeapon("spear", SIMPLE_WEAPON, () => roll(1 d6), PIERCING)( 1.5 Kg,
  1 GP), Thrown(20, 60), Versatile(() => roll(1 d8))

case object LightCrossbow extends RangedWeapon[BoltType]("light crossbow", SIMPLE_WEAPON, () => roll(1 d8), 80,
  320)(2.5 Kg, 25 GP), Loading, TwoHanded

case object Dart extends ThrowingWeapon("dart", SIMPLE_WEAPON, () => roll(1 d4), 20, 60,
  PIERCING)(0.25 Kg, 5 CP), Finesse

case object Shortbow extends RangedWeapon[ArrowType]("shortbow", SIMPLE_WEAPON, () => roll(1 d6), 80,
  320)(1 Kg, 25 GP), TwoHanded

case object Sling extends RangedWeapon[BulletType]("sling", SIMPLE_WEAPON, () => roll(1 d4), 30, 120)
  (0 gr, 1 SP)

// Martial Melee Weapons
case object Battleaxe	extends HandToHandWeapon("battleaxe", MARTIAL_WEAPON, () => roll(1 d8),
  SLASHING)(2 Kg, 10 GP), Versatile(() => roll(1 d10))

case object Flail	extends HandToHandWeapon("flail", MARTIAL_WEAPON, () => roll(1 d8), BLUDGEONING)(1 Kg, 10 GP)

case object Glaive extends HandToHandWeapon("glaive", MARTIAL_WEAPON, () => roll(1 d10), SLASHING)(3 Kg, 20 GP),
  HeavyWeapon, Reach, TwoHanded

case object Greataxe extends HandToHandWeapon("greataxe", MARTIAL_WEAPON, () => roll(1 d12), SLASHING)(3.5 Kg, 30 GP),
  HeavyWeapon, TwoHanded

case object Greatsword extends HandToHandWeapon("greatsword", MARTIAL_WEAPON, () => roll(2 d6), SLASHING)(3 Kg, 50 GP),
  HeavyWeapon, TwoHanded

case object Halberd	extends HandToHandWeapon("halberd", MARTIAL_WEAPON, () => roll(1 d10), SLASHING)(3 Kg, 20 GP), HeavyWeapon, Reach, TwoHanded

case object Lance	extends HandToHandWeapon("lance", MARTIAL_WEAPON, () => roll(1 d12), PIERCING)(3 Kg, 10 GP),
  Reach //, Special

case object Longsword	extends HandToHandWeapon("longsword", MARTIAL_WEAPON, () => roll(1 d8), SLASHING)(1.5 Kg, 15 GP),
  Versatile(() => roll(1 d10))

case object Maul extends HandToHandWeapon("maul", MARTIAL_WEAPON, () => roll(2 d6), BLUDGEONING)(5 Kg, 10 GP),
  HeavyWeapon, TwoHanded

case object Morningstar	extends HandToHandWeapon("morningstar", MARTIAL_WEAPON, () => roll(1 d8), PIERCING)(2 Kg, 15 GP)

case object Pike extends HandToHandWeapon("pike", MARTIAL_WEAPON, () => roll(1 d10), PIERCING)( 9 Kg,
  5 GP), HeavyWeapon, Reach, TwoHanded

case object Rapier extends HandToHandWeapon("rapier", MARTIAL_WEAPON, () => roll(1 d8), PIERCING)( 1 Kg,
  25 GP), Finesse

case object Scimitar extends HandToHandWeapon("scimitar", MARTIAL_WEAPON, () => roll(1 d6), SLASHING)( 1.5 Kg,
  25 GP), Finesse, LightWeapon

case object Shortsword extends HandToHandWeapon("shortsword", MARTIAL_WEAPON, () => roll(1 d6), PIERCING)(
  1 Kg, 10 GP), Finesse, LightWeapon

case object Trident	extends HandToHandWeapon("trident", MARTIAL_WEAPON, () => roll(1 d6), PIERCING)( 2 Kg,
  5 GP), Thrown(20, 60), Versatile(() => roll(1 d8))

case object WarPick extends HandToHandWeapon("war pick", MARTIAL_WEAPON, () => roll(1 d8), PIERCING)( 1 Kg,
  5 GP)

case object Warhammer	extends HandToHandWeapon("warhammer", MARTIAL_WEAPON, () => roll(1 d8), BLUDGEONING)(
  1 Kg, 15 GP), Versatile(() => roll(1 d10))

case object Whip extends HandToHandWeapon("whip", MARTIAL_WEAPON, () => roll(1 d4), SLASHING)( 1.5 Kg,
  2 GP), Finesse, Reach

// Martial Ranged Weapons
case object Blowgun extends RangedWeapon[NeedleType]("blowgun", MARTIAL_WEAPON, () => 1, 25, 100)(
  500 gr, 10 GP), Loading

case object HandCrossbow extends RangedWeapon[BoltType]("hand crossbow", MARTIAL_WEAPON, () => roll(1 d6), 30,
  120)( 1.5 Kg, 75 GP), Loading, LightWeapon

case object HeavyCrossbow extends RangedWeapon[BoltType]("heavy crossbow", MARTIAL_WEAPON, () => roll(1 d10), 100,
  400)( 9 Kg, 50 GP), Loading, HeavyWeapon, TwoHanded

case object Longbow extends RangedWeapon[ArrowType]("longbow", MARTIAL_WEAPON, () => roll(1 d8), 150,
  600)(1 Kg, 50 GP), HeavyWeapon, TwoHanded
