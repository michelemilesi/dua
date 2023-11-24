package dua

import dua.inventory.*


class Inventory {
  val coins: Coins = Coins()
  val items: Map[String, (Item, Int)] = Map.empty[String, (Item, Int)]

  var head: Option[HeadWear] = None
  var body: Option[BodyWear] = None
  var mainHand: Option[MainHandEquipment] = None
  var offHand: Option[OffHandEquipment] = None
  var gloves: Option[Gloves] = None
  var foot: Option[FootWear] = None
  var belt: Option[Belt] = None
  var necklace: Option[Necklace] = None
  var clothes: Option[Clothes] = None
  var ring: Option[Ring] = None

  def weight : Weight = coins.weight + items.values.foldLeft(Weight(0))((acc: Weight, item: (Item, Int)) => {
    acc + (item(0).weight * item(1))
  })

  def ac : Int = List(head, body, mainHand, offHand, gloves, foot, belt, necklace, clothes, ring)
      .filter(_.isDefined)
      .map(_.get)
      .foldLeft(0)((ac: Int, item: Wearable)=> {ac + item.acModifier})
}
