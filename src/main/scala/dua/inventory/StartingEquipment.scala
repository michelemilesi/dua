package dua.inventory

case class StartingEquipment(label : String = "", items : List[(Int, Item)] = List.empty) {
  def withItem(n: Int, item: Item): StartingEquipment = StartingEquipment(this.label, (n, item) :: items )
}

object StartingEquipment {
  val DUNGEONEER_PACK = StartingEquipment("Dungeoneer's Pack")
  //a backpack
  //
  //• a crowbar
  //
  //• a hammer
  //
  //• 10 pitons
  //
  //• 10 torches
  //
  //• a tinderbox
  //
  //• 10 days of rations
  //
  //• a waterskin
  //
  //• 50 feet of hempen rope

  val EXPLORER_PACK = StartingEquipment("Explorer's Pack")
//  a backpack
//
//  •a bedroll
//
//  •a mess kit
//
//  •a tinderbox
//
//  •10 torches
//
//  •10 days of rations
//
//  •a waterskin
//
//  •50 feet of hempen rope
}
