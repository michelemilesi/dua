package dua.inventory

trait Wearable {
  def acModifier : Int = 0
}

trait HeadWear extends Wearable
trait BodyWear extends Wearable
trait MainHandEquipment extends Wearable
trait OffHandEquipment extends Wearable
trait Gloves extends Wearable
trait FootWear extends Wearable
trait Belt extends Wearable
trait Necklace extends Wearable
trait Clothes extends Wearable
trait Ring extends Wearable
