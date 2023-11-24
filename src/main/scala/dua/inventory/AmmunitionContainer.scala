package dua.inventory

import dua.inventory._

import scala.collection.mutable
import scala.language.postfixOps

abstract class AmmunitionContainer[A <: Ammunition](name: String, val size: Int)(w: Weight, c: Coins)
  extends Item(name, w, c), Container[A]() {
  val ammunition =  mutable.HashMap.empty[String, (A, Int)]
  var active: Option[A] = None

  def put(ammo: A, n: Int): Unit = {
    if (count + n <= size) {
      val current = ammunition.getOrElse(ammo.name, (ammo, 0))
      ammunition(ammo.name) = (current._1, current._2 + n)
    }
  }

  def activate(ammo: String): Boolean = {
    ammunition.get(ammo) match
      case Some(a, n) if n > 0 =>
        active = Some(a)
        true
      case _ => false
  }

  def take(): Option[A] = {
    val ammo = active match
      case Some(a) => a.name
      case _ => "none"

    ammunition.get(ammo) match
      case Some((_, 0)) => None
      case Some((_, 1)) =>
        active = None
        ammunition.remove(ammo)
        active
      case Some((a, n)) =>
        ammunition.put(ammo, (a, n-1))
        active
      case _ => None
  }

  def count: Int = ammunition
    .values
    .foldLeft(0)((acc, v) => { acc + v._2 })

  override def netWeight : Weight = ammunition
    .values
    .foldLeft(0 gr)((w, v) => {w + (v._1.weight * v._2)})

  override def grossWeight: Weight = weight + netWeight

}

case object Quiver extends AmmunitionContainer[ArrowType]("quiver", 20)(0.5 Kg, 1 GP)