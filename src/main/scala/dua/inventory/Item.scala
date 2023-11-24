package dua.inventory

import scala.collection.mutable
import dua.inventory.*

import scala.language.postfixOps


trait Item(val name: String, val weight: Weight, val value: Coins)

trait Container[I <: Item](maxWeight: Option[Weight] = None) {
  val items: mutable.HashMap[I, Int] = mutable.HashMap.empty[I, Int]

  def grossWeight : Weight

  def netWeight : Weight = items
    .foldLeft(0 gr)((w, i) => {
      w + (i._1.weight * i._2)
    })

  def add(item: I): Boolean = {
    def _add(item: I) = {
      val newValue = items.get(item) match
        case Some(n) => n + 1
        case None => 1
      items.put(item, newValue)
    }

    maxWeight match
      case Some(mw) =>
        val newWeight = netWeight + item.weight
        if (newWeight <= mw) {
          _add(item)
          true
        } else {
          false
        }
      case _ =>
        _add(item)
        true
  }

}
