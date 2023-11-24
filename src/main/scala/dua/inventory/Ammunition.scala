package dua.inventory

import dua.inventory.DamageType._
import dua.actions._

import scala.language.postfixOps

abstract class Ammunition(override val name: String, extraDamage: () => Int = () => 0, damageType: DamageType)
                          (w: Weight, v: Coins)
  extends Item(name, w, v) {

}

class ArrowType(override val name: String) extends Ammunition(name, () => 0, PIERCING)( 10 gr, 1 SP)

case object Arrow extends ArrowType("arrow")

class BoltType(override val name: String) extends Ammunition(name, () => 0, PIERCING)( 10 gr, 1 SP)

case object Bolt extends BoltType("bolt")

class NeedleType(override val name: String) extends Ammunition(name, () => 0, PIERCING)( 10 gr, 1 CP)

case object Needle extends NeedleType("needle")

class BulletType(override val name: String) extends Ammunition(name, () => 0, BLUDGEONING)( 10 gr, 1 CP)

case object Bullet extends BulletType("bullet")
